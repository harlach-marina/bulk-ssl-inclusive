package api.lw.ssl.analyze;

import lw.ssl.analyze.utils.JSONHelper;
import org.json.JSONObject;

/**
 * Created by zmushko_m on 02.05.2016.
 */
public class HstsPolicy {
    private final static String HSTS_STATUS = "status";

    public HstsPolicy() {
    }

    public HstsPolicy(JSONObject hstsPolicyObject) {
        fillFromJSONObject(hstsPolicyObject);
    }

    private String HstsStatus;

    public String getHstsStatus() {
        return HstsStatus;
    }

    public void setHstsStatus(String hstsStatus) {
        HstsStatus = hstsStatus;
    }

    public void fillFromJSONObject(JSONObject hstsPolicyObject) {
        if (hstsPolicyObject != null) {
            if (hstsPolicyObject.has(HSTS_STATUS)) {
                this.setHstsStatus(JSONHelper.getStringIfExists(hstsPolicyObject, HSTS_STATUS));
            }
        }
    }
}
