package com.joyin.fyzg.wyy.config.cas.user.strategy;

import java.security.Principal;

public interface IUserDealStrategy {


    String apply(Principal principal);

    boolean isMatch(Principal principal);
}
