package com.github.guilhermebauer.studymanagement.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.Set;

@Entity
@Table(name = "users")
public class UserEntity {


    @Id
    private String id;
    private String name;
    private String email;
    private String password;

    @OneToMany( fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user")
    private Set<RoleEntity> roles;

    public Set<RoleEntity> getRoles() {
        return roles;
    }

    public UserEntity(String id, String name, String email, String password, Set<RoleEntity> roles) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public UserEntity() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
    }

}
