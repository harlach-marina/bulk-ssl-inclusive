package lw.ssl.analyze.pojo.ssllabs.newpack;

/**
 * Created on 07.10.2016;
 *
 * @author p.sinitskiy (adronex303@gmail.com);
 * @since 1.0.
 */
class Protocols {
    private Boolean tls1p0; // Protocols
    private Boolean tls1p1; // Protocols
    private Boolean tls1p2; // Protocols
    private Boolean ssl2; // Protocols
    private Boolean ssl3; // Protocols

    public Boolean getTls1p0() {
        return tls1p0;
    }

    private Protocols() {
    }

    public Boolean getTls1p1() {
        return tls1p1;
    }

    public Boolean getTls1p2() {
        return tls1p2;
    }

    public Boolean getSsl2() {
        return ssl2;
    }

    public Boolean getSsl3() {
        return ssl3;
    }
}
