package lw.ssl.analyze.servlets;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by a.bukov on 22.04.2016.
 */
@WebFilter("/AuthenticationFilter")
public class AuthenticationFilter implements Filter {

    private ServletContext context;

    public void init(FilterConfig fConfig) throws ServletException {
        this.context = fConfig.getServletContext();
        this.context.log("AuthenticationFilter initialized");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        this.context.log("Requested Resource::"+uri);

        HttpSession session = req.getSession(false);
        if((session == null || session.getAttribute("user") == null || session.getAttribute("eMail") == null) &&
                !(uri.endsWith("login.jsp") || uri.endsWith("LoginServlet/Heroku") || uri.endsWith("LoginServlet/LinkedIn") || uri.endsWith("AuthServlet") ||
                        uri.endsWith("/auth/heroku") || uri.startsWith("/css") || uri.startsWith("/js") || uri.startsWith("/img"))){
            this.context.log("Unauthorized access request");
            res.sendRedirect("/login.jsp");
        }else{
            // pass the request along the filter chain
            chain.doFilter(request, response);
        }
    }

    public void destroy() {
        //close any resources here
    }

}
