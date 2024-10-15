package com.github.guilhermebauer.studymanagement.factory;

import com.github.guilhermebauer.studymanagement.model.RoleEntity;
import com.github.guilhermebauer.studymanagement.utils.UuidUtils;

public class RoleFactory {

    public RoleFactory() {
    }

    public static RoleEntity create(String name) {
        return new RoleEntity(UuidUtils.generateUuid(), "ROLE_" + name);
    }
}
