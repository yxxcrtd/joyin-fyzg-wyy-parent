package com.joyin.fyzg.wyy.config.cas.filter;

import org.jasig.cas.client.Protocol;
import org.jasig.cas.client.validation.Cas20ProxyReceivingTicketValidationFilter;
import org.jasig.cas.client.validation.Cas30ProxyTicketValidator;
import org.jasig.cas.client.validation.Cas30ServiceTicketValidator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name  = "cas.enabled", havingValue = "true")
public class Cas300ProxyReceivingTicketValidationFilter extends Cas20ProxyReceivingTicketValidationFilter {

    public Cas300ProxyReceivingTicketValidationFilter() {
        super(Protocol.CAS3);
        this.defaultServiceTicketValidatorClass = Cas30ServiceTicketValidator.class;
        this.defaultProxyTicketValidatorClass = Cas30ProxyTicketValidator.class;
    }
}
