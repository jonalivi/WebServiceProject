package servlets;

import account.AccountService;
import account.UserProfile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This servlet handles users' logging-in.
 * If valid login and pass are sent, it responds with code 200,
 * otherwise with 401.
 *
 * Created by Надежда Полидорова on 05.01.2016.
 */
public class SignInServlet extends HttpServlet {
    private final AccountService accountService;

    public SignInServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        resp.setContentType("text/html;charset=utf-8");
        if (login == null || password == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        UserProfile userProfile = accountService.getUserByLogin(login);
        if (userProfile != null && userProfile.getPassword().equals(password)) {
            resp.getWriter().println("Authorized");
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            resp.getWriter().println("Unauthorized");
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
