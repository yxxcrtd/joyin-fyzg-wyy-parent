package com.joyin.fyzg.wyy.filter;

import com.alibaba.fastjson.JSON;
import com.joyin.fyzg.common.RestResponse;
import com.joyin.fyzg.config.jwt.JwtInfo;
import com.joyin.fyzg.wyy.common.exception.ApiExceptionEnum;
import com.joyin.fyzg.utils.DateUtil8;
import com.joyin.fyzg.utils.JedisUtil;
import com.joyin.fyzg.utils.JwtTokenUtil;
import com.joyin.fyzg.config.user.UserProperties;
import com.joyin.fyzg.wyy.service.rbac.UserService;
import com.joyin.fyzg.wyy.entity.rbac.UserDO;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

import static com.joyin.fyzg.wyy.common.exception.ApiExceptionEnum.*;

@Component
@Slf4j
public class JwtAuthorizationTokenFilter extends OncePerRequestFilter {

	@Autowired
	private JwtInfo jwtInfo;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserProperties userProperties;

	@Autowired
	private Environment environment;

	@Autowired
	private UserService userService;

	private AntPathMatcher antPathMatcher = new AntPathMatcher();

	public static final String REDIRECT_URL = "joyinRedirectUrl";


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
		// 允许跨域预检请求直接通过
		if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
			chain.doFilter(request, response);
			return;
		}
		if (!ignore(request)) {
			// 非白名单请求：若开启强制认证，则Authorization缺失或非法时直接拒绝
			boolean requireAuth = Boolean.parseBoolean(environment.getProperty("jwt.requireAuthorization", "false"));
			final String requestHeader = request.getHeader(jwtInfo.getHeader());
			if (requireAuth) {
				if (StringUtils.isBlank(requestHeader) || !requestHeader.startsWith("Bearer ") || "Bearer null".equals(requestHeader)) {
					responseWrite(response, TOKEN_VALIDATE);
					return;
				}
			}
			// 非白名单的请求这里判断请求头中的token是否合法（当存在时）
			if (requestHeader != null && requestHeader.startsWith("Bearer ") && !"Bearer null".equals(requestHeader)) {
				String token = requestHeader.substring(7);
				String userCode = null;
				try {
					userCode = jwtTokenUtil.getCodeFromToken(token);
					// 判断token是否存在与redis中
					if (JedisUtil.KEYS.exists(jwtTokenUtil.buildTokenKey(userCode,token))){
						// 单点登录校验：若已开启，当前 token 必须等于该用户记录的“最新 token”
						if (userProperties.isSingleSignOn()) {
							String latestToken = JedisUtil.STRINGS.get("wyy:single:login:" + userCode);
							if (!ObjectUtils.isEmpty(latestToken) && !ObjectUtils.nullSafeEquals(latestToken, token)) {
								log.error(TOKEN_IS_EXPIRED.format(Optional.ofNullable(userCode).orElse(token)).getErrorMsg());
								responseWrite(response, TOKEN_IS_EXPIRED);
								return;
							}
						}
						 // 判断token是否需要续约
						if(jwtTokenUtil.canTokenBeRefreshed(token)) {
							// 分布式锁，放置老的token续约出多个新token
							if (JedisUtil.STRINGS.setnx(jwtTokenUtil.buildRefreshTokenKey(userCode, token), DateUtil8.getNowTime_EN()) == 1) {
								// 用老的token去续约，得到新的token
								String refreshToken = jwtTokenUtil.refreshToken(token);
								// 给新的token设置过期时间
								JedisUtil.STRINGS.setEx(jwtTokenUtil.buildTokenKey(userCode, refreshToken), jwtTokenUtil.buildTtl(), DateUtil8.getNowTime_EN());
								// 单点登录：更新该用户记录的“最新 token”
								if (userProperties.isSingleSignOn()) {
									JedisUtil.STRINGS.setEx("wyy:single:login:" + userCode, jwtTokenUtil.buildTtl(), refreshToken);
								}
								// 老的token设置成三分钟后过期
								JedisUtil.KEYS.expired(jwtTokenUtil.buildTokenKey(userCode, token), 60*3);
								// 分布式锁的key设置成三分钟后过期
								JedisUtil.KEYS.expired(jwtTokenUtil.buildRefreshTokenKey(userCode, token), 60*3);
								// 新的token
								response.setHeader("refresh-token",refreshToken);
							}
						}
					}else{
						log.error(TOKEN_IS_EXPIRED.format(Optional.ofNullable(userCode).orElse(token)).getErrorMsg());
						responseWrite(response, TOKEN_IS_EXPIRED);
						return;
					}
					// 后端鉴权（阻断垂直越权）：命中受保护路径仅允许管理员访问
					boolean backendAuthEnabled = Boolean.parseBoolean(environment.getProperty("jwt.authz.backend.enabled", "false"));
					if (backendAuthEnabled) {
						String patterns = environment.getProperty("jwt.authz.backend.protectPatterns", "");
						if (org.apache.commons.lang3.StringUtils.isNotBlank(patterns)) {
							String uri = request.getRequestURI();
							for (String p : patterns.split("\\s*,\\s*")) {
								if (antPathMatcher.match(p, uri)) {
									try {
										String adminAccount = environment.getProperty("user.adminAccount", "admin");
										UserDO u = userService.getUserByUserCode(userCode);
										if (u == null || !adminAccount.equals(u.getAccount())) {
											responseWrite(response, TOKEN_VALIDATE);
											return;
										}
									} catch (Exception ex) {
										responseWrite(response, TOKEN_VALIDATE);
										return;
									}
									break;
								}
							}
						}
					}
				}
				catch (ExpiredJwtException e) {
					log.error(TOKEN_IS_EXPIRED.format(Optional.ofNullable(userCode).orElse(token)).getErrorMsg(), e);
					responseWrite(response, TOKEN_IS_EXPIRED);
					return;
				}
				catch (Exception e) {
					log.error(TOKEN_VALIDATE.format(Optional.ofNullable(userCode).orElse(token)).getErrorMsg(), e);
					responseWrite(response, TOKEN_VALIDATE);
					return;
				}
			}
		}
		chain.doFilter(request, response);
	}

	private void responseWrite(HttpServletResponse response, ApiExceptionEnum exceptionEnum) throws IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		// 返回410错误，进到这里之后不再执行过滤器链了
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		PrintWriter writer = response.getWriter();
		writer.write(JSON.toJSONString(RestResponse.transWithCodeMsgEnum(exceptionEnum.format())));
	}

	/**
	 * 判断请求的URL是否在白名单中
	 * <br/>
	 * @param request
	 * @return boolean
	 * @author Administrator
	 * @date 2023/10/26 15:56
	 */
	private boolean ignore(HttpServletRequest request) {
		String uri = request.getRequestURI();
		String methodType = request.getMethod();
		if (jwtInfo.getUriIgnoreList().contains(uri)) {
			log.info(">>>>>>>>>>>>匹配到了白名单》》》》》》》"+uri);
			return true;
		}
		String prefixUri = methodType.toLowerCase() + ":" + uri;
		if (jwtInfo.getUriIgnoreList().contains(prefixUri)) {
			return true;
		}
		// 优先用AntPathMatcher，其实用这个也够了，底层是一样的，下面用的方式兜底
		for (String u : jwtInfo.getUriIgnoreList()) {
			boolean match = antPathMatcher.match(u, uri);
			if (match) {
				return true;
			}
		}
		return false;
	}
}
