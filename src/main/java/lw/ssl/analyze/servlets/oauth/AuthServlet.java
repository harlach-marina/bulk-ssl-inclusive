package lw.ssl.analyze.servlets.oauth;

import api.lw.ssl.analyze.enums.oauth.AuthType;
import api.lw.ssl.analyze.oauth.HerokuProviderResource;
import api.lw.ssl.analyze.oauth.LinkedInProviderResource;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.common.OAuthProviderType;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by zmushko_m on 04.05.2016.
 */
@WebServlet(name = "AuthServlet", urlPatterns = {"/AuthServlet"})
public class AuthServlet extends HttpServlet {
    private final static String APP_URL_VAR_NAME = "APP_URL";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AuthType authType = AuthType.getByName(request.getParameter("authOpts"));

        if (authType != null) {
            OAuthClientRequest oAuthRequest = null;
            try {
                if (authType.equals(AuthType.HEROKU)) {
                    oAuthRequest = OAuthClientRequest
                            .authorizationLocation(HerokuProviderResource.AUTZ_ENDPOINT_URL)
                            .setClientId(System.getenv(HerokuProviderResource.HEROKU_OAUTH_ID_NAME))
                            .setRedirectURI(System.getenv(APP_URL_VAR_NAME) + "/LoginServlet/Heroku")
                            .setResponseType(HerokuProviderResource.RESPONSE_TYPE)
                            .setScope(HerokuProviderResource.AUTH_SCOPE)
                            .buildQueryMessage();
                    response.sendRedirect(oAuthRequest.getLocationUri());
                } else if (authType.equals(AuthType.LINKEDIN)) {
                    String state = UUID.randomUUID().toString();
                    HttpSession session = request.getSession();
                    session.setAttribute("state", state);

                    oAuthRequest = OAuthClientRequest
                            .authorizationProvider(OAuthProviderType.LINKEDIN)
                            .setClientId(System.getenv(LinkedInProviderResource.LINKEDIN_OAUTH_ID_NAME))
                            .setRedirectURI(System.getenv(APP_URL_VAR_NAME) + "/LoginServlet/LinkedIn")
                            .setResponseType(LinkedInProviderResource.RESPONSE_TYPE)
                            .setScope(LinkedInProviderResource.AUTH_SCOPE)
                            .setState(state)
                            .buildQueryMessage();
                    response.sendRedirect(oAuthRequest.getLocationUri());
                } else {
                    request.setAttribute("errorMessage", "Invalid authentication type");
                    request.getRequestDispatcher("/login.jsp").forward(request, response);
                }
            } catch (OAuthSystemException e) {
                e.printStackTrace();
            }
        } else {
            request.setAttribute("errorMessage", "Invalid authentication type");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
}
