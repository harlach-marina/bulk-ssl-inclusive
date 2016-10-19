package lw.ssl.analyze.pojo.dnssec;

import java.util.Set;
import java.util.TreeSet;

/**
 * Created on 19.10.2016;
 *
 * @author p.sinitskiy (adronex303@gmail.com);
 * @since 1.0.
 */
public class DnsSecAnalyzerResults {
    private Set<String> redResults = new TreeSet<>();
    private Set<String> greenResults = new TreeSet<>();

    public DnsSecAnalyzerResults(Set<String> redResults, Set<String> greenResults) {
        this.redResults = redResults;
        this.greenResults = greenResults;
    }

    public Integer getPercentageOfGreenResults() {
        return redResults.size() * 100 / (redResults.size() + greenResults.size());
    }

    public Set<String> getRedResults() {
        return redResults;
    }

    public Set<String> getGreenResults() {
        return greenResults;
    }
}
