package com.joyin.fyzg.utils;

import com.joyin.fyzg.config.jwt.JwtInfo;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Component
public class JwtTokenUtil implements Serializable {

	public static final String USER_LOGIN_ACCOUNT = "USER_LOGIN_ACCOUNT";
	public static final String USER_LOGIN_STATE = "USER_LOGIN_STATE";
	public static final String CLIENT_ID = "clientId";
	public static final String FINANCIER = "financier";
	private static final String LOGIN_TOKENS = "LOGIN_TOKENS";
	private static final String LOGIN_REFRESH_TOKENS = "LOGIN_REFRESH_TOKENS";
	private static final long serialVersionUID = -3301605591108950415L;

	private Clock clock = DefaultClock.INSTANCE;

	@Autowired
	private JwtInfo jwtInfo;

	public String buildTokenKey(String userCode, String token) {
		return buildTokenPrefixKey(userCode) + token;
	}

	public String buildSessionTokenKey(String userCode, String session,String token) {
		return buildTokenPrefixKey(userCode) + session + ":" + token;
	}


	/***
	 * 获取token key的前缀
	 * <br/>
	 * @param userCode
	 * @return java.lang.String
	 * @author jinyuan.lin
	 * @date 2023/10/27 17:34
	 */
	public String buildTokenPrefixKey(String userCode) {
		return LOGIN_TOKENS + ":" + userCode + ":";
	}

	public String buildSessionPrefixKey(String userCode,String session) {
		return LOGIN_TOKENS + ":" + userCode + ":"+ session + ":";
	}


	public String buildRefreshTokenKey(String userCode, String token) {
		return buildRefreshTokenPrefixKey(userCode) + token;
	}

	/***
	 * 获取token key的前缀
	 * <br/>
	 * @param userCode
	 * @return java.lang.String
	 * @author jinyuan.lin
	 * @date 2023/10/27 17:34
	 */
	public String buildRefreshTokenPrefixKey(String userCode) {
		return LOGIN_REFRESH_TOKENS + ":" + userCode + ":";
	}

	public int buildTtl() {
		return Long.valueOf(jwtInfo.getExpiration()).intValue();
	}

	public String getCodeFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	public Date getIssuedAtDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getIssuedAt);
	}

	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser()
				.setSigningKey(jwtInfo.getSecret())
				.parseClaimsJws(token)
				.getBody();
	}

	/**
	 * 判断token是否还在有效期内
	 * <br/>
	 * @param token	
	 * @return java.lang.Boolean
	 * @author Administrator
	 * @date 2023/10/25 18:49
	 */
	public Boolean isTokenExpired(String token) {
		String leftDate = DateUtil8.formatTime_EN(clock.now());
		String rightDate = DateUtil8.formatTime_EN(getExpirationDateFromToken(token));
		// leftDate小于rightDate，String对比调试方便
		Boolean flag= leftDate.compareTo(rightDate) < 0;
		return  flag;
	}

	/**
	 * 判断token是否需要续约
	 * <br/>
	 * @param token
	 * @return java.lang.Boolean
	 * @author Administrator
	 * @date 2023/10/26 16:06
	 */
	private Boolean ignoreTokenExpiration(String token) {
		// 若token的生命周期小于1/4的时长则判断成需要续约
		long rightTime = getExpirationDateFromToken(token).getTime() - (jwtInfo.getExpiration() / jwtInfo.getParagraph() * 1000);
		String rightDate = DateUtil8.formatTime_EN(new Date(rightTime));
		String leftDate = DateUtil8.formatTime_EN(clock.now());
		// leftDate大于rightDate，String对比调试方便
		Boolean flag= leftDate.compareTo(rightDate) > 0;
		return  flag;
	}

	private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
		return (lastPasswordReset != null && created.before(lastPasswordReset));
	}

	public String generateToken4ClaimsWithExpiration(String userCode, Map<String, Object> claims) {
		return doGenerateToken(claims, userCode, true);
	}

	public String generateToken4ClaimsNoneExpiration(String userCode, Map<String, Object> claims) {
		return doGenerateToken(claims, userCode,false);
	}

	private String doGenerateToken(Map<String, Object> claims, String subject, Boolean setExpiration) {
		final Date createdDate = clock.now();
		JwtBuilder jwtBuilder = Jwts.builder()
				.setClaims(claims)
				.setSubject(subject)
				.setIssuedAt(createdDate)
				.signWith(SignatureAlgorithm.HS512, jwtInfo.getSecret());
		if (setExpiration){
			jwtBuilder.setExpiration(calculateExpirationDate(createdDate));
		}
		return jwtBuilder.compact();
	}

	/**
	 * 判断token是否需要续约
	 * <br/>
	 * @param token
	 * @return java.lang.Boolean
	 * @author Administrator
	 * @date 2023/10/26 16:07
	 */
	public Boolean canTokenBeRefreshed(String token) {
		// token为超过有效其且满足续约的规则
		return (isTokenExpired(token) && ignoreTokenExpiration(token));
	}

	public String refreshToken(String token) {
		final Date createdDate = clock.now();
		final Date expirationDate = calculateExpirationDate(createdDate);

		final Claims claims = getAllClaimsFromToken(token);
		claims.setIssuedAt(createdDate);
		claims.setExpiration(expirationDate);

		return Jwts.builder()
				.setClaims(claims)
				.signWith(SignatureAlgorithm.HS512, jwtInfo.getSecret())
				.compact();
	}

	public Boolean validateToken(String token, String userCode) {
		final String username = getCodeFromToken(token);
		return username.equals(userCode) && isTokenExpired(token);
	}

	private Date calculateExpirationDate(Date createdDate) {
		//		return new Date(createdDate.getTime() + 15 * 1000);
		//		return new Date(createdDate.getTime() + 60 * 1000);
		return new Date(createdDate.getTime() + jwtInfo.getExpiration() * 1000);
	}

	public static void main(String[] args) {
		String token="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImNsaWVudElkIjoiV0ZXLVBPU1QiLCJleHAiOjE2OTg5MTgxNjUsImlhdCI6MTY5ODkxNDU2NSwiVVNFUl9MT0dJTl9TVEFURSI6ImU4ZmRkOTk0LWI3MjAtNDc3NS1iYmNkLWJiZDI3NzQ4ZTc3MiJ9._G4LL1HUeEPf8-3XOsxVZG6Ud-xDDkqlPAbWQr-G4bU40xNFjo7HmTS8caZFzLJzW11WYh1ZJ-MqlYyIFap8yQ";
		Claims claims = Jwts.parser()
				.setSigningKey("mySecret")
				.parseClaimsJws(token)
				.getBody();
		System.out.println(DateUtil8.formatTime_EN(claims.getIssuedAt()));
		System.out.println(DateUtil8.formatTime_EN(claims.getExpiration()));
		System.out.println(claims.get(USER_LOGIN_STATE));
		System.out.println(claims);

	}
}
