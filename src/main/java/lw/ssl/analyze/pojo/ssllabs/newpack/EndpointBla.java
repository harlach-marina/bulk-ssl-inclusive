package lw.ssl.analyze.pojo.ssllabs.newpack;

/**
 * Created on 07.10.2016;
 *
 * @author p.sinitskiy (adronex303@gmail.com);
 * @since 1.0.
 */
class EndpointBla {

    private String ipAddress;
    private String grade;

    private Certificate certificate;
    private Protocols protocols;
    private ProtocolDetails protocolDetails;

    private EndpointBla() {
    }
//    EndpointBla(JSONObject jsonObject) {
//        ipAddress = jsonObject.optString("ipAddress");
//        grade = jsonObject.optString("grade");
//    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getGrade() {
        return grade;
    }

    public Certificate getCertificate() {
        return certificate;
    }

    public Protocols getProtocols() {
        return protocols;
    }

    public ProtocolDetails getProtocolDetails() {
        return protocolDetails;
    }
}
