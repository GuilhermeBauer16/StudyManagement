package com.github.guilhermebauer.studymanagement.model.values;


import com.github.guilhermebauer.studymanagement.model.RoleEntity;

import java.util.Set;

public class UserVO {

    private String id;
    private String name;
    private String email;
    private String password;
    private Set<RoleEntity> roles;

    public UserVO(String id, String name, String email, String password, Set<RoleEntity> roles) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public UserVO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
    }
}


