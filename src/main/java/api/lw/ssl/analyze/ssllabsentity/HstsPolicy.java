package api.lw.ssl.analyze.ssllabsentity;

import lw.ssl.analyze.utils.JSONHelper;
import org.json.JSONObject;

/**
 * Created by zmushko_m on 02.05.2016.
 */
public class HstsPolicy {
    private final static String HSTS_STATUS = "status";

    HstsPolicy(JSONObject hstsPolicyObject) {
        fillFromJSONObject(hstsPolicyObject);
    }

    private String hstsStatus;

    private void fillFromJSONObject(JSONObject hstsPolicyObject) {
        if (hstsPolicyObject != null) {
            if (hstsPolicyObject.has(HSTS_STATUS)) {
                hstsStatus = JSONHelper.getStringIfExists(hstsPolicyObject, HSTS_STATUS);
            }
        }
    }

    public String getHstsStatus() {
        return hstsStatus;
    }
}
