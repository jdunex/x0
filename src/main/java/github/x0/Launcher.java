package github.x0;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

public class Launcher {

	public static void main(String[] args) throws Exception {
		final Server server = new Server(8080);
		server.setHandler(new ServletContextHandler(ServletContextHandler.SESSIONS) {
			{
				final ResourceConfig cfg = new ResourceConfig()
						.property("jersey.config.servlet.filter.forwardOn404", true).packages("github.x0")
						.register(JacksonFeature.class);
				addFilter(new FilterHolder(new ServletContainer(cfg)), "/*", null);
				addServlet(new ServletHolder(new DefaultServlet() {
					public Resource getResource(String path) {
						return Resource.newClassPathResource("WEB-INF/html" + path);
					};
				}), "/*");
			}
		});
		// WebSocketServerContainerInitializer.configureContext((ServletContextHandler)
		// server.getHandler()).addEndpoint(A.class);
		server.start();
		server.join();
	}
}
