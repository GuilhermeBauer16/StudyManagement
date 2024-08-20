package com.github.guilhermebauer.studymanagement.mapper;

import java.lang.reflect.Field;

public class BuildMapper {

    public BuildMapper() {
    }

    public static <T, S> T parseObject(T target, S source) throws IllegalAccessException, NoSuchFieldException {

        Class<?> targetClass = target.getClass();
        Class<?> sourceClass = source.getClass();
        Field[] sourceFields = sourceClass.getDeclaredFields();


        for (Field sourceField : sourceFields) {

            sourceField.setAccessible(true);
            Object value = sourceField.get(source);

            if (value != null && !sourceField.getName().equals("serialVersionUID")) {
                try {
                    Field targetField = targetClass.getDeclaredField(sourceField.getName());
                    targetField.setAccessible(true);
                    targetField.set(target, value);

                } catch (NoSuchFieldException ignored) {

                }

            }

        }

        return target;
    }
}
