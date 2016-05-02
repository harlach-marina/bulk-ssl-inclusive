package api.lw.ssl.analyze.enums;

/**
 * Created by zmushko_m on 02.05.2016.
 */
public enum ForwardSecrecy {
//    forwardSecrecy - indicates support for Forward Secrecy
//    bit 0 (1) - set if at least one browser from our simulations negotiated a Forward Secrecy suite.
//    bit 1 (2) - set based on Simulator results if FS is achieved with modern clients. For example, the server supports ECDHE suites, but not DHE.
//    bit 2 (4) - set if all simulated clients achieve FS. In other words, this requires an ECDHE + DHE combination to be supported.

    BIT_0(1, "at least one browser from our simulations negotiated a Forward Secrecy suite"),
    BIT_1(2, "FS is achieved with modern clients"),
    BIT_2(4, "all simulated clients achieve FS");

    ForwardSecrecy(Integer code, String value) {
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

    public static ForwardSecrecy getByCode(Integer code) {
        if (code != null) {
            for (ForwardSecrecy forwardSecrecy : values()) {
                if (forwardSecrecy.getCode().equals(code)) {
                    return forwardSecrecy;
                }
            }
        }

        return null;
    }
}
