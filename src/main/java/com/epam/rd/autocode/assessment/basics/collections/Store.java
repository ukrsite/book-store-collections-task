package com.epam.rd.autocode.assessment.basics.collections;

import com.epam.rd.autocode.assessment.basics.entity.Book;
import com.epam.rd.autocode.assessment.basics.entity.Client;
import com.epam.rd.autocode.assessment.basics.entity.Order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class Store implements Add, Sort, Find {
    List<Order> orders;
    List<Book> books;
    List<Client> clients;

    @Override
    public void addBook(Book book) {
        if (book == null) {
            throw new NullPointerException("The Book may not be Null");
        }
        books.add(book);
    }

    @Override
    public void addOrder(Order order) {
        if (order == null) {
            throw new NullPointerException("The Order may not be Null");
        }
        orders.add(order);
    }

    @Override
    public Set<String> findAuthors() {
        Set<String> author = new HashSet<>();
        for (Book book : books) {
            author.add(book.getAuthor());
        }
        return author;
    }

    @Override
    public Map<String, List<Order>> findOrdersGroupedByClientId() {
        Map<String, List<Order>> clientsMap = new HashMap<>();

        for (Client client : clients) {
            List<Order> ordersList = new ArrayList<>();
            final long clientId = client.getId();

            for (Order order : orders) {
                final long orderClientId = order.getClientId();

                if (clientId == orderClientId) {
                    ordersList.add(order);
                }
            }
            // Returns a map in which the key is the clientId
            // and the value is a list of the orders of selected client that the store has
            final String strClientId = String.valueOf(clientId);
            clientsMap.put(strClientId, ordersList);
        }
        return clientsMap;
    }

    @Override
    public List<String> findMostPopularAuthors() {
        // Calculates the number of books that has been ordered for each author
        // Map to store the number of orders for each author
        Map<String, Integer> authorOrderCount = new HashMap<>();

        // Count the number of books ordered for each author
        for (Book book : books) {
            final String author = book.getAuthor();
            int count = 0;

            for (Order order : orders) {
                final long bookId = order.getBookId();
                if (book.getId() == bookId) {
                    count += order.getNumberOfBooks();
                }
            }

            // Update the count for the author in the map
            authorOrderCount.put(author, authorOrderCount.getOrDefault(author, 0) + (count));
        }

        // Create a list of authors and sort it manually by the number of orders in descending order
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(authorOrderCount.entrySet());
        entries.sort(new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
                return e2.getValue().compareTo(e1.getValue());
            }
        });

        // Extract the sorted author names into a list
        List<String> mostPopularAuthors = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : entries) {
            mostPopularAuthors.add(entry.getKey());
        }

        return mostPopularAuthors;
    }

    @Override
    public List<Book> findBooksWhichPublishedAfterSelectedDate(LocalDate date) {
        // Returns a list of books whose published date is after the date parameter
        List<Book> list = new ArrayList<>();
        for (Book book : books) {
            final LocalDate publicationDate = book.getPublicationDate();
            if (publicationDate.isAfter(date)) {
                list.add(book);
            }
        }
        return list;
    }

    @Override
    public List<Book> findBooksInPriceRange(BigDecimal min, BigDecimal max) {
        List<Book> list = new ArrayList<>();
        // Returns a list of books whose price within a range provided by min and max parameters
        for (Book book : books) {
            final BigDecimal price = book.getPrice();
            if (min.compareTo(price) <= 0 && max.compareTo(price) >=0) {
                list.add(book);
            }
        }
        return list;
    }

    @Override
    public Set<Client> findClientsWithAveragePriceNoLessThan(List<Client> clients, int average) {
        // Calculates the average number of orders for each client and returns a set of clients
        // whose average order amount is not less than the average
        Set<Client> set = new HashSet<>();
        for (Client client : clients) {
            final long clientId = client.getId();
            int count = 0;
            int amount = 0;

            for (Order order : orders) {
                final long id = order.getClientId();
                final int price = order.getPrice().intValue();

                if (clientId == id) {
                    count++;
                    amount += price ;
                }
            }

            int averageOrderAmount = 0;
            if (count > 0) {
                averageOrderAmount = amount / count;
            }
            if (averageOrderAmount > average) {
                set.add(client);
            }
        }
        return set;
    }

    @Override
    public Set<Order> findOrdersByDate(LocalDateTime dateTime) {
        // Returns a set of orders that has the same orderDate as the dateTime parameter
        Set<Order> set = new HashSet<>();
        for (Order order : orders) {
            final LocalDateTime orderDate = order.getOrderDate();
            if (orderDate.isEqual(dateTime)) {
                set.add(order);
            }
        }
        return set;
    }

    @Override
    public List<Order> sortOrdersByClientId() {
        // ??? Returns all orders sorted by clientId as a new list
        if (orders == null) {
            return null;
        }

        // Create a copy of the orders list to sort
        List<Order> sortedOrders = new ArrayList<>(orders);

        // Implementing a simple bubble sort to sort orders by clientId
        for (int i = 0; i < sortedOrders.size() - 1; i++) {
            for (int j = 0; j < sortedOrders.size() - 1 - i; j++) {
                if (sortedOrders.get(j).getClientId() > sortedOrders.get(j + 1).getClientId()) {
                    // Swap the orders if they are in the wrong order
                    Order temp = sortedOrders.get(j);
                    sortedOrders.set(j, sortedOrders.get(j + 1));
                    sortedOrders.set(j + 1, temp);
                }
            }
        }

        return sortedOrders;
    }

    @Override
    public List<Book> sortBooksByPublishedYear() {
        if (books == null) {
            return null;
        }

        // Create a copy of the books list to sort
        List<Book> sortedBooks = new ArrayList<>(books);

        // Implementing a simple bubble sort to sort books by publication date
        for (int i = 0; i < sortedBooks.size() - 1; i++) {
            for (int j = 0; j < sortedBooks.size() - 1 - i; j++) {
                if (sortedBooks.get(j).getPublicationDate().isAfter(sortedBooks.get(j + 1).getPublicationDate())) {
                    // Swap the books if they are in the wrong order
                    Book temp = sortedBooks.get(j);
                    sortedBooks.set(j, sortedBooks.get(j + 1));
                    sortedBooks.set(j + 1, temp);
                }
            }
        }

        return sortedBooks;
    }

    @Override
    public List<Book> sortBooksByPriceDesc() {
        if (books == null) {
            return null;
        }

        // Create a copy of the books list to sort
        List<Book> sortedBooks = new ArrayList<>(books);

        // Implementing a simple bubble sort to sort books by price in descending order
        for (int i = 0; i < sortedBooks.size() - 1; i++) {
            for (int j = 0; j < sortedBooks.size() - 1 - i; j++) {
                if (sortedBooks.get(j).getPrice().compareTo(sortedBooks.get(j + 1).getPrice()) < 0) {
                    // Swap the books if they are in the wrong order (ascending)
                    Book temp = sortedBooks.get(j);
                    sortedBooks.set(j, sortedBooks.get(j + 1));
                    sortedBooks.set(j + 1, temp);
                }
            }
        }

        return sortedBooks;
    }


}
