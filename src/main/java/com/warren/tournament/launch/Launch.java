package com.warren.tournament.launch;

import org.eclipse.jetty.server.Server;
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

    	String webappDirLocation = "src/main/webapp/";
        server = new Server(Integer.valueOf("8080"));
        root = new WebAppContext();
        root.setContextPath("/tournament/builder");
        root.setDescriptor(webappDirLocation + "/WEB-INF/web.xml");
        root.setResourceBase(webappDirLocation);

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
