package com.epam.rd.autocode.assessment.basics.collections;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.lang.reflect.Method;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeclarationTest {

    @ParameterizedTest
    @DisplayName("Store has all the methods that was declared")
    @CsvFileSource(files = "src/test/resources/methods/store-methods.csv", numLinesToSkip = 1)
    void testsStoreMethodsExistence(String expected) {
        String actual = Arrays.stream(Store.class.getDeclaredMethods())
                .filter(content -> content.toString().equals(expected))
                .findFirst()
                .map(Method::toString)
                .orElse("");
        assertEquals(expected, actual,
                "Some methods of Store are not declared");
    }

    @ParameterizedTest
    @DisplayName("Add has all the methods that was declared")
    @CsvFileSource(files = "src/test/resources/methods/add-methods.csv", numLinesToSkip = 1)
    void testsAddMethodsExistence(String expected) {
        String actual = Arrays.stream(Add.class.getDeclaredMethods())
                .filter(content -> content.toString().equals(expected))
                .findFirst()
                .map(Method::toString)
                .orElse("");
        assertEquals(expected, actual,
                "Some methods of Add interface are not declared");
    }

    @ParameterizedTest
    @DisplayName("Sort has all the methods that was declared")
    @CsvFileSource(files = "src/test/resources/methods/sort-methods.csv", numLinesToSkip = 1)
    void testsSortMethodsExistence(String expected) {
        String actual = Arrays.stream(Sort.class.getDeclaredMethods())
                .filter(content -> content.toString().equals(expected))
                .findFirst()
                .map(Method::toString)
                .orElse("");
        assertEquals(expected, actual,
                "Some methods of Sort interface are not declared");
    }

    @ParameterizedTest
    @DisplayName("Find has all the methods that was declared")
    @CsvFileSource(files = "src/test/resources/methods/find-methods.csv", numLinesToSkip = 1)
    void testsFindMethodsExistence(String expected) {
        String actual = Arrays.stream(Find.class.getDeclaredMethods())
                .filter(content -> content.toString().equals(expected))
                .findFirst()
                .map(Method::toString)
                .orElse("");
        assertEquals(expected, actual,
                "Some methods of Find interface are not declared");
    }

}
