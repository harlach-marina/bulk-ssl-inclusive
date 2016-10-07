package lw.ssl.analyze.pojo.ssllabs;

import api.lw.ssl.analyze.ssllabsentity.Endpoint;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 06.10.2016;
 *
 * @author p.sinitskiy (adronex303@gmail.com);
 * @since 1.0.
 */
public class SslLabsResults {

    private String hostName;
    private String port;
    private List<Endpoint> endpoints = new ArrayList<>();

    public SslLabsResults(JSONObject serverResponse) {
        hostName = serverResponse.optString("host");
        port = serverResponse.optString("port");

        JSONArray endpoints = serverResponse.optJSONArray("endpoints");
        for (int i = 0; i < endpoints.length(); i++) {

            if (endpoints.get(i) instanceof JSONObject) {
                this.endpoints.add(new Endpoint((JSONObject)endpoints.get(i)));
            }
        }
    }

    public String getHostName() {
        return hostName;
    }

    public String getPort() {
        return port;
    }

    public List<Endpoint> getEndpoints() {
        return endpoints;
    }
}