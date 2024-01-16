package com.epam.rd.autocode.assessment.basics.collections;

import com.epam.rd.autocode.assessment.basics.entity.Book;
import com.epam.rd.autocode.assessment.basics.entity.Client;
import com.epam.rd.autocode.assessment.basics.entity.Order;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.epam.rd.autocode.assessment.basics.entity.MethodChecker.isMethodStartsWithAndIsAssignable;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FindTest {

    private static Store store;

    @BeforeAll
    static void testsOverrideFindMethodStore() {
        long numberOfFindOverrideMethods = Arrays.stream(Store.class.getDeclaredMethods())
                .filter(val -> isMethodStartsWithAndIsAssignable(val, "find", Find.class))
                .count();
        assertEquals(7, numberOfFindOverrideMethods,
                "Find methods of Store has not implemented right");
    }

    @BeforeAll
    static void setGlobal() throws Exception {
        store = new Store();
        store.books = CSVOperator.readDataFromCSV(Book.class, "src/test/resources/book.csv");
        store.orders = CSVOperator.readDataFromCSV(Order.class, "src/test/resources/order.csv");
        store.clients = CSVOperator.readDataFromCSV(Client.class, "src/test/resources/client.csv");
    }

    @Test
    @DisplayName("Method findAuthors() successfully launched")
    void findAuthors() throws Exception {
        Set<String> expected = CSVOperator.readDataFromCSV(String.class, "src/test/resources/find/authors.csv")
                .stream()
                .collect(Collectors.toUnmodifiableSet());
        assertEquals(expected, store.findAuthors(),
                "List of actual authors are not equal to the expected one");
    }

    @ParameterizedTest
    @DisplayName("Method findOrdersGroupedByClientId() successfully launched")
    @CsvFileSource(resources = "/find/orders-grouped-by-clientId.csv")
    void findOrdersGroupedByClientId(String position, String expected) {
        assertEquals(expected, store.findOrdersGroupedByClientId()
                        .get(position)
                        .toString(),
                "List of actual orders by clientId are not equal to the expected one");
    }

    @ParameterizedTest
    @DisplayName("Method findMostPopularAuthors() successfully launched")
    @CsvFileSource(resources = "/find/popular-authors.csv")
    void findMostPopularAuthors(int position, String expected) {
        assertEquals(expected, store.findMostPopularAuthors()
                        .get(position),
                "Actual author is not valid to expected one! [#position of author in the list might be another than expected]");
    }

    @Test
    @DisplayName("Method findBooksWhichPublishedAfterSelectedDate() successfully launched")
    void findBooksWhichPublishedAfterSelectedDate() throws Exception {
        List<Book> expected = CSVOperator.readDataFromCSV(Book.class, "src/test/resources/find/books-after-selected-date.csv");
        assertEquals(expected, store.findBooksWhichPublishedAfterSelectedDate(LocalDate.of(2022, 1, 18)),
                "List of books which published after selected date are not valid.");
    }

    @Test
    @DisplayName("Method findBooksInPriceRange() successfully launched")
    void findBooksInPriceRange() throws Exception {
        List<Book> expected = CSVOperator.readDataFromCSV(Book.class, "src/test/resources/find/books-in-price-range.csv");
        assertEquals(expected, store.findBooksInPriceRange(BigDecimal.valueOf(12), BigDecimal.valueOf(14)),
                "Books that are found are not valid due to value of price in range between Min and Max values");
    }

    @Test
    @DisplayName("Method findClientsWithAveragePriceNoLessThan() successfully launched")
    void findClientsWithAveragePriceNoLessThan() throws Exception {
        List<Client> expected = CSVOperator.readDataFromCSV(Client.class, "src/test/resources/find/client-with-average-price.csv");
        assertEquals(expected, store.findClientsWithAveragePriceNoLessThan(store.clients, 90)
                        .stream()
                        .toList(),
                "Clients that are found are not valid due to value of average price");
    }

    @Test
    @DisplayName("Method findOrdersByDate() successfully launched")
    void findOrdersByDate() throws Exception {
        List<Order> expected = CSVOperator.readDataFromCSV(Order.class, "src/test/resources/find/orders-by-date.csv");
        assertEquals(expected, store.findOrdersByDate(LocalDateTime.of(2023, 11, 13, 12, 34, 56))
                        .stream()
                        .toList(),
                "Orders that are found are not valid due to selected date");
    }
}
