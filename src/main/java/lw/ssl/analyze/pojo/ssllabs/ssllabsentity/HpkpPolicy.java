package lw.ssl.analyze.pojo.ssllabs.ssllabsentity;

import lw.ssl.analyze.utils.JSONHelper;
import org.json.JSONObject;

/**
 * Created by zmushko_m on 02.05.2016.
 */
public class HpkpPolicy {

    private static final String HPKP_STATUS = "status";

    HpkpPolicy(JSONObject hpkpPolicyJSON) {
        fillFromJSONObject(hpkpPolicyJSON);
    }

    private String hpkpStatus;

    private void fillFromJSONObject(JSONObject hpkpPolicyObject) {
        if (hpkpPolicyObject != null) {
            if (hpkpPolicyObject.has(HPKP_STATUS)) {
                hpkpStatus = JSONHelper.getStringIfExists(hpkpPolicyObject, HPKP_STATUS);
            }
        }
    }

    public String getHpkpStatus() {
        return hpkpStatus;
    }
}
