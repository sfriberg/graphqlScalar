package sfriberg;

import static java.util.Objects.isNull;

import graphql.language.ObjectValue;
import graphql.language.StringValue;
import graphql.language.Value;
import graphql.schema.Coercing;
import graphql.schema.GraphQLScalarType;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class TestScalarType extends GraphQLScalarType {

	public TestScalarType() {
		super("Test", "Test", new TestCoercing());
	}

	private static class TestCoercing implements Coercing<String, Object> {

		@Override
		public Object serialize(Object input) {
			return null;
		}

		@Override
		public String parseValue(Object input) {
			return parseLiteral(input);
		}

		@SuppressWarnings("squid:S1166")
		@Override
		public String parseLiteral(Object input) {
			if (input instanceof ObjectValue) {
				ObjectValue object = (ObjectValue) input;
				Value<?> value = object.getObjectFields().stream()
						.filter(field -> field.getName().equals("test"))
						.findFirst()
						.map(field -> field.getValue())
						.orElse(null);
				if (isNull(value)) {
					return null;
				} else if (value instanceof StringValue) {
					return ((StringValue) value).getValue();
				} else {
					LoggerFactory.getLogger(TestCoercing.class).error("Unexpected value type: " + value.getClass().getSimpleName());
					return null;
				}
			}
			return null;
		}
	}
}
