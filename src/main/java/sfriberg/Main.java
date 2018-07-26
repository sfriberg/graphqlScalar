package sfriberg;

import com.coxautodev.graphql.tools.SchemaParser;
import graphql.schema.GraphQLSchema;
import graphql.servlet.SimpleGraphQLServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;

import java.net.URI;

import javax.servlet.Servlet;

public class Main {

	public static void main(String... argv) throws Exception {
		startServer();
	}

	public static void startServer() throws Exception {

		Server server = new Server(8080);

		Servlet graphqlServlet = SimpleGraphQLServlet.builder(buildSchema()).build();

		ServletContextHandler context = new ServletContextHandler(null, "/");
		URI baseResource = URI.create(Main.class.getResource("/webroot/index.html").toURI()
				.toASCIIString().replaceFirst("/index.html$", "/"));
		context.setBaseResource(Resource.newResource(baseResource));
		context.setWelcomeFiles(new String[] {"index.html"});
		context.addServlet(new ServletHolder(graphqlServlet), "/graphql");
		context.addServlet(new ServletHolder(DefaultServlet.class), "/");
		server.setHandler(context);
		server.start();
		server.join();
	}

	public static GraphQLSchema buildSchema() {
		return SchemaParser.newParser()
				.files("schema.graphqls")
				.resolvers(new Query())
				.scalars(new TestScalarType())
				.build()
				.makeExecutableSchema();
	}
}
