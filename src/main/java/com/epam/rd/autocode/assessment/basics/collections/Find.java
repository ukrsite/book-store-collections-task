package com.epam.rd.autocode.assessment.basics.collections;

import com.epam.rd.autocode.assessment.basics.entity.Book;
import com.epam.rd.autocode.assessment.basics.entity.Client;
import com.epam.rd.autocode.assessment.basics.entity.Order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Find {
    Set<String> findAuthors();

    Map<String, List<Order>> findOrdersGroupedByClientId();

    List<String> findMostPopularAuthors();

    List<Book> findBooksWhichPublishedAfterSelectedDate(LocalDate date);

    List<Book> findBooksInPriceRange(BigDecimal min, BigDecimal max);

    Set<Client> findClientsWithAveragePriceNoLessThan(List<Client> clients, int average);

    Set<Order> findOrdersByDate(LocalDateTime dateTime);
}
