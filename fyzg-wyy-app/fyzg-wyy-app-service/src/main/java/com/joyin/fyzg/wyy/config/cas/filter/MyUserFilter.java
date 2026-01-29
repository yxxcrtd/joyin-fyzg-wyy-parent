package com.joyin.fyzg.wyy.config.cas.filter;

import com.joyin.fyzg.common.MethodResponse;
import com.joyin.fyzg.utils.DateUtil8;
import com.joyin.fyzg.utils.JedisUtil;
import com.joyin.fyzg.utils.JwtTokenUtil;
import com.joyin.fyzg.wyy.common.dto.LoginDTO;
import com.joyin.fyzg.wyy.config.cas.config.CasInfo;
import com.joyin.fyzg.wyy.config.cas.constant.Constant;
import com.joyin.fyzg.wyy.config.cas.user.strategy.IUserDealStrategy;
import com.joyin.fyzg.wyy.service.permission.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name  = "cas.enabled", havingValue = "true")
public class MyUserFilter implements Filter {

	private final CasInfo casInfo;

	private final AuthService authService;

	private final List<IUserDealStrategy> strategies;

	private final JwtTokenUtil jwtTokenUtil;


	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		String redirectUrl = request.getParameter(Constant.REDIRECT_URL);
		// redirectUrl并非cas的login,而是我们的portal或wfw
		if (StringUtils.isNotBlank(redirectUrl)) {
			//进来IF目前只有login4JoyinCas.act这个请求，这里往reids添加会话信息
			final String clientId = request.getParameter(JwtTokenUtil.CLIENT_ID);
			String token = getToken(request, clientId);
			final String ticket = "ST-" + UUID.randomUUID().toString();
			final boolean cFlag = redirectUrl.contains("?");
			if (cFlag) {
				redirectUrl += ("&ticket=" + ticket);
			}
			else {
				redirectUrl += ("?ticket=" + ticket);
			}
			redirectUrl += ("#" + casInfo.getRoute());
			JedisUtil.HASH.hsetnx(Constant.ST_TOKENS, ticket, token);
			response.sendRedirect(redirectUrl);
			return;
		}
		filterChain.doFilter(servletRequest, servletResponse);
	}

	private String getToken(HttpServletRequest request, String clientId) {
		//security和cas 都会接管request，就是一个包装模式，导致这里生成了很多个不同的cookie
		final String userCode = getUserCode(request);
		Assert.notNull(userCode, "获取当前登录用户失败，请联系管理员！");
//		String token = jwtTokenUtil.generateToken4ClaimsNoneExpiration(userCode, new HashMap(1 << 1) {{
//			put(JwtTokenUtil.USER_LOGIN_STATE, getUserLoginState(request));
//			put(JwtTokenUtil.CLIENT_ID, clientId);
//		}});
		String token =  authService.login4Cas(userCode, clientId).getData().getToken();
		if (request.getSession(false) != null) {
			JedisUtil.STRINGS.set(jwtTokenUtil.buildSessionTokenKey(userCode, request.getSession(false).getId(), token), DateUtil8.getNowTime_EN());
		}
		return token;
	}

	private String getUserLoginState(HttpServletRequest request) {
		final Object userLoginStateObj = request.getSession(false).getAttribute(JwtTokenUtil.USER_LOGIN_STATE);
		String userLoginState = "";
		if (userLoginStateObj == null) {
			userLoginState = UUID.randomUUID().toString();
			request.getSession(false).setAttribute(JwtTokenUtil.USER_LOGIN_STATE, userLoginState);
		}
		else {
			userLoginState = String.valueOf(userLoginStateObj);
		}
		return userLoginState;
	}

	public String getUserCode(HttpServletRequest request) {
		String userCode = "";
		final Object userCodeObj = request.getSession(false).getAttribute(Constant.CURRENT_USER);
		if (userCodeObj == null) {
			final Principal principal = request.getUserPrincipal();
			// strategies这里两种方式取得登录会话信息，UserCasStrategy(CAS)和UserSecurityStrategy（SpringSecurity）
			final IUserDealStrategy strategy = strategies.parallelStream().filter(a -> a.isMatch(principal)).findFirst().orElse(null);
			Assert.notNull(strategy, "登录用户请求未找到处理策略，请联系管理员");
			userCode = strategy.apply(principal);
			/*在另一个方法中写数据*/
			/*MethodResponse<String> methodResponse = authService.writeUserSession(userCode);
			Assert.isTrue(methodResponse.isSuccess(), "登录用户请求调用用户服务失败，请联系管理员！");*/
			//这里currentUser写入的用户账号，不死userCode
			request.getSession().setAttribute(Constant.CURRENT_USER, userCode);
		}
		else {
			userCode = String.valueOf(userCodeObj);
		}
		return userCode;
	}

	@Override
	public void destroy() {

	}
}
