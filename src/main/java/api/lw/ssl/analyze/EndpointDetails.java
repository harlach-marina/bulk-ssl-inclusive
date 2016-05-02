package api.lw.ssl.analyze;

import lw.ssl.analyze.utils.JSONHelper;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by zmushko_m on 29.04.2016.
 */
public class EndpointDetails {
    public static String KEY = "key";
    public static String CERT = "cert";
    public static String PROTOCOLS = "protocols";

    public EndpointDetails() {
    }

    public EndpointDetails(JSONObject endpoitDetailsJSON) {
        fillFromJSONObject(endpoitDetailsJSON);
    }

    private Cert cert;
    private Key key;
    private ProtocolContainer protocols = new ProtocolContainer();

    public Cert getCert() {
        return cert;
    }

    public void setCert(Cert cert) {
        this.cert = cert;
    }

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public ProtocolContainer getProtocols() {
        return protocols;
    }

    public void setProtocols(ProtocolContainer protocols) {
        this.protocols = protocols;
    }

    public void fillFromJSONObject(JSONObject endpointDetailsJSONObject) {
        if (endpointDetailsJSONObject != null) {
            if (endpointDetailsJSONObject.has(CERT)) {
                JSONObject certJSONObject = endpointDetailsJSONObject.getJSONObject(CERT);
                this.cert = new Cert(certJSONObject);
            } else {
                this.cert = new Cert(null);
            }

            if (endpointDetailsJSONObject.has(KEY)) {
                JSONObject keyJSONObject = endpointDetailsJSONObject.getJSONObject(KEY);
                this.key = new Key(keyJSONObject);
            } else {
                this.key = new Key(null);
            }

            if (endpointDetailsJSONObject.has(PROTOCOLS)) {
                JSONArray protocolsJSONArray = endpointDetailsJSONObject.getJSONArray(PROTOCOLS);

                if (protocolsJSONArray != null) {
                    for (int protocolPointer = 0; protocolPointer < protocolsJSONArray.length(); protocolPointer++) {
                        JSONObject protocolJSONObject = protocolsJSONArray.getJSONObject(protocolPointer);

                        String name = JSONHelper.getStringIfExists(protocolJSONObject, ProtocolDetails.NAME);
                        String version = JSONHelper.getStringIfExists(protocolJSONObject, ProtocolDetails.VERSION);
                        boolean isAvailible = true;
                        Integer insecureFlag = JSONHelper.getIntIfExists(protocolJSONObject, ProtocolDetails.INSECURE_FLAG);
                        boolean isInsecure = false;
                        if (insecureFlag != null && insecureFlag == 0) {
                            isInsecure = true;
                        }

                        protocols.setProtocolInfoByNameAndVersion(name, version, isAvailible, isInsecure);
                    }
                }
            }
        }
    }
}
