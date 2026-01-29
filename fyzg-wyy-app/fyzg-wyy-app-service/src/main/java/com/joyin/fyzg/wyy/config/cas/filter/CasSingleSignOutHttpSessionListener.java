package com.joyin.fyzg.wyy.config.cas.filter;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.joyin.fyzg.utils.JedisUtil;
import com.joyin.fyzg.utils.JwtTokenUtil;
import com.joyin.fyzg.vo.ChatUserVO;
import com.joyin.fyzg.wyy.config.cas.constant.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Map;
import java.util.Set;

import static com.joyin.fyzg.utils.JwtTokenUtil.USER_LOGIN_STATE;

@Slf4j
@Component
@ConditionalOnProperty(name  = "cas.enabled", havingValue = "true")
public final class CasSingleSignOutHttpSessionListener implements HttpSessionListener {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public CasSingleSignOutHttpSessionListener() {
    }

    public void sessionCreated(HttpSessionEvent event) {
    }

    public void sessionDestroyed(HttpSessionEvent event) {
        final String userCode = getUserCode(event.getSession());
//        Set<String> keys = JedisUtil.KEYS.keys(jwtTokenUtil.buildSessionPrefixKey(userCode,event.getSession().getId()) + "*");
    }

    public String getUserCode(HttpSession session) {
        final Object userCodeObj = session.getAttribute(Constant.CURRENT_USER);
        Assert.notNull(userCodeObj, "登出用户未找会话信息，请联系管理员");
        return String.valueOf(userCodeObj);
    }
}

