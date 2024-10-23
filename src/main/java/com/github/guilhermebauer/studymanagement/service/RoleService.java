package com.github.guilhermebauer.studymanagement.service;

import com.github.guilhermebauer.studymanagement.exception.FieldNotFound;
import com.github.guilhermebauer.studymanagement.exception.RoleAllReadyRegisterException;
import com.github.guilhermebauer.studymanagement.exception.RoleNotFoundException;
import com.github.guilhermebauer.studymanagement.factory.RoleFactory;
import com.github.guilhermebauer.studymanagement.mapper.BuildMapper;
import com.github.guilhermebauer.studymanagement.model.RoleEntity;
import com.github.guilhermebauer.studymanagement.model.values.RoleVO;
import com.github.guilhermebauer.studymanagement.repository.RoleRepository;
import com.github.guilhermebauer.studymanagement.service.contract.RoleServiceContract;
import com.github.guilhermebauer.studymanagement.utils.ValidatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService implements RoleServiceContract {

    private final RoleRepository repository;

    private static final String ROLE_NOT_FOUND_MESSAGE = "That role does not exist or was not found in the database!";
    private static final String ROLE_ALREADY_REGISTER_MESSAGE = "A role with that name was registered in the database!";
    private static final String ROLE_PREFIX = "ROLE_";

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.repository = roleRepository;
    }

    @Override
    public RoleVO createRole(RoleVO role) {

        checkIfRoleAlreadyRegisteredIntoDatabase(role.getName());
        RoleEntity roleFactory = RoleFactory.create(role.getName().toUpperCase());
        ValidatorUtils.checkFieldNotNullAndNotEmptyOrThrowException(roleFactory, ROLE_NOT_FOUND_MESSAGE, FieldNotFound.class);
        RoleEntity roleEntity = repository.save(roleFactory);
        return BuildMapper.parseObject(new RoleVO(), roleEntity);
    }

    @Override
    public RoleVO findRoleByName(String name) {

        RoleEntity roleEntity = repository.findByName(ROLE_PREFIX + name.toUpperCase()).orElseThrow(() -> new RoleNotFoundException(ROLE_NOT_FOUND_MESSAGE));
        return BuildMapper.parseObject(new RoleVO(), roleEntity);
    }


    private void checkIfRoleAlreadyRegisteredIntoDatabase(String roleName) {
        if (repository.findByName(ROLE_PREFIX + roleName.toUpperCase()).isPresent()) {
            throw new RoleAllReadyRegisterException(ROLE_ALREADY_REGISTER_MESSAGE);
        }

    }




}
