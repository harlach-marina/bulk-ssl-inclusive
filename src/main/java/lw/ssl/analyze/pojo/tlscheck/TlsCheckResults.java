package lw.ssl.analyze.pojo.tlscheck;

/**
 * Created on 18.10.2016;
 *
 * @author p.sinitskiy (adronex303@gmail.com);
 * @since 1.0.
 */
public class TlsCheckResults {
    private Integer tlsAdvPercents;
    private Integer certOkPercents;
    private Integer tlsNegPercents;

    public TlsCheckResults(Integer tlsAdvPercents, Integer certOkPercents, Integer tlsNegPercents) {
        this.tlsAdvPercents = tlsAdvPercents;
        this.certOkPercents = certOkPercents;
        this.tlsNegPercents = tlsNegPercents;
    }

    public Integer getTlsAdvPercents() {
        return tlsAdvPercents;
    }

    public Integer getCertOkPercents() {
        return certOkPercents;
    }

    public Integer getTlsNegPercents() {
        return tlsNegPercents;
    }
}
