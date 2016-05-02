package api.lw.ssl.analyze.enums;

/**
 * Created by zmushko_m on 29.04.2016.
 */
public enum RevocationInfo {
    CRL("CRL", 1),
    OCSP("OCSP", 2),
    CRL_OCSP("CRL, OSCP", 3),
    NONE("None", 0);

    RevocationInfo(String value, Integer code) {
        this.value = value;
        this.code = code;
    }

    private String value;
    private Integer code;

    public String getValue() {
        return value;
    }

    public Integer getCode() {
        return code;
    }

    public static RevocationInfo getByCode(Integer code) {
        if (code != null) {
            for(RevocationInfo e : values()) {
                if(e.code.equals(code)) {
                    return e;
                }
            }
        }

        return RevocationInfo.NONE;
    }
}
