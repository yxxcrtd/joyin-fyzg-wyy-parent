package com.joyin.fyzg.common;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.joyin.fyzg.config.jwt.JwtInfo;
import com.joyin.fyzg.utils.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * BaseController
 * <br/>
 *
 * @author pengzhen
 * @date 2020/2/13 0013 上午 11:24
 */
@Slf4j
@Component("baseController")
public class BaseController {

	@Autowired
	private JwtInfo jwtInfo;

	@Autowired
	protected JwtTokenUtil jwtTokenUtil;

	@Autowired
	protected HttpServletRequest request;

	public String getLoginUserCode() {
		return Optional.ofNullable(getToken()).map(token->jwtTokenUtil.getCodeFromToken(token)).orElse(null);
	}

	public String getClientId() {
		return this.getTokenClaim(a -> a.get("clientId", String.class));
	}

	public String getTokenClaim(Function<Claims, String> a){
		 return Optional.ofNullable(getToken()).map(token->jwtTokenUtil.getClaimFromToken(token, a)).orElse("");
	}

	public String getToken() {
		final String requestHeader = request.getHeader(jwtInfo.getHeader());
		if (requestHeader != null && requestHeader.startsWith("Bearer ")){
			log.info(MessageFormat.format("requestHeader值為：{0}", requestHeader));
			Assert.notNull(requestHeader, "解析当前登录用户状态出错，请联系管理员！错误信息如下【token为空】");
			String authToken = requestHeader.substring(7);
			log.info(MessageFormat.format("authToken值為：{0}", authToken));
			return authToken;
		}else {
			log.warn("couldn't find bearer string, will ignore the header");
		}
		return null;
	}

	protected String getClientIp() {
		return request.getRemoteAddr();
	}

    //解析IP, 若有代理，代理需要将真实IP绑定到请求头里
    protected String getIpAddress() {
        String Xip = request.getHeader("X-Real-IP");
        String XFor = request.getHeader("X-Forwarded-For");

        if (!Strings.isNullOrEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)) {
            int index = XFor.indexOf(",");
            if (index != -1) {
                return XFor.substring(0, index);
            } else {
                return XFor;
            }
        }
        XFor = Xip;
        if (!Strings.isNullOrEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)) {
            return XFor;
        }
        if (Strings.nullToEmpty(XFor).trim().isEmpty() || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("Proxy-Client-IP");
        }
        if (Strings.nullToEmpty(XFor).trim().isEmpty() || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("WL-Proxy-Client-IP");
        }
        if (Strings.nullToEmpty(XFor).trim().isEmpty() || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("HTTP_CLIENT_IP");
        }
        if (Strings.nullToEmpty(XFor).trim().isEmpty() || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (Strings.nullToEmpty(XFor).trim().isEmpty() || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getRemoteAddr();
        }

        return "0:0:0:0:0:0:0:1".equals(XFor) ? "127.0.0.1" : XFor;
    }

	public RestResponse<Map> buildResponse(List<QueryResultMap> tableData) {
		Integer totalCount = 0;
		if (tableData.size() > 0) {
			totalCount = Integer.valueOf(tableData.get(0).get("F_COUNT").toString());
		}
		return RestResponse.success(ImmutableMap.<String, Object>builder().put("tableData", tableData).put("totalCount", totalCount).build());
	}

	protected boolean isPageSearch(Map<String, Object> paramMap) {
		if (paramMap.containsKey("currentPage") && paramMap.containsKey("pageSize")) {
			return true;
		}
		return false;
	}

	protected void generateStartEnd(Map<String, Object> paramMap) {
		String currentPage = paramMap.get("currentPage").toString();
		String pageSize = paramMap.get("pageSize").toString();
		int startNum = (Integer.valueOf(currentPage) - 1) * Integer.valueOf(pageSize) + 1;
		int endNum = Integer.valueOf(currentPage) * Integer.valueOf(pageSize);
		paramMap.put("startNum", startNum);
		paramMap.put("endNum", endNum);
		paramMap.remove("currentPage");
		paramMap.remove("pageSize");
	}
}
