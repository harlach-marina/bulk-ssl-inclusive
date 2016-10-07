package lw.ssl.analyze.pojo;

import lw.ssl.analyze.pojo.securityheaders.SecurityHeadersResults;
import lw.ssl.analyze.pojo.ssllabs.SslLabsResults;
import lw.ssl.analyze.pojo.virustotal.VirusTotalResults;

/**
 * Created on 06.10.2016;
 *
 * @author p.sinitskiy (adronex303@gmail.com);
 * @since 1.0.
 */
public class TotalResults {
    private SecurityHeadersResults securityHeadersResults;
    private VirusTotalResults virusTotalResults;
    private SslLabsResults sslLabsResults;

    public TotalResults(SecurityHeadersResults securityHeadersResults,
                        VirusTotalResults virusTotalResults,
                        SslLabsResults sslLabsResults) {
        this.securityHeadersResults = securityHeadersResults;
        this.virusTotalResults = virusTotalResults;
        this.sslLabsResults = sslLabsResults;
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
}
