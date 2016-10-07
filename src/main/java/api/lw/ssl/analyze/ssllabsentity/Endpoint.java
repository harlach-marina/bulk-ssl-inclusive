package api.lw.ssl.analyze.ssllabsentity;

import org.json.JSONObject;

/**
 * Created by zmushko_m on 29.04.2016.
 */
public class Endpoint {
    private static final String IP_ADDRESS = "ipAddress";
    private static final String HOST = "ipAddress";
    private static final String PORT = "ipAddress";
    private static final String STATUS = "ipAddress";
    private static final String STATUS_MESSAGE = "statusMessage";
    private static final String GRADE = "grade";
    private static final String DETAILS = "details";

    private String ipAddress;
    private String host;
    private String port;
    private String status;
    private String statusMessage;
    private String grade;
    private EndpointDetails details;

    public Endpoint(JSONObject jsonObject) {
        fillFromJSONObject(jsonObject);
    }

    private void fillFromJSONObject(JSONObject endpointJSONObject) {
        if (endpointJSONObject != null) {
            ipAddress = endpointJSONObject.optString(IP_ADDRESS, null);
            host = endpointJSONObject.optString(HOST, null);
            port = endpointJSONObject.optString(PORT, null);
            status = endpointJSONObject.optString(STATUS, null);
            statusMessage = endpointJSONObject.optString(STATUS_MESSAGE, null);
            grade = endpointJSONObject.optString(GRADE, null);
            details = new EndpointDetails(endpointJSONObject.optJSONObject(DETAILS));
        }
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    public String getStatus() {
        return status;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public String getGrade() {
        return grade;
    }

    public final EndpointDetails getDetails() {
        return details;
    }
}
