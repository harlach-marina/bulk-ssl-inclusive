package lw.ssl.analyze.servlets;

import lw.ssl.analyze.utils.PropertyFilesHelper;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import javax.servlet.http.Cookie;

/**
 * Created by a.bukov on 22.04.2016.
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String PASSWORDS_PROPERTIES_SERVLET_CONTENT_PATH = "/WEB-INF/properties/passwords.properties";

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        // get request parameters for userID and password
        String user = request.getParameter("user");
        String pwd = request.getParameter("pwd");
        final Properties passwords = PropertyFilesHelper.getPropertyByPath(PASSWORDS_PROPERTIES_SERVLET_CONTENT_PATH, getServletContext());

        for(String login : passwords.stringPropertyNames()){
            if(login.equals(user) && passwords.getProperty(login).equals(pwd)){
                HttpSession session = request.getSession();
                session.setAttribute("user", login);
                //setting session to expiry in 30 mins
                session.setMaxInactiveInterval(30 * 60);
                Cookie userName = new Cookie("user", user);
                userName.setMaxAge(30 * 60);
                response.addCookie(userName);
                response.sendRedirect("index.jsp");
                return;
            }
        }
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
        PrintWriter out= response.getWriter();
        out.println("<font color=red>Entered user name or password is wrong.</font>");
        rd.include(request, response);
    }

}
