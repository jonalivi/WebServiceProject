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
public class SessionsServlet extends HttpServlet {
    private final AccountService accountService;

    public SessionsServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sessionId = req.getSession().getId();
        UserProfile user = accountService.getUserBySessionId(sessionId);
        resp.setContentType("text/html;charset=utf-8");
        if (user == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            Gson gson = new Gson();
            String json = gson.toJson(user);
            resp.getWriter().println(json);
            resp.setStatus(HttpServletResponse.SC_OK);
        }
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

        UserProfile profile = accountService.getUserByLogin(login);
        if (profile == null || !profile.getPassword().equals(password)) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        accountService.addSession(req.getSession().getId(), profile);
        Gson gson = new Gson();
        String json = gson.toJson(profile);
        resp.getWriter().println(json);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sessionId = req.getSession().getId();
        UserProfile profile = accountService.getUserBySessionId(sessionId);
        resp.setContentType("text/html;charset=utf-8");
        if (profile == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            accountService.deleteSession(sessionId);
            resp.getWriter().println("Goodbye!");
            resp.setStatus(HttpServletResponse.SC_OK);
        }
    }
}
