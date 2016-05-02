package api.lw.ssl.analyze;

import api.lw.ssl.analyze.enums.WeakKeyDebian;
import lw.ssl.analyze.utils.JSONHelper;
import org.json.JSONObject;

/**
 * Created by zmushko_m on 29.04.2016.
 */
public class Key {
    public static final String SIZE = "size";
    public static final String KEY = "alg";
    public static final String KEY_STRENGTH = "strength";
    public static final String WEAK_KEY_DEBIAN = "debianFlaw";

    private Integer size;
    private String key;
    private Integer keyStrength;
    private WeakKeyDebian weakKeyDebian;

    public Key() {
    }

    public Key(JSONObject keyJSONObject) {
        fillFromJSONObject(keyJSONObject);
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getKeyStrength() {
        return keyStrength;
    }

    public void setKeyStrength(Integer keyStrength) {
        this.keyStrength = keyStrength;
    }

    public WeakKeyDebian getWeakKeyDebian() {
        return weakKeyDebian;
    }

    public void setWeakKeyDebian(WeakKeyDebian weakKeyDebian) {
        this.weakKeyDebian = weakKeyDebian;
    }

    public void fillFromJSONObject(JSONObject keyJSONObject) {
        if (keyJSONObject != null) {
            this.setSize(JSONHelper.getIntIfExists(keyJSONObject, this.SIZE));
            this.setKey(JSONHelper.getStringIfExists(keyJSONObject, this.KEY));
            this.setKeyStrength(JSONHelper.getIntIfExists(keyJSONObject, this.KEY_STRENGTH));
            this.setWeakKeyDebian(WeakKeyDebian.getByIsWeak(JSONHelper.getBooleanIfExists(keyJSONObject, this.WEAK_KEY_DEBIAN)));

        }
    }
}
