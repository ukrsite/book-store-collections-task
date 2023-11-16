package com.epam.rd.autocode.assessment.basics.collections;

import com.epam.rd.autocode.assessment.basics.entity.Book;
import com.epam.rd.autocode.assessment.basics.entity.Order;
import com.epam.rd.autocode.assessment.basics.entity.enums.AgeGroup;
import com.epam.rd.autocode.assessment.basics.entity.enums.Language;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddTest {

    private List<Book> books;
    private List<Order> orders;

    @BeforeEach
    void launch() {
        books = new ArrayList<>();
        orders = new ArrayList<>();
    }

    @Test
    @DisplayName("Tested book list if it's empty")
    void testAddBookZero() {
        assertEquals(0, books.size(),
                "Number of books is not equal to 0");
    }

    @Test
    @DisplayName("Tested book list if it's have some content")
    void testAddBook() {
        books.add(new Book(1L, "name", "genre", AgeGroup.ADULT, BigDecimal.TEN, LocalDate.EPOCH, "author", 121, "characteristics", "description", Language.ENGLISH));
        assertEquals(1, books.size(),
                "Number of books is different than expected: 1");
    }

    @Test
    @DisplayName("Tested order list if it's empty")
    void testAddOrderZero() {
        assertEquals(0, orders.size(),
                "Number of orders is not equal to 0");
    }

    @Test
    @DisplayName("Tested order list if it's have some content")
    void testAddOrder() {
        orders.add(new Order(1L, 1L, 1L, 1L, 12, LocalDateTime.now(), BigDecimal.TEN));
        assertEquals(1, orders.size(),
                "Number of orders is different than expected: 1");
    }
}
