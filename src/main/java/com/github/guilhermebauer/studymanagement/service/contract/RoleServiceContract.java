package com.github.guilhermebauer.studymanagement.service.contract;

import com.github.guilhermebauer.studymanagement.model.values.RoleVO;

public interface RoleServiceContract {

    RoleVO createRole(RoleVO role);

    RoleVO findRoleByName(String name);


}
