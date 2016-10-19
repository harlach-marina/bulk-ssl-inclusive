package lw.ssl.analyze.pojo.ssllabs;

import api.lw.ssl.analyze.enums.Protocol;
import api.lw.ssl.analyze.enums.WeakKeyDebian;
import lw.ssl.analyze.pojo.ssllabs.ssllabsentity.Endpoint;
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
    private String status;
    private String statusMessage;
    private List<Endpoint> endpoints = new ArrayList<>();

    private SslLabsResults(JSONObject serverResponse) {
        hostName = serverResponse.optString("host");
        port = serverResponse.optString("port");
        status = serverResponse.optString("status");
        statusMessage = serverResponse.optString("statusMessage");

        JSONArray endpoints = serverResponse.optJSONArray("endpoints");
        for (int i = 0; i < endpoints.length(); i++) {

            if (endpoints.get(i) instanceof JSONObject) {
                this.endpoints.add(new Endpoint((JSONObject)endpoints.get(i)));
            }
        }
    }

    public static SslLabsResults createFromResponse(JSONObject response) {
        return new SslLabsResults(response);
    }

    public static SslLabsResults getErrorResults(String host, String port, String status, String statusMessage) {
        JSONObject badResults = new JSONObject();
        badResults.put("host", host);
        badResults.put("port", port);
        badResults.put("status", status);
        badResults.put("statusMessage", statusMessage);
        return new SslLabsResults(badResults);
    }

    public Boolean isTlsInGoodShape() {
        return isProtocolSupported(Protocol.TLS_1_1) || isProtocolSupported(Protocol.TLS_1_2);
    }

    /**
     * https://www.ssllabs.com/downloads/SSL_Server_Rating_Guide.pdf
     * Page 5
     */
    public Long getSummaryProtocolSupport() {
        return isProtocolSupported(Protocol.SSL_3)
                ? isProtocolSupported(Protocol.TLS_1_0)
                ? isProtocolSupported(Protocol.TLS_1_1)
                ? isProtocolSupported(Protocol.TLS_1_2)
                ? 100L
                : 95
                : 90
                : 80
                : 0;
    }

    private Boolean isProtocolSupported(Protocol protocol) {
        return endpoints.stream().filter(e -> e.getDetails().getProtocols().isProtocolHere(protocol)).count() > 0;
    }

    public Long getSummaryKeyRating(){
        Long result = 0L;
        for (Endpoint endpoint : endpoints) {
            result += getKeyStrength(endpoint) < 4096
                    ? getKeyStrength(endpoint) < 2048
                    ? getKeyStrength(endpoint) < 1024
                    ? getKeyStrength(endpoint) < 512
                    ? getKeyStrength(endpoint) == 0
                    ? 0L
                    : 20
                    : 40
                    : 80
                    : 90
                    : 100;
        }
        return result / endpoints.size();
    }

    private Long getKeyStrength(Endpoint endpoint){
        return WeakKeyDebian.STRONG.equals(endpoint.getDetails().getKey().getWeakKeyDebian())
                ? endpoint.getDetails().getKey().getKeyStrength()
                : 0L;
    }

    public Long getSummaryCipher() {
        Long cipherStrength = getCipherStrength();
        return cipherStrength > 0
                ? cipherStrength > 128
                ? cipherStrength > 256
                ? 100L
                : 80
                : 20
                : 0;
    }

    private Long getCipherStrength() {
        Long result = 0L;
        for (Endpoint endpoint : endpoints) {
            result += endpoint.getDetails().getSuites().stream()
                    .map(s -> s.getCipherStrength()).max(Integer::compare).get();
            result += endpoint.getDetails().getSuites().stream()
                    .map(s -> s.getCipherStrength()).min(Integer::compare).get();
            result /= 2;
        }
        return result;
    }

    public Boolean isSummaryCertificateValid(){
        return endpoints.stream().filter(e -> e.getDetails().getCertificate().getValid()).count() > 0;
    }

    public Boolean isRc4Supported(){
        return endpoints.stream().filter(e -> e.getDetails().getSupportsRc4()).count() > 0;
    }

    public String getHostName() {
        return hostName;
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

    public List<Endpoint> getEndpoints() {
        return endpoints;
    }
}