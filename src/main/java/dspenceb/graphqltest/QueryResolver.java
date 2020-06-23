package dspenceb.graphqltest;

import graphql.kickstart.tools.GraphQLQueryResolver;

import java.util.List;

public class QueryResolver implements GraphQLQueryResolver {

    private BookRepository bookRepository;

    public QueryResolver(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> books() {
        return bookRepository.findAll();
    }
}
