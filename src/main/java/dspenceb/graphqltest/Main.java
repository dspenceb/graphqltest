package dspenceb.graphqltest;

import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.kickstart.tools.SchemaParser;
import graphql.schema.GraphQLSchema;

public class Main {
    public static void main(String[] args) {
        GraphQLSchema schema = SchemaParser.newParser()
                .file("schema.graphqls")

                .resolvers(new QueryResolver(new BookRepository()), new BookResolver(new AuthorRepository()))
                .directive("allowed", new AllowedDirective())
                .build()
                .makeExecutableSchema();

        GraphQL graphQl = GraphQL.newGraphQL(schema).build();


        ExecutionInput input = ExecutionInput.newExecutionInput(query).context(AllowedState.ALLOWED).build();
        ExecutionResult result = graphQl.execute(input);

        System.out.println(result.getData().toString());
    }

    private static String query = "query {\n" +
            "    books {\n" +
            "        id\n" +
            "        name\n" +
            "    }\n" +
            "}";
}
