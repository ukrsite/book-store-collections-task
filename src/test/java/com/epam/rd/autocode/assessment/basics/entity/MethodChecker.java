package com.epam.rd.autocode.assessment.basics.entity;

import java.lang.reflect.Method;
import java.util.Arrays;

public class MethodChecker {

    private static boolean isMethodAssignable(Method method, Class<?> aClass) {
        if (aClass.isAssignableFrom(method.getDeclaringClass())) {
            return Arrays.stream(aClass.getDeclaredMethods()).anyMatch(val -> isSameSignature(method, val));
        }
        return false;
    }

    private static boolean isSameSignature(Method method1, Method method2) {
        return method1.getName().equals(method2.getName()) &&
                method1.getReturnType().equals(method2.getReturnType()) &&
                Arrays.equals(method1.getParameterTypes(), method2.getParameterTypes());
    }

    public static boolean isMethodStartsWithAndIsAssignable(Method method, String value, Class<?> clazz) {
        return method.getName().startsWith(value) && isMethodAssignable(method, clazz);
    }
}
