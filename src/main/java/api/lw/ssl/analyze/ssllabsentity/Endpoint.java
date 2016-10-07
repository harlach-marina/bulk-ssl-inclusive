package api.lw.ssl.analyze.ssllabsentity;

import org.json.JSONObject;

/**
 * Created by zmushko_m on 29.04.2016.
 */
public class Endpoint {
    private static final String IP_ADDRESS = "ipAddress";
    private static final String STATUS_MESSAGE = "statusMessage";
    private static final String GRADE = "grade";
    private static final String DETAILS = "details";

    private String ipAddress;
    private String statusMessage;
    private String grade;
    private EndpointDetails details;

    public Endpoint(JSONObject jsonObject) {
        fillFromJSONObject(jsonObject);
    }

    private void fillFromJSONObject(JSONObject endpointJSONObject) {
        if (endpointJSONObject != null) {
            this.ipAddress = endpointJSONObject.optString(IP_ADDRESS, null);
            this.statusMessage = endpointJSONObject.optString(STATUS_MESSAGE, null);
            this.grade = endpointJSONObject.optString(GRADE, null);
            this.details = new EndpointDetails(endpointJSONObject.optJSONObject(DETAILS));
        }
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public String getGrade() {
        return grade;
    }

    public EndpointDetails getDetails() {
        return details;
    }
}
