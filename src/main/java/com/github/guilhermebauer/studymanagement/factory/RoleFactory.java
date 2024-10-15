package com.github.guilhermebauer.studymanagement.factory;

import com.github.guilhermebauer.studymanagement.model.RoleEntity;
import com.github.guilhermebauer.studymanagement.utils.UuidUtils;

public class RoleFactory {

    private static final String ROLE_PREFIX = "ROLE_";

    public RoleFactory() {
    }

    public static RoleEntity create(String name) {
        return new RoleEntity(UuidUtils.generateUuid(), ROLE_PREFIX + name);
    }
}
