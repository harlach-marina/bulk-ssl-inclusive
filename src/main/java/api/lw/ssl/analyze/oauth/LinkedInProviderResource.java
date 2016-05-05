package api.lw.ssl.analyze.oauth;

import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;

/**
 * Created by zmushko_m on 04.05.2016.
 */
public class LinkedInProviderResource {
    public static final String LINKEDIN_OAUTH_ID_NAME = "LINKEDIN_OAUTH_ID";
    public static final String LINKEDIN_OAUTH_SECRET_NAME = "LINKEDIN_OAUTH_SECRET";

    public static final String RESPONSE_TYPE = "code";
    public static final String AUTH_SCOPE = "r_basicprofile r_emailaddress";

    private static final String USER_INFO_URL = "https://api.linkedin.com/v1/people/~:(first-name,last-name,email-address)?format=json&oauth2_access_token={0}";

    public static final String getUserInfoUrl(String accessToken) {
        if (StringUtils.isNotBlank(accessToken)) {
            return MessageFormat.format(USER_INFO_URL, accessToken);
        }

        return null;
    }
}
