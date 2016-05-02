package api.lw.ssl.analyze;

import lw.ssl.analyze.utils.JSONHelper;
import org.json.JSONObject;

/**
 * Created by zmushko_m on 02.05.2016.
 */
public class HpkpPolicy {

    public static final String HPKP_STATUS = "status";

    public HpkpPolicy() {
    }

    public HpkpPolicy(JSONObject hpkpPolicyJSON) {
        fillFromJSONObject(hpkpPolicyJSON);
    }

    private String hpkpStatus;

    public String getHpkpStatus() {
        return hpkpStatus;
    }

    public void setHpkpStatus(String hpkpStatus) {
        this.hpkpStatus = hpkpStatus;
    }

    public void fillFromJSONObject(JSONObject hpkpPolicyObject) {
        if (hpkpPolicyObject != null) {
            if (hpkpPolicyObject.has(HPKP_STATUS)) {
                this.setHpkpStatus(JSONHelper.getStringIfExists(hpkpPolicyObject, HPKP_STATUS));
            }
        }
    }
}
