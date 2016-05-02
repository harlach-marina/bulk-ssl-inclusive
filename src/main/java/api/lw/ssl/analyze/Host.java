package api.lw.ssl.analyze;

import api.lw.ssl.analyze.enums.HostAssessmentStatus;
import lw.ssl.analyze.utils.JSONHelper;
import org.json.JSONObject;

/**
 * Created by zmushko_m on 29.04.2016.
 */
public class Host {
    public final static String HOST = "host";
    public final static String PORT = "port";
    public final static String STATUS = "status";
    public final static String STATUS_MESSAGE = "statusMessage";

    private String host;
    private String port;
    private HostAssessmentStatus status;
    private String statusMessage;

    public Host() {
    }

    public Host(JSONObject jsonObject) {
        fillFromJSONObject(jsonObject);
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public HostAssessmentStatus getStatus() {
        return status;
    }

    public void setStatus(HostAssessmentStatus status) {
        this.status = status;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public void fillFromJSONObject(JSONObject jsonObject) {
        if (jsonObject != null) {
            this.host = JSONHelper.getStringIfExists(jsonObject, this.HOST);
            Integer port = JSONHelper.getIntIfExists(jsonObject, this.PORT);
            if (port != null) {
                this.port = Integer.toString(port);
            }
            this.status = HostAssessmentStatus.getByName(JSONHelper.getStringIfExists(jsonObject, this.STATUS));
            this.statusMessage = JSONHelper.getStringIfExists(jsonObject, this.STATUS_MESSAGE);
        }
    }
}
