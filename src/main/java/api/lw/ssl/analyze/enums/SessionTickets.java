package api.lw.ssl.analyze.enums;

/**
 * Created by zmushko_m on 02.05.2016.
 */
public enum SessionTickets {
    SUPPORTED(0, "supported"),
    NOT_IMPLEMENTED(1, "not implemented"),
    INTOLERANT_TO_EXTENSION(2, "intolerant to the extension");

    SessionTickets(Integer code, String value) {
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

    public static SessionTickets getByCode(Integer code) {
        if (code != null) {
            for (SessionTickets sessionTickets : values()) {
                if (sessionTickets.getCode().equals(code)) {
                    return sessionTickets;
                };
            }
        }

        return null;
    }
}
