package api.lw.ssl.analyze.enums.oauth;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by zmushko_m on 04.05.2016.
 */
public enum AuthType {

    HEROKU("Heroku"),
    LINKEDIN("LinkedIn");

    AuthType(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }

    public static AuthType getByName(String name) {
        if (StringUtils.isNotBlank(name)) {
            for (AuthType authType : values()) {
                if (authType.getName().equals(name)) {
                    return authType;
                }
            }
        }

        return null;
    }
}
