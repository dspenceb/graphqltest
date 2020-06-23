package dspenceb.graphqltest;

import graphql.schema.DataFetcher;
import graphql.schema.GraphQLArgument;
import graphql.schema.GraphQLDirective;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLFieldsContainer;
import graphql.schema.idl.SchemaDirectiveWiring;
import graphql.schema.idl.SchemaDirectiveWiringEnvironment;

import java.util.List;
import java.util.Optional;

public class AllowedDirective implements SchemaDirectiveWiring {
    @Override
    public GraphQLFieldDefinition onField(SchemaDirectiveWiringEnvironment<GraphQLFieldDefinition> environment) {

        GraphQLFieldDefinition field = environment.getElement();
        GraphQLFieldsContainer parentType = environment.getFieldsContainer();
        DataFetcher originalDataFetcher = environment.getCodeRegistry().getDataFetcher(parentType, field);
        DataFetcher authDataFetcher = dataFetchingEnvironment -> {

            GraphQLDirective allowedDirective = environment.getDirective();
            Optional<GraphQLArgument> allowedArgument = Optional
                    .ofNullable(allowedDirective.getArgument("allowed"));

            AllowedState allowed = dataFetchingEnvironment.getContext();

            // AuthState is not a required argument, if it does not exist, this check should
            // be true.
            boolean allowedCheck = allowedArgument.map(GraphQLArgument::getValue)
                    .map(allowedStates -> doAllowedCheck(allowed, (List<AllowedState>) allowedStates)).orElse(true);

            if (allowedCheck) {
                return originalDataFetcher.get(dataFetchingEnvironment);
            } else {
                return null;
            }
        };

        environment.getCodeRegistry().dataFetcher(parentType, field, authDataFetcher);
        return field;
    }

    private boolean doAllowedCheck(AllowedState allowed, List<AllowedState> allowedStates) {
        return allowedStates.stream()
                .anyMatch(allowedState -> allowedState.equals(allowed));
    }
}