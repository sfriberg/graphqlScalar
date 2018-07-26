# graphqlScalar

To run: gradle run

To reproduce issue go to Graphiql on http://localhost:8080

This query should work and return the String "ok"

    query testOk {
      test(test: {test: "ok"} )
    }

This query will fail

    query testFail($value: String) {
      test(test: {test: $value} )
    }

    {"value": "fail" }