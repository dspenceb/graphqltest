package dspenceb.graphqltest;

import java.util.ArrayList;
import java.util.List;

public class BookRepository {
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        books.add(new Book(1, "Harry Potter", 1));
        books.add(new Book(2, "1984", 2));
        return books;
    }
}
