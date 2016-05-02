package api.lw.ssl.analyze.enums;

/**
 * Created by zmushko_m on 02.05.2016.
 */
public enum SSLtlsCompression {
    YES(null, "Yes"),
    NO(0, "No");

    SSLtlsCompression(Integer code, String value) {
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

    public static SSLtlsCompression getByCode(Integer code) {
        if (code != null) {
            for (SSLtlsCompression sslTLScompression: values()) {
                Integer enumCode = sslTLScompression.getCode();
                if (enumCode != null && enumCode.equals(code)) {
                    return sslTLScompression;
                }
            }
        }

        return SSLtlsCompression.YES;
    }
}
