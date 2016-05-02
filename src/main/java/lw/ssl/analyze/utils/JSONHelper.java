package lw.ssl.analyze.utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zmushko_m on 28.04.2016.
 */
public class JSONHelper {

    public static String getStringIfExists(JSONObject jsonObject, String key) {
        if (jsonObject != null && jsonObject.has(key)) {
            return jsonObject.getString(key);
        }

        return null;
    }

    public static Long getLongIfExists(JSONObject jsonObject, String key) {
        if (jsonObject != null && jsonObject.has(key)) {
            return jsonObject.getLong(key);
        }

        return null;
    }

    public static Integer getIntIfExists(JSONObject jsonObject, String key) {
        if (jsonObject != null && jsonObject.has(key)) {
            return jsonObject.getInt(key);
        }

        return null;
    }

    public static Boolean getBooleanIfExists(JSONObject jsonObject, String key) {
        if (jsonObject != null && jsonObject.has(key)) {
            return jsonObject.getBoolean(key);
        }

        return null;
    }


    public static List getStringListFromJSONArray(JSONArray jsonArray) {
        List<String> stringList = null;

        if (jsonArray != null && jsonArray.length() > 0) {
            stringList = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                stringList.add(jsonArray.getString(i));
            }
        }

        return stringList;
    }
}
