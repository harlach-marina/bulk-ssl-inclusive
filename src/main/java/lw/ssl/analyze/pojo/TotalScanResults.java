package lw.ssl.analyze.pojo;

import lw.ssl.analyze.pojo.securityheaders.SecurityHeadersResults;
import lw.ssl.analyze.pojo.ssllabs.SslLabsResults;
import lw.ssl.analyze.pojo.tlscheck.TlsCheckResults;
import lw.ssl.analyze.pojo.virustotal.VirusTotalResults;

/**
 * Created on 06.10.2016;
 *
 * @author p.sinitskiy (adronex303@gmail.com);
 * @since 1.0.
 */
public class TotalScanResults {
    private SecurityHeadersResults securityHeadersResults;
    private VirusTotalResults virusTotalResults;
    private SslLabsResults sslLabsResults;
    private TlsCheckResults tlsCheckResults;

    private TotalScanResults(SecurityHeadersResults securityHeadersResults,
                             VirusTotalResults virusTotalResults,
                             SslLabsResults sslLabsResults,
                             TlsCheckResults tlsCheckResults) {
        this.securityHeadersResults = securityHeadersResults;
        this.virusTotalResults = virusTotalResults;
        this.sslLabsResults = sslLabsResults;
        this.tlsCheckResults = tlsCheckResults;
    }

    public static TotalScanResults getTotalResults(SecurityHeadersResults securityHeadersResults,
                                                   VirusTotalResults virusTotalResults,
                                                   SslLabsResults sslLabsResults,
                                                   TlsCheckResults tlsCheckResults) {
        return new TotalScanResults(securityHeadersResults, virusTotalResults, sslLabsResults, tlsCheckResults);
    }

    public SecurityHeadersResults getSecurityHeadersResults() {
        return securityHeadersResults;
    }

    public VirusTotalResults getVirusTotalResults() {
        return virusTotalResults;
    }

    public SslLabsResults getSslLabsResults() {
        return sslLabsResults;
    }

    public TlsCheckResults getTlsCheckResults() {
        return tlsCheckResults;
    }
}
