package com.github.guilhermebauer.studymanagement.utils;


import com.github.guilhermebauer.studymanagement.exception.UuidUtilsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class UuidUtilsTest {

    public static final String UUID_REQUIRED = "An UUID need to be informed!";
    private final String INVALID_ID = "d8e";



    @Test
    void testGenerateUuid_When_Successful_ShouldReturnUUID(){
        String uuid = UuidUtils.generateUuid();
        assertNotNull(uuid);


    }


    @Test
    void testIsValidUuid_WhenUuidIsNull_ShouldThrowUuidUtilsException() {


        UuidUtilsException exception = assertThrows(UuidUtilsException.class, () -> UuidUtils.isValidUuid(null));
        assertNotNull(exception);
        Assertions.assertEquals(UuidUtilsException.ERROR.formatErrorMessage(UUID_REQUIRED), exception.getMessage());


    }


    @Test
    void testIsValidUuid_WhenUuidIsInvalid_ShouldThrowUuidUtilsException() {


        UuidUtilsException exception = assertThrows(UuidUtilsException.class, () -> UuidUtils.isValidUuid(INVALID_ID));
        assertNotNull(exception);
        Assertions.assertEquals(UuidUtilsException.ERROR.formatErrorMessage(INVALID_ID), exception.getMessage());


    }

    @Test
    void testIsValidUuid_WhenUuidIsIsValid_ShouldReturnTrue() {


        String ID = "d8e7df81-2cd4-41a2-a005-62e6d8079716";
        assertTrue(UuidUtils.isValidUuid(ID));

    }


}


