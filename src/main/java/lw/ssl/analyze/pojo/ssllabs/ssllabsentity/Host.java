package lw.ssl.analyze.pojo.ssllabs.ssllabsentity;

import api.lw.ssl.analyze.enums.HostAssessmentStatus;
import lw.ssl.analyze.utils.JSONHelper;
import org.json.JSONObject;

/**
 * Created by zmushko_m on 29.04.2016.
 */
public class Host {
    private final static String HOST = "host";
    private final static String PORT = "port";
    private final static String STATUS = "status";
    private final static String STATUS_MESSAGE = "statusMessage";

    private String host;
    private String port;
    private HostAssessmentStatus status;
    private String statusMessage;

    public Host(String host, String port, String status, String statusMessage) {
        this.host = host;
        this.port = port;
        this.status = HostAssessmentStatus.getByName(status);
        this.statusMessage = statusMessage;
    }

    private void fillFromJSONObject(JSONObject jsonObject) {
        if (jsonObject != null) {
            this.host = JSONHelper.getStringIfExists(jsonObject, HOST);
            Integer port = JSONHelper.getIntIfExists(jsonObject, PORT);
            if (port != null) {
                this.port = Integer.toString(port);
            }
            this.status = HostAssessmentStatus.getByName(JSONHelper.getStringIfExists(jsonObject, STATUS));
            this.statusMessage = JSONHelper.getStringIfExists(jsonObject, STATUS_MESSAGE);
        }
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    public HostAssessmentStatus getStatus() {
        return status;
    }

    public String getStatusMessage() {
        return statusMessage;
    }
}
