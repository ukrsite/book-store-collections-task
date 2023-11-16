package com.epam.rd.autocode.assessment.basics.collections;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.opencsv.CSVReader;

import java.io.Reader;
import java.lang.reflect.Constructor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CSVOperator {
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    private static Object toObject(Class<?> clazz, String value) {
        if (boolean.class == clazz) return Boolean.parseBoolean(value);
        if (byte.class == clazz) return Byte.parseByte(value);
        if (short.class == clazz) return Short.parseShort(value);
        if (int.class == clazz) return Integer.parseInt(value);
        if (long.class == clazz) return Long.parseLong(value);
        if (float.class == clazz) return Float.parseFloat(value);
        if (double.class == clazz) return Double.parseDouble(value);
        if (char.class == clazz) return value.charAt(0);
        return value;
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> readDataFromCSV(Class<T> clazz, String filePath) throws Exception {
        List<T> list = new ArrayList<>();

        Constructor<?> constr = Arrays.stream(clazz.getConstructors())
                .filter(c -> c.getParameterTypes().length > 0)
                .findFirst()
                .orElseThrow(() -> new NoSuchMethodException("No suitable constructor found for class " + clazz.getName()));

        Class<?>[] types = constr.getParameterTypes();
        Path path = Paths.get(filePath);

        try (Reader reader = Files.newBufferedReader(path);
             CSVReader csvReader = new CSVReader(reader)) {

            csvReader.skip(1); // Skip header
            List<String[]> lines = csvReader.readAll();

            for (String[] fields : lines) {
                Object[] args = new Object[types.length];
                for (int j = 0; j < types.length; j++) {
                    args[j] = convertField(types[j], fields[j]);
                }
                list.add((T) constr.newInstance(args));
            }
        }

        return list;
    }

    private static Object convertField(Class<?> type, String value) throws Exception {
        if (type.isPrimitive()) {
            return toObject(type, value);
        } else if (type.isEnum()) {
            return type.getMethod("valueOf", String.class).invoke(null, value);
        } else if (type == LocalDateTime.class) {
            return LocalDateTime.parse(value, dateTimeFormatter);
        } else if (List.class.isAssignableFrom(type)) {
            return parseList(value);
        } else if (Set.class.isAssignableFrom(type)) {
            return parseSet(value);
        } else if (type == LocalDate.class) {
            return LocalDate.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } else {
            return type.getConstructor(String.class).newInstance(value);
        }
    }

    private static List<?> parseList(String listString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JavaType elementType = objectMapper.getTypeFactory().constructType(Object.class);
            CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(List.class, elementType);
            return objectMapper.readValue(listString, listType);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error parsing List from JSON-like string: " + listString, e);
        }
    }

    private static Set<?> parseSet(String setString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JavaType elementType = objectMapper.getTypeFactory().constructType(Object.class);
            CollectionType setType = objectMapper.getTypeFactory().constructCollectionType(Set.class, elementType);
            return objectMapper.readValue(setString, setType);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error parsing Set from JSON-like string: " + setString, e);
        }
    }
}
