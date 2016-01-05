package servlets;

import account.AccountService;
import account.UserProfile;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Надежда Полидорова on 05.01.2016.
 */
public class UsersServlet extends HttpServlet {
    private final AccountService accountService;

    public UsersServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    // change profile
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sessionId = req.getSession().getId();
        UserProfile profile = accountService.getUserBySessionId(sessionId);
        resp.setContentType("text/html;charset=utf-8");
        if (profile == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        String login = profile.getLogin();
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        accountService.deleteUser(sessionId);
        profile = new UserProfile(login, password, email);
        accountService.addUser(profile);
        accountService.addSession(sessionId, profile);
        Gson gson = new Gson();
        String json = gson.toJson(profile);
        resp.getWriter().println(json);
        resp.setStatus(HttpServletResponse.SC_OK);
    }


    // get public profile
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserProfile profile = accountService.getUserBySessionId(req.getSession().getId());
        resp.setContentType("text/html;charset=utf-8");
        if (profile == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            Gson gson = new Gson();
            String json = gson.toJson(profile);
            resp.getWriter().println(json);
            resp.setStatus(HttpServletResponse.SC_OK);
        }
    }

    // sign up
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        resp.setContentType("text/html;charset=utf-8");
        if (login == null || password == null || email == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        UserProfile profile = accountService.getUserByLogin(login);
        if (profile == null) {
            accountService.addUser(new UserProfile(login, password, email));
            resp.getWriter().println("You are registered as " + login);
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    // unregister
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sessionId = req.getSession().getId();
        UserProfile profile = accountService.getUserBySessionId(sessionId);
        resp.setContentType("text/html;charset=utf-8");
        if (profile == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            accountService.deleteUser(sessionId);
            resp.getWriter().println("You are unregistered. Goodbye!");
            resp.setStatus(HttpServletResponse.SC_OK);
        }
    }
}
