package api.lw.ssl.analyze.enums;

/**
 * Created by zmushko_m on 02.05.2016.
 */
public enum SessionResumption {
//    sessionResumption - this is an integer value that describes endpoint support for session resumption. The possible values are:
//    0 - session resumption is not enabled and we're seeing empty session IDs
//    1 - endpoint returns session IDs, but sessions are not resumed
//    2 - session resumption is enabled

    NOT_ENABLED(0, "not enabled, empty session IDs"),
    SESSIONS_ARENT_RESUMED(1, "sessions are not resumed"),
    ENABLED(2, "enabled");

    SessionResumption(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    private Integer code;
    private String value;

    public Integer getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public static SessionResumption getByCode(Integer code) {
        if (code != null) {
            for (SessionResumption sessionResumption : values()) {
                if (sessionResumption.getCode().equals(code)) {
                    return sessionResumption;
                }
            }
        }

        return null;
    }
}
