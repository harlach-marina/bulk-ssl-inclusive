package api.lw.ssl.analyze.enums;

/**
 * Created by zmushko_m on 02.05.2016.
 */
public enum PoodleTLS {
    TIMEOUT(-3, "timeout"),
    TLS_NOT_SUPPORTED(-2, "TLS not supported"),
    TEST_FAILED(-1, "test failed"),
    UNKNOWN(0, "unknown"),
    NOT_VULNERABLE(1, "not vulnerable"),
    VULNERABLE(2, "vulnerable");

    PoodleTLS(Integer code, String value) {
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

    public static PoodleTLS getByCode(Integer code) {
        if (code != null) {
            for (PoodleTLS poodleTLS : values()) {
                if (poodleTLS.getCode().equals(code)) {
                    return  poodleTLS;
                }
            }
        }

        return null;
    }
}
