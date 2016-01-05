package main;

import account.AccountService;
import account.UserProfile;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.SessionsServlet;
import servlets.UsersServlet;

/**
 * Created by Надежда Полидорова on 05.01.2016.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        AccountService accService = new AccountService();
        accService.addUser(new UserProfile("admin"));
        accService.addUser(new UserProfile("test"));
        ServletContextHandler contextHandler =
                new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.addServlet(new ServletHolder(new SessionsServlet(accService)),
                "/api/v1/sessions");
        contextHandler.addServlet(new ServletHolder(new UsersServlet(accService)),
                "/api/v1/users");

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase("public_html");

        HandlerList handlerList = new HandlerList();
        handlerList.setHandlers(new Handler[]{resourceHandler, contextHandler});

        Server server = new Server(8080);
        server.setHandler(handlerList);

        server.start();
        server.join();
    }
}
