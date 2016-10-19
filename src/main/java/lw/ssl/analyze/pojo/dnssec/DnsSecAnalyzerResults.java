package lw.ssl.analyze.pojo.dnssec;

import java.util.Set;

/**
 * Created on 19.10.2016;
 *
 * @author p.sinitskiy (adronex303@gmail.com);
 * @since 1.0.
 */
public class DnsSecAnalyzerResults {
    private Set<String> redResults;
    private Set<String> greenResults;

    public DnsSecAnalyzerResults(Set<String> redResults, Set<String> greenResults) {
        this.redResults = redResults;
        this.greenResults = greenResults;
    }

    public Set<String> getRedResults() {
        return redResults;
    }

    public Set<String> getGreenResults() {
        return greenResults;
    }
}
