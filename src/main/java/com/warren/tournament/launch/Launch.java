package com.warren.tournament.launch;

import com.warren.tournament.servlet.Demo;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 *
 * This class launches the web application in an embedded Jetty container.
 * This is the entry point to your application. The Java command that is used for
 * launching should fire this main method.
 *
 */
public class Launch {
	
    private static Server server;			// Jetty
    private static WebAppContext root;		// Jetty 
	
	public static void startWebServer() throws Exception {
//        Server server = new Server();
//        ServerConnector connector = new ServerConnector(server);
//        connector.setPort(8080);
//        server.setConnectors(new Connector[] { connector });
//        ServletContextHandler context = new ServletContextHandler();
//        context.setContextPath("/");
//        context.addServlet(Demo.class, "/demo.htm");
//        HandlerCollection handlers = new HandlerCollection();
//        handlers.setHandlers(new Handler[] { context, new DefaultHandler() });
//        server.setHandler(handlers);
//        server.start();
//        server.join();
//        if(true)
//        	return;

    	String webappDirLocation = "src/main/webapp/";
        // The port that we should run on can be set into an environment variable
        // Look for that variable and default to 8080 if it isn't there.
        String webPort = System.getenv("PORT");
        if (webPort == null || webPort.isEmpty()) {
            webPort = "8080";
        }

        server = new Server(Integer.valueOf(webPort));
        root = new WebAppContext();
        root.setContextPath("/");
        root.setDescriptor(webappDirLocation + "/WEB-INF/web.xml");
        root.setResourceBase(webappDirLocation);
        root.setCopyWebDir(true);
        root.setCopyWebInf(true);

        // Parent loader priority is a class loader setting that Jetty accepts.
        // By default Jetty will behave like most web containers in that it will
        // allow your application to replace non-server libraries that are part of the
        // container. Setting parent loader priority to true changes this behavior.
        // Read more here: http://wiki.eclipse.org/Jetty/Reference/Jetty_Classloading
        root.setParentLoaderPriority(true);

        server.setHandler(root);
        server.start();
        server.join();		
	}
	
	public static void stopWebServer() throws Exception {
		root.stop();
        server.stop();
	}
	
    /**
     * @param args
     */
    public static void main(String[] args) throws Exception{        
    	startWebServer();
    }

}
