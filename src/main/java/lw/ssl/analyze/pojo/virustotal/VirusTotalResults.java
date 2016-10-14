package lw.ssl.analyze.pojo.virustotal;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created on 06.10.2016;
 *
 * @author p.sinitskiy (adronex303@gmail.com);
 * @since 1.0.
 */
public class VirusTotalResults {

    private List<VirusTotalScan> scans = new ArrayList<>();

    public VirusTotalResults(JSONObject jsonObject) {
        Iterator<?> keys = jsonObject.keys();

        while( keys.hasNext() ) {
            String key = (String)keys.next();
            if ( jsonObject.get(key) instanceof JSONObject ) {
                JSONObject value = (JSONObject) jsonObject.get(key);
                Boolean detected = value.optBoolean("detected");
                String result = value.optString("result");
                String details = value.optString("detail");
                scans.add(new VirusTotalScan(key, detected, result, details));
            }
        }
    }

    public List<VirusTotalScan> getScans() {
        return scans;
    }

    public Long getCleanResultsPercentage(){
        return scans.stream().filter(s -> Boolean.FALSE.equals(s.getDetected())).count()
                * 100
                / scans.size();
    }
}