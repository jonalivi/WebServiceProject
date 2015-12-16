package main;

import org.eclipse.jetty.http.HttpParser;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.log.Log;
import servlets.GetParamServlet;

public class Main {
    public static void main(String[] args) {
        GetParamServlet servlet = new GetParamServlet();
        ServletContextHandler handler =
                new ServletContextHandler(ServletContextHandler.SESSIONS);
        handler.addServlet(new ServletHolder(servlet), "/mirror");
        Server server = new Server(8080);
        server.setHandler(handler);
        try {
            server.start();
            Log.getLogger(Main.class).info("Server started");
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}