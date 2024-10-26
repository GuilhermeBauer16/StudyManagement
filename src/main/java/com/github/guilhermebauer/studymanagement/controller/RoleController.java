package com.github.guilhermebauer.studymanagement.controller;


import com.github.guilhermebauer.studymanagement.controller.contract.RoleControllerContract;
import com.github.guilhermebauer.studymanagement.model.values.RoleVO;
import com.github.guilhermebauer.studymanagement.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/role")
public class RoleController implements RoleControllerContract {


    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public ResponseEntity<RoleVO> createRole(RoleVO role) {

        RoleVO roleVO = roleService.createRole(role);
        return new ResponseEntity<>(roleVO, HttpStatus.CREATED);

    }

    @Override
    public ResponseEntity<RoleVO> findRoleByName(String name) {

        RoleVO roleByName = roleService.findRoleByName(name);
        return ResponseEntity.ok(roleByName);
    }
}
