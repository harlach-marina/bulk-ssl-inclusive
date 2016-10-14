package lw.ssl.analyze.pojo.ssllabs.ssllabsentity;

import api.lw.ssl.analyze.enums.WeakKeyDebian;
import lw.ssl.analyze.utils.JSONHelper;
import org.json.JSONObject;

/**
 * Created by zmushko_m on 29.04.2016.
 */
public class Key {
    private static final String SIZE = "size";
    private static final String KEY = "alg";
    private static final String KEY_STRENGTH = "strength";
    private static final String WEAK_KEY_DEBIAN = "debianFlaw";

    private Integer size;
    private String key;
    private Integer keyStrength;
    private WeakKeyDebian weakKeyDebian;

    Key(JSONObject keyJSONObject) {
        fillFromJSONObject(keyJSONObject);
    }

    private void fillFromJSONObject(JSONObject keyJSONObject) {
        if (keyJSONObject != null) {
            size = JSONHelper.getIntIfExists(keyJSONObject, SIZE);
            key = JSONHelper.getStringIfExists(keyJSONObject, KEY);
            keyStrength = JSONHelper.getIntIfExists(keyJSONObject, KEY_STRENGTH);
            weakKeyDebian = WeakKeyDebian.getByIsWeak(JSONHelper.getBooleanIfExists(keyJSONObject, WEAK_KEY_DEBIAN));
        }
    }

    public Integer getSize() {
        return size;
    }

    public String getKey() {
        return key;
    }

    public Integer getKeyStrength() {
        return keyStrength;
    }

    public WeakKeyDebian getWeakKeyDebian() {
        return weakKeyDebian;
    }
}
