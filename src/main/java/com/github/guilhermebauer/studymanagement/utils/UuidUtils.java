package com.github.guilhermebauer.studymanagement.utils;



import com.github.guilhermebauer.studymanagement.exception.UuidUtilsException;

import java.util.UUID;

public class UuidUtils {

    public static final String UUID_REQUIRED = "An UUID need to be informed!";

    public static String generateUuid() {
        return UUID.randomUUID().toString();
    }

    public static boolean isValidUuid(String uuid) throws UuidUtilsException {
        if (uuid == null) {
            throw new UuidUtilsException(UUID_REQUIRED);
        }

        try {
            UUID.fromString(uuid);
            return true;
        } catch (IllegalArgumentException ignored) {

            throw new UuidUtilsException(uuid);
        }
    }
}
