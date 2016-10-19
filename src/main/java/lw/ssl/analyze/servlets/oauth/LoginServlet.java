package lw.ssl.analyze.servlets.oauth;

import api.lw.ssl.analyze.enums.oauthp.AuthType;
import lw.ssl.analyze.report.PdfReportService;
import lw.ssl.analyze.utils.external.DnsSecAnalyzerUtil;
import lw.ssl.analyze.utils.notificators.EmailNotifier;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.ArrayList;

/**
 * Created by a.bukov on 22.04.2016.
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet/*"})
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final static String BULK_SSL_APP_ADMIN_EMAIL = "BULK_SSL_APP_ADMIN_EMAIL";
    private final static String STATISTIC_MESSAGE_TEXT_TEMPLATE = "User {0} with e-mail {1} entered the application.";

    private final static String APP_URL_VAR_NAME = "APP_URL";
    private static final String CALLBACK_CODE = "code";

    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        PdfReportService.buildReport(new ArrayList<>());
        String[] pathInfo = request.getPathInfo().split("/");
        String loginType = pathInfo[1];
        String errorMessage = null;

        if (pathInfo.length < 1 || StringUtils.isBlank(loginType)) {
            errorMessage = "Login type is undefined";
        } else {
            AuthType authType = AuthType.getByName(loginType);

            if (authType != null) {
                try {
                    String callbackCode = request.getParameter(CALLBACK_CODE);
                    DnsSecAnalyzerUtil.getStatistics("students.bsuir.by");

                    if (StringUtils.isNotBlank(callbackCode)) {
                        String userName = "Vasiliy";
                        String eMail = "adronex303@gmail.com";
                        String userInfoString = null;

//                        if (authType.equals(AuthType.HEROKU)) {
//                            //get token
//                            OAuthClientRequest herokuOauthRequest = OAuthClientRequest
//                                    .tokenLocation(HerokuProviderResource.TOKEN_ENDPOINT_URL)
//                                    .setClientId(System.getenv(HerokuProviderResource.HEROKU_OAUTH_ID_NAME))
//                                    .setClientSecret(System.getenv(HerokuProviderResource.HEROKU_OAUTH_SECRET_NAME))
//                                    .setGrantType(GrantType.AUTHORIZATION_CODE)
//                                    .setCode(callbackCode).buildBodyMessage();
//                            OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
//                            final OAuthAccessTokenResponse oAuthResponse = oAuthClient.accessToken(herokuOauthRequest, "POST");
//                            String accessToken = oAuthResponse.getAccessToken();
//
//                            //get user information
//                            HttpClient httpclient = new DefaultHttpClient();
//                            HttpGet getUserInfoRequest = new HttpGet(HerokuProviderResource.USER_INFORMATION_URL);
//                            getUserInfoRequest.addHeader("Authorization", "Bearer " + accessToken);
//                            HttpResponse getUserInfoResponse = httpclient.execute(getUserInfoRequest);
//                            userInfoString = EntityUtils.toString(getUserInfoResponse.getEntity());
//                        } else if (authType.equals(AuthType.LINKEDIN)) {
//                            //get token
//                            HttpClient httpclient = new DefaultHttpClient();
//                            HttpPost httppost = new HttpPost(OAuthProviderType.LINKEDIN.getTokenEndpoint());
//                            List<NameValuePair> params = new ArrayList<NameValuePair>(2);
//                            params.add(new BasicNameValuePair("grant_type", GrantType.AUTHORIZATION_CODE.toString()));
//                            params.add(new BasicNameValuePair("code", callbackCode));
//                            params.add(new BasicNameValuePair("redirect_uri", System.getenv(APP_URL_VAR_NAME) + "/LoginServlet/LinkedIn"));
//                            params.add(new BasicNameValuePair("client_id", System.getenv(LinkedInProviderResource.LINKEDIN_OAUTH_ID_NAME)));
//                            params.add(new BasicNameValuePair("client_secret", System.getenv(LinkedInProviderResource.LINKEDIN_OAUTH_SECRET_NAME)));
//                            httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
//                            httppost.setHeader("Content-Type", "application/x-www-form-urlencoded");
//                            HttpResponse oresponse = httpclient.execute(httppost);
//                            String accessTokenString = EntityUtils.toString(oresponse.getEntity());
//
//                            JSONObject accessTokenJSON = new JSONObject(accessTokenString);
//                            String error = JSONHelper.getStringIfExists(accessTokenJSON, "error");
//                            if (StringUtils.isNotBlank(error)) {
//                                throw new IllegalStateException(error);
//                            }
//
//                            String accessToken = JSONHelper.getStringIfExists(accessTokenJSON, "access_token");
//                            if (StringUtils.isBlank(accessToken)) {
//                                throw new IllegalStateException("Login problems");
//                            }
//
//                            //get user information
//                            HttpGet getUserInfoRequest = new HttpGet(LinkedInProviderResource.getUserInfoUrl(accessToken));
//                            HttpResponse getUserInfoResponse = httpclient.execute(getUserInfoRequest);
//                            userInfoString = EntityUtils.toString(getUserInfoResponse.getEntity());
//                        } else {
//                            errorMessage = "Invalid login type";
//                        }
//
//                        JSONObject userInfoJSON = null;
//                        if (StringUtils.isNotBlank(userInfoString)) {
//                            userInfoJSON = new JSONObject(userInfoString);
//                        }
//
//                        if (authType.equals(authType.HEROKU)) {
//                            userName = JSONHelper.getStringIfExists(userInfoJSON, "name");
//                            eMail = JSONHelper.getStringIfExists(userInfoJSON, "email");
//                        } else if (authType.equals(AuthType.LINKEDIN)){
//                            userName = MessageFormat.format("{0} {1}", JSONHelper.getStringIfExists(userInfoJSON, "firstName"), JSONHelper.getStringIfExists(userInfoJSON, "lastName"));
//                            eMail = JSONHelper.getStringIfExists(userInfoJSON, "emailAddress");
//                        }

                        if (StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(eMail)) {
                            String notificationText = MessageFormat.format(STATISTIC_MESSAGE_TEXT_TEMPLATE, userName, eMail);
                            EmailNotifier.notifyAdminWithMessage(notificationText, notificationText);

                            HttpSession session = request.getSession();
                            session.setAttribute("user", userName);
                            session.setAttribute("eMail", eMail);
                            //setting session to expiry in 30 mins
                            session.setMaxInactiveInterval(30 * 60);
                            response.sendRedirect("/index.jsp");
                            return;
                        } else {
                            errorMessage = "User data wasn't found(name and e-mail)";
                        }
                    } else {
                        errorMessage = "User data wasn't found(code)";
                    }
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                    errorMessage = e.getMessage();
                } catch (Exception e) {
                    e.printStackTrace();
                    errorMessage = "Connection error";
                }
            } else {
                errorMessage = "Invalid login type";
            }
        }

        response.setContentType("text/html");
        StringBuffer redirectPage = new StringBuffer();
        redirectPage.append("/login.jsp");
        if (StringUtils.isNotBlank(errorMessage)) {
            redirectPage.append("?errorMessage=" + URLEncoder.encode(errorMessage, "UTF-8"));
        }
        response.sendRedirect(redirectPage.toString());
    }
}
