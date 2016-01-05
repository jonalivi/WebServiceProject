package main;

import account.AccountService;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.log.Log;
import servlets.SignInServlet;
import servlets.SignUpServlet;

/**
 * Created by Надежда Полидорова on 05.01.2016.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        AccountService accountService = new AccountService();
        ServletContextHandler contextHandler
                = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.addServlet(
                new ServletHolder(new SignInServlet(accountService)),
                "/signin"
        );
        contextHandler.addServlet(
                new ServletHolder(new SignUpServlet(accountService)),
                "/signup"
        );
        Server server = new Server(8080);
        server.setHandler(contextHandler);
        server.start();
        Log.getLogger(Main.class).info("Server started");
        server.join();
    }
}
