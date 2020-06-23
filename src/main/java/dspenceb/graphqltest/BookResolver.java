package dspenceb.graphqltest;

import graphql.kickstart.tools.GraphQLResolver;

public class BookResolver implements GraphQLResolver<Book> /* This class is a resolver for the Book "Data Class" */ {

    private AuthorRepository authorRepository;

    public BookResolver(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author author(Book book) {
        return authorRepository.findById(book.getAuthorId());
    }
}
