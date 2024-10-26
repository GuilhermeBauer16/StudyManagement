package com.github.guilhermebauer.studymanagement.service;

import com.github.guilhermebauer.studymanagement.exception.EmailAllReadyRegisterException;
import com.github.guilhermebauer.studymanagement.exception.FieldNotFound;
import com.github.guilhermebauer.studymanagement.exception.UserNotFoundException;
import com.github.guilhermebauer.studymanagement.factory.UserFactory;
import com.github.guilhermebauer.studymanagement.mapper.BuildMapper;
import com.github.guilhermebauer.studymanagement.model.RoleEntity;
import com.github.guilhermebauer.studymanagement.model.UserEntity;
import com.github.guilhermebauer.studymanagement.model.values.RoleVO;
import com.github.guilhermebauer.studymanagement.model.values.UserVO;
import com.github.guilhermebauer.studymanagement.repository.UserRepository;
import com.github.guilhermebauer.studymanagement.response.UserRegistrationResponse;
import com.github.guilhermebauer.studymanagement.service.contract.UserRegistrationServiceContract;
import com.github.guilhermebauer.studymanagement.utils.ValidatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserRegistrationService  implements UserRegistrationServiceContract {

    private static final String USER_NOT_FOUND_MESSAGE = "That User was not found!";
    private static final String EMAIL_ALREADY_REGISTER_MESSAGE = "The email %s is already registered for another user!";


    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleService roleService;

    @Autowired
    public UserRegistrationService(UserRepository userRepository, RoleService roleService, PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserRegistrationResponse createUser(UserVO userVO) {

        ValidatorUtils.checkObjectIsNullOrThrowException(userVO,USER_NOT_FOUND_MESSAGE, UserNotFoundException.class);
        userVO.setPassword(passwordEncoder.encode(userVO.getPassword()));
        checkIfEmailAlreadyRegistered(userVO.getEmail());
        UserEntity userFactory = UserFactory.create(userVO.getName(),userVO.getEmail(),userVO.getPassword(),retrieveRoles(userVO.getRoles()));
        ValidatorUtils.checkFieldNotNullAndNotEmptyOrThrowException(userFactory,USER_NOT_FOUND_MESSAGE, FieldNotFound.class);
        UserEntity userEntity = userRepository.save(userFactory);

        return BuildMapper.parseObject(new UserRegistrationResponse(),userEntity);
    }

    private Set<RoleEntity> retrieveRoles(Set<RoleEntity> roles) {

        Set<RoleEntity> rolesSet = new HashSet<>();

        for (RoleEntity role : roles) {

            RoleVO roleByName = roleService.findRoleByName(role.getName());
            rolesSet.add(BuildMapper.parseObject(new RoleEntity(),roleByName));

        }

        return rolesSet;
    }

    private void checkIfEmailAlreadyRegistered(String email) {

        if(userRepository.findUserByEmail(email).isPresent()) {

            throw new EmailAllReadyRegisterException(String.format(EMAIL_ALREADY_REGISTER_MESSAGE, email));
        }
    }
}
