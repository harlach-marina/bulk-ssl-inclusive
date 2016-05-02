package api.lw.ssl.analyze.enums;

/**
 * Created by zmushko_m on 02.05.2016.
 */
public enum DhUsesKnownPrimes {
//    dhUsesKnownPrimes - whether the server uses known DH primes. Not present if the server doesn't support the DH key exchange. Possible values:
//    0 - no
//    1 - yes, but they're not weak
//    2 - yes and they're weak
    NO(0, "no"),
    YES_NOT_WEAK(1, "yes, but they're not weak"),
    YES_WEAK(2, "yes and they're weak");

    DhUsesKnownPrimes(Integer code, String value) {
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

    public static DhUsesKnownPrimes getByCode(Integer code) {
        if (code != null) {
            for (DhUsesKnownPrimes dhUsesKnownPrimes : values()) {
                if (dhUsesKnownPrimes.getCode().equals(code)) {
                    return dhUsesKnownPrimes;
                }
            }
        }

        return null;
    }
}
