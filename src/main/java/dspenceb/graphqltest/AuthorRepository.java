package dspenceb.graphqltest;

public class AuthorRepository {

    public Author findById(int authorId) {
        return new Author(authorId, "dspenceb");
    }
}
