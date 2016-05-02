package api.lw.ssl.analyze.enums;

/**
 * Created by zmushko_m on 02.05.2016.
 */
public enum RenegotationSupport {
//    renegSupport - this is an integer value that describes the endpoint support for renegotiation:
//    bit 0 (1) - set if insecure client-initiated renegotiation is supported
//    bit 1 (2) - set if secure renegotiation is supported
//    bit 2 (4) - set if secure client-initiated renegotiation is supported
//    bit 3 (8) - set if the server requires secure renegotiation support

    BIT_1(1, "insecure client-initiated renegotiation is supported"),
    BIT_2(2, "secure renegotiation is supported"),
    BIT_3(4, "secure client-initiated renegotiation is supported"),
    BIT_4(8, "server requires secure renegotiation support");

    RenegotationSupport(Integer code, String value) {
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

    public static RenegotationSupport getByCode(Integer code) {
        if (code != null) {
            for (RenegotationSupport renegotationSupport : values()) {
                if (renegotationSupport.getCode().equals(code)) {
                    return renegotationSupport;
                }
            }
        }

        return null;
    }
}
