package com.github.guilhermebauer.studymanagement.service;

import com.github.guilhermebauer.studymanagement.exception.RoleAllReadyRegisterException;
import com.github.guilhermebauer.studymanagement.exception.RoleNotFoundException;
import com.github.guilhermebauer.studymanagement.model.RoleEntity;
import com.github.guilhermebauer.studymanagement.model.values.RoleVO;
import com.github.guilhermebauer.studymanagement.repository.RoleRepository;
import com.github.guilhermebauer.studymanagement.utils.ValidatorUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoleTest {

    private static final String ROLE_NOT_FOUND_MESSAGE = "That role does not exist or was not found in the database!";
    private static final String ROLE_ALREADY_REGISTER_MESSAGE = "A role with that name was registered in the database!";

    private static final String ID = "5f68880e-7356-4c86-a4a9-f8cc16e2ec87";
    private static final String ROLE_NAME = "ROLE_USER";


    @Mock
    private RoleEntity roleEntity;

    @Mock
    private RoleVO roleVO;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleService roleService;

    @BeforeEach
    void setUp() {
        roleEntity = new RoleEntity(ID, ROLE_NAME);
        roleVO = new RoleVO(ID, ROLE_NAME);
    }

    @Test
    void testCreateRole_WhenSuccess_ShouldReturnRoleObject() {
        try (MockedStatic<ValidatorUtils> mockedValidatorUtils = Mockito.mockStatic(ValidatorUtils.class)) {


            mockedValidatorUtils.when(() -> ValidatorUtils.checkFieldNotNullAndNotEmptyOrThrowException
                    (any(), anyString(), any(Class.class))).thenAnswer(invocation -> null);


            when(roleRepository.save(any(RoleEntity.class))).thenReturn(roleEntity);

            RoleVO role = roleService.createRole(roleVO);

            mockedValidatorUtils.verify(() -> ValidatorUtils.checkFieldNotNullAndNotEmptyOrThrowException(any(), anyString(), any(Class.class)));

            verify(roleRepository, times(1)).save(any(RoleEntity.class));

            assertNotNull(role);
            assertNotNull(role.getId());
            assertNotNull(role.getName());
            assertEquals(ID, role.getId());
            assertEquals(ROLE_NAME, role.getName());
        }
    }

    @Test
    void testCreateRole_WhenRoleAlreadyRegisteredIntoDatabase_ShouldThrowRoleAlreadyRegisteredException() {

        when(roleRepository.findByName(anyString())).thenReturn(Optional.of(roleEntity));
        RoleAllReadyRegisterException exception = assertThrows(RoleAllReadyRegisterException.class, () ->
                roleService.createRole(roleVO));

        assertNotNull(exception);
        assertEquals(RoleAllReadyRegisterException.ERROR.formatErrorMessage(ROLE_ALREADY_REGISTER_MESSAGE),
                exception.getMessage());
    }


    @Test
    void testFindRoleByName_WhenSuccess_ShouldReturnRoleObject() {

        when(roleRepository.findByName(anyString())).thenReturn(Optional.of(roleEntity));

        RoleVO roleByName = roleService.findRoleByName(ROLE_NAME);
        verify(roleRepository, times(1)).findByName(anyString());

        assertNotNull(roleByName);
        assertNotNull(roleByName.getId());
        assertNotNull(roleByName.getName());
        assertEquals(ID, roleByName.getId());
        assertEquals(ROLE_NAME, roleByName.getName());
    }

    @Test
    void testFindRoleByName_WhenRoleWasNotFound_ShouldThrowRoleNotFoundException() {

        RoleNotFoundException exception = assertThrows(RoleNotFoundException.class, () -> roleService.findRoleByName(ROLE_NAME));
        assertNotNull(exception);
        assertEquals(RoleNotFoundException.ERROR.formatErrorMessage(ROLE_NOT_FOUND_MESSAGE),
                exception.getMessage());
    }


}
