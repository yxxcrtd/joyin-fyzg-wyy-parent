package com.joyin.fyzg.wyy.config.cas.user.strategy;

import lombok.extern.slf4j.Slf4j;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.springframework.stereotype.Component;

import java.security.Principal;

@Slf4j
@Component
public class UserCasStrategy implements IUserDealStrategy{


    @Override
    public String apply(Principal principal) {
        final AttributePrincipal principalT = (AttributePrincipal) principal;
        return principalT.getName();
//        Map attributes = principalT.getAttributes();
//        log.info(JSON.toJSONString(attributes));
//        return String.valueOf(attributes.get("userOcode"));
    }

    @Override
    public boolean isMatch(Principal principal) {
        return principal instanceof AttributePrincipal;
    }
}
