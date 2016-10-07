package lw.ssl.analyze.servlets;

/**
 * Created by a.bukov on 21.07.2016.
 */

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HSTSFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletResponse resp = (HttpServletResponse) res;

        resp.setHeader("Strict-Transport-Security", "max-age=31622400; includeSubDomains; preload");
        resp.setHeader("X-Frame-Options", "DENY");
        resp.setHeader("X-XSS-Protection", "1; mode=block");
//        resp.setHeader("Content-Security-Policy", "default-src 'none'; script-src 'self'; connect-src 'self'; img-src 'self'; style-src 'self'; font-src 'self' data: maxcdn.bootstrapcdn.com;");
        resp.setHeader("X-Content-Security-Policy", "allow 'self';");
        resp.setHeader("X-Content-Type-Options", "nosniff");
        resp.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate, no-transform, max-age=0");
        resp.setHeader("Pragma", "no-cache");

        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {

    }
}
