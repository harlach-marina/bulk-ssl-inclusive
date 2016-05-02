package api.lw.ssl.analyze.enums;

/**
 * Created by zmushko_m on 28.04.2016.
 */
public enum RevocationStatus {
//    0 - not checked
//    1 - certificate revoked
//    2 - certificate not revoked
//    3 - revocation check error
//    4 - no revocation information
//    5 - internal error

    NOT_CHECKED(0, "not checked"),
    CERTIFICATE_REVOKED(1, "certificate revoked"),
    CERTIFICATE_NOT_REVOKED(2, "certificate not revoked"),
    REVOCATION_CHECK_REVOKE(3, "revocation check error"),
    NO_REVOCATION_INFORMATION(4, "no revocation information"),
    INTERNAL_ERROR(5, "internal error");


    RevocationStatus(Integer id, String name) {
        this.name = name;
        this.id = id;
    }

    private String name;
    private Integer id;

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public static RevocationStatus getId(Integer id) {
        if (id != null) {
            for(RevocationStatus e : values()) {
                if(e.id.equals(id)) {
                    return e;
                }
            }
        }

        return null;
    }
}
