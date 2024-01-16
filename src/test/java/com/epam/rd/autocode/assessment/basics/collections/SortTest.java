package com.epam.rd.autocode.assessment.basics.collections;

import com.epam.rd.autocode.assessment.basics.entity.Book;
import com.epam.rd.autocode.assessment.basics.entity.Order;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.epam.rd.autocode.assessment.basics.entity.MethodChecker.isMethodStartsWithAndIsAssignable;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SortTest {

    private static Store store;

    @BeforeAll
    static void testsOverrideSortMethodStore() {
        long numberOfFindOverrideMethods = Arrays.stream(Store.class.getDeclaredMethods())
                .filter(val -> isMethodStartsWithAndIsAssignable(val, "sort", Sort.class))
                .count();
        assertEquals(3, numberOfFindOverrideMethods,
                "Sort methods of Store has not implemented right");
    }


    @BeforeAll
    static void setGlobal() throws Exception {
        store = new Store();
        store.orders = CSVOperator.readDataFromCSV(Order.class, "src/test/resources/order.csv");
        store.books = CSVOperator.readDataFromCSV(Book.class, "src/test/resources/book.csv");
    }

    @Test
    @DisplayName("Method sortOrderByClientId() successfully launched")
    void testsSortOrdersByClientId() throws Exception {
        List<Order> expected = CSVOperator.readDataFromCSV(Order.class, "src/test/resources/sort/sorted-order-by-clientId.csv");
        assertEquals(expected, new ArrayList<>(store.sortOrdersByClientId()),
                "Sort method is not sorted actual orders right. Check your realization.");
    }

    @Test
    @DisplayName("Method sortBooksByPublishedYear() successfully launched")
    void testSortBooksByPublishedYear() throws Exception {
        List<Book> expected = CSVOperator.readDataFromCSV(Book.class, "src/test/resources/sort/book-s-published-year.csv");
        assertEquals(expected, new ArrayList<>(store.sortBooksByPublishedYear()),
                "Sort method is not sorted actual books right. Check your realization.");
    }

    @Test
    @DisplayName("Method sortBooksByPriceDesc() successfully launched")
    void testSortBooksByPriceDesc() throws Exception {
        List<Book> expected = CSVOperator.readDataFromCSV(Book.class, "src/test/resources/sort/book-s-price-desc.csv");
        assertEquals(expected, new ArrayList<>(store.sortBooksByPriceDesc()),
                "Sort method is not sorted actual books right. Check your realization. [Desc order maybe?]");
    }

}
