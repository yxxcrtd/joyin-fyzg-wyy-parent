package com.joyin.fyzg.wyy.common.utils;

import org.springframework.stereotype.Component;
import org.springframework.util.IdGenerator;
import org.springframework.util.JdkIdGenerator;

import java.util.UUID;

@Component
public class UuidGenerator implements IdGenerator {
    private final IdGenerator delegate = new JdkIdGenerator();

    @Override
    public UUID generateId() {
        return delegate.generateId();
    }

    public String nextId() {
        return generateId().toString().replace("-", "");
    }

    public String getType() {
        return "UUID";
    }

    public String nextApplyId(){
        return "aid" + nextId();
    }

    public String nextAttachmentId(){
        return "att" + nextId();
    }
}
