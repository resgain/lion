package server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class LionServer 
{
	public static void main(String[] args) throws Exception {
//		InetSocketAddress ia = new InetSocketAddress("192.168.1.102", 8080);
		Server server = new Server(8080);
		WebAppContext webAppContext = new WebAppContext();
		webAppContext.setContextPath("/");
		webAppContext.setResourceBase("./web");
		webAppContext.setConfigurationDiscovered(true);
		webAppContext.setParentLoaderPriority(true);
		server.setHandler(webAppContext);
		server.start();
	    server.join();
	}
}