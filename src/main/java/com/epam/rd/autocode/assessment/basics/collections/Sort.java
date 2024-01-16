package com.epam.rd.autocode.assessment.basics.collections;

import com.epam.rd.autocode.assessment.basics.entity.Book;
import com.epam.rd.autocode.assessment.basics.entity.Order;

import java.util.List;

public interface Sort {
    List<Order> sortOrdersByClientId();

    List<Book> sortBooksByPublishedYear();

    List<Book> sortBooksByPriceDesc();
}
