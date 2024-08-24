package com.github.guilhermebauer.studymanagement.mapper;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public static <T, S> List<T> parseObjectList(List<T> targetList, List<S> sourceList) throws IllegalAccessException {
        if (targetList.isEmpty() || sourceList.isEmpty()) {
            return targetList;
        }

        Class<?> sourceClass = sourceList.get(0).getClass();
        Map<String, Field> sourceFieldMap = getFieldMap(sourceClass);

        for (int i = 0; i < sourceList.size(); i++) {
            S source = sourceList.get(i);
            T target = targetList.get(i);

            for (Map.Entry<String, Field> entry : sourceFieldMap.entrySet()) {
                String fieldName = entry.getKey();
                Field sourceField = entry.getValue();

                if (sourceField != null) {
                    sourceField.setAccessible(true);
                    Object value = sourceField.get(source);

                    if (value != null && !fieldName.equals("serialVersionUID")) {
                        try {
                            Field targetField = target.getClass().getDeclaredField(fieldName);
                            targetField.setAccessible(true);
                            targetField.set(target, value);
                        } catch (NoSuchFieldException e) {

                        }
                    }
                }
            }
        }

        return targetList;
    }

    private static Map<String, Field> getFieldMap(Class<?> clazz) {
        Map<String, Field> fieldMap = new HashMap<>();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            if (!field.getName().equals("serialVersionUID")) {
                fieldMap.put(field.getName(), field);
            }
        }

        return fieldMap;
    }
}



