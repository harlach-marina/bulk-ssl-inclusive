package api.lw.ssl.analyze.enums;

/**
 * Created by zmushko_m on 02.05.2016.
 */
public enum OpenSSLccs {
    BIT_1(-1, "test failed"),
    BIT_2(0, "unknown"),
    BIT_3(1, "not vulnerable"),
    BIT_4(2, "possibly vulnerable, but not exploitable"),
    BIT_5(3, "vulnerable and exploitable");

    OpenSSLccs(Integer code, String value) {
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

    public static OpenSSLccs getByCode(Integer code) {
        if (code != null) {
            for (OpenSSLccs openSSLccs : values()) {
                if (openSSLccs.getCode().equals(code)) {
                    return openSSLccs;
                }
            }
        }

        return null;
    }
}
