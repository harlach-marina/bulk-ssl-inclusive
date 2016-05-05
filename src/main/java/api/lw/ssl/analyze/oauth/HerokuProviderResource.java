package api.lw.ssl.analyze.oauth;

/**
 * Created by zmushko_m on 03.05.2016.
 */
public class HerokuProviderResource {
    private static final String HEROKU_BASE_URL = "https://id.heroku.com";
    public static final String AUTZ_ENDPOINT_URL = HEROKU_BASE_URL + "/oauth/authorize";
    public static final String TOKEN_ENDPOINT_URL = HEROKU_BASE_URL + "/oauth/token";

    private static final String HEROKU_API_URL = "https://api.heroku.com";
    public static final String USER_INFORMATION_URL = HEROKU_API_URL + "/account";

    public static final String AUTH_SCOPE = "identity";
    public static final String RESPONSE_TYPE = "code";

    public static final String HEROKU_OAUTH_ID_NAME = "HEROKU_OAUTH_ID";
    public static final String HEROKU_OAUTH_SECRET_NAME = "HEROKU_OAUTH_SECRET";
}
