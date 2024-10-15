package com.github.guilhermebauer.studymanagement.controller.contract;

import com.github.guilhermebauer.studymanagement.model.values.RoleVO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface RoleControllerContract {

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<RoleVO> createRole(@RequestBody RoleVO role);

    @GetMapping(value="/findByRoleName/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<RoleVO> findRoleByName(@PathVariable(value = "name") String name);
}
