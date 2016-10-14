package lw.ssl.analyze.pojo.securityheaders;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 06.10.2016;
 *
 * @author p.sinitskiy (adronex303@gmail.com);
 * @since 1.0.
 */
public class SecurityHeadersResults {

    public static final Integer MAX_RED_HEADERS = 4;

    private List<String> greenHeaders = new ArrayList<>();
    private List<String> redHeaders = new ArrayList<>();

    public SecurityHeadersResults() {
    }

    public SecurityHeadersResults(List<String> greenHeaders, List<String> redHeaders) {
        this.greenHeaders = greenHeaders;
        this.redHeaders = redHeaders;
    }

    public List<String> getGreenHeaders() {
        return greenHeaders;
    }

    public List<String> getRedHeaders() {
        return redHeaders;
    }
}
