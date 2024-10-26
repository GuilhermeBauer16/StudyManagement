package com.github.guilhermebauer.studymanagement.factory;

import com.github.guilhermebauer.studymanagement.model.RoleEntity;
import com.github.guilhermebauer.studymanagement.model.UserEntity;
import com.github.guilhermebauer.studymanagement.utils.UuidUtils;

import java.util.Set;

public class UserFactory {
    public UserFactory() {
    }

    public static UserEntity create(String name, String email, String password, Set<RoleEntity> roles) {
        return new UserEntity(UuidUtils.generateUuid(), name, email, password, roles);
    }
}
