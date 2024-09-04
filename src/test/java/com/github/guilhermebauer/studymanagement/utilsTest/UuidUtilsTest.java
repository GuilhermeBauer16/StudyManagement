package com.github.guilhermebauer.studymanagement.utilsTest;


import com.github.guilhermebauer.studymanagement.exception.UuidUtilsException;
import com.github.guilhermebauer.studymanagement.utils.UuidUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class UuidUtilsTest {

    public static final String UUID_REQUIRED = "An UUID need to be informed!";
    private final String ID = "d8e7df81-2cd4-41a2-a005-62e6d8079716";
    private final String INVALID_ID = "d8e";


    @Test
    void testIsValidUuid_WhenUuidIsNull_ShouldThrowUuidUtilsException(){

        try(MockedStatic<UuidUtils> uuidUtilsMockedStatic =  Mockito.mockStatic(UuidUtils.class);){

            uuidUtilsMockedStatic.when(()-> UuidUtils.isValidUuid(null)).thenThrow(new UuidUtilsException(UUID_REQUIRED));
            UuidUtilsException exception = assertThrows(UuidUtilsException.class, () -> UuidUtils.isValidUuid(null));
            assertNotNull(exception);
            assertEquals(UuidUtilsException.ERROR.formatErrorMessage(UUID_REQUIRED), exception.getMessage());
        }


    }

    @Test
    void testIsValidUuid_WhenUuidIsInvalid_ShouldThrowUuidUtilsException(){

        try(MockedStatic<UuidUtils> uuidUtilsMockedStatic =  Mockito.mockStatic(UuidUtils.class);){

            uuidUtilsMockedStatic.when(()-> UuidUtils.isValidUuid(INVALID_ID)).thenThrow(new UuidUtilsException(INVALID_ID));
            UuidUtilsException exception = assertThrows(UuidUtilsException.class, () -> UuidUtils.isValidUuid(INVALID_ID));
            System.out.println(exception.getMessage());
            assertNotNull(exception);
            assertEquals(UuidUtilsException.ERROR.formatErrorMessage(INVALID_ID), exception.getMessage());
        }


    }

    @Test
    void testIsValidUuid_WhenUuidIsIsValid_ShouldReturnTrue(){

        try(MockedStatic<UuidUtils> uuidUtilsMockedStatic =  Mockito.mockStatic(UuidUtils.class);){

            uuidUtilsMockedStatic.when(()-> UuidUtils.isValidUuid(ID)).thenReturn(true);
        }


    }

}
