package api.lw.ssl.analyze;

import lw.ssl.analyze.utils.JSONHelper;
import org.json.JSONObject;

/**
 * Created by zmushko_m on 29.04.2016.
 */
public class Endpoint {
    public static String IP_ADDRESS = "ipAddress";
    public static String STATUS_MESSAGE = "statusMessage";
    public static String GRADE = "grade";
    public static String DETAILS = "details";


    private String ipAddress;
    private String statusMessage;
    private String grade;
    private EndpointDetails details;

    public Endpoint(JSONObject jsonObject) {
        fillFromJSONObject(jsonObject);
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public EndpointDetails getDetails() {
        return details;
    }

    public void setDetails(EndpointDetails details) {
        this.details = details;
    }

    public void fillFromJSONObject(JSONObject endpointJSONObject) {
        if (endpointJSONObject != null) {
            this.ipAddress = JSONHelper.getStringIfExists(endpointJSONObject, this.IP_ADDRESS);
            this.statusMessage = JSONHelper.getStringIfExists(endpointJSONObject, this.STATUS_MESSAGE);
            this.grade = JSONHelper.getStringIfExists(endpointJSONObject, this.GRADE);


            if (endpointJSONObject.has(DETAILS)) {
                JSONObject endpointDetailsJSONObject = endpointJSONObject.getJSONObject(DETAILS);
                this.details = new EndpointDetails(endpointDetailsJSONObject);
            } else {
                this.details = new EndpointDetails(null);
            }
        }
    }
}
