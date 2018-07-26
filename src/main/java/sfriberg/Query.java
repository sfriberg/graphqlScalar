package sfriberg;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;

public class Query implements GraphQLQueryResolver {

	public String test(String test, DataFetchingEnvironment env) {
		return test;
	}
}
