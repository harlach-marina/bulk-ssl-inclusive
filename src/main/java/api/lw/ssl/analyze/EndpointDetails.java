package api.lw.ssl.analyze;

import api.lw.ssl.analyze.enums.*;
import lw.ssl.analyze.utils.JSONHelper;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by zmushko_m on 29.04.2016.
 */
public class EndpointDetails {
    public static final String KEY = "key";
    public static final String CERT = "cert";
    public static final String PROTOCOLS = "protocols";
    public static final String RENEGOTATOIN_SUPPORT = "renegSupport";
    public static final String VULNERABLE_BEAST = "vulnBeast";
    public static final String POODLE = "poodle";
    public static final String POODLE_TLS = "poodleTls";
    public static final String DOWNGRADE_ATTACK_PREVENTION = "fallbackScsv";
    public static final String SSL_TLS_COMPRESSION = "compressionMethods";
    public static final String RC4 = "supportsRc4";
    public static final String HEARTBEAT = "heartbeat";
    public static final String HEARTBLEED = "heartbleed";
    public static final String OPEN_SSL_CCS = "openSslCcs";
    public static final String FORWARD_SECRECY = "forwardSecrecy";
    public static final String NPN = "supportsNpn";
    public static final String SESSION_RESUMPTION = "sessionResumption";
    public static final String SESSION_TICKETS = "sessionTickets";
    public static final String OCSP_STAPLING = "ocspStapling";
    public static final String HSTS_POLICY = "hstsPolicy";
    public static final String HPKP_POLICY = "hpkpPolicy";
    public static final String HPKP_RO_POLICY = "hpkpRoPolicy";
    public static final String DH_USES_KNOWN_PRIMES = "dhUsesKnownPrimes";
    public static final String DH_YS_REUSE = "dhYsReuse";

    public EndpointDetails() {
    }

    public EndpointDetails(JSONObject endpoitDetailsJSON) {
        fillFromJSONObject(endpoitDetailsJSON);
    }

    private Cert cert;
    private Key key;
    private ProtocolContainer protocols = new ProtocolContainer();
    private RenegotationSupport renegotationSupport;
    private Boolean isVulnerableBeast;
    private Boolean poodle;
    private PoodleTLS poodleTLS;
    private Boolean fallbackScsv;
    private SSLtlsCompression ssLtlsCompression;
    private Boolean supportsRc4;
    private Boolean heartBeat;
    private Boolean heartBleed;
    private OpenSSLccs openSSLccs;
    private ForwardSecrecy forwardSecrecy;
    private Boolean npn;
    private SessionResumption sessionResumption;
    private SessionTickets sessionTickets;
    private Boolean ocspStapling;
    private HstsPolicy hstsPolicy;
    private HpkpPolicy hpkpPolicy;
    private HpkpPolicy hpkpROPolicy;
    private DhUsesKnownPrimes dhUsesKnownPrimes;
    private Boolean dhYsReuse;

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

    public RenegotationSupport getRenegotationSupport() {
        return renegotationSupport;
    }

    public void setRenegotationSupport(RenegotationSupport renegotationSupport) {
        this.renegotationSupport = renegotationSupport;
    }

    public Boolean getVulnerableBeast() {
        return isVulnerableBeast;
    }

    public void setVulnerableBeast(Boolean vulnerableBeast) {
        isVulnerableBeast = vulnerableBeast;
    }

    public Boolean getPoodle() {
        return poodle;
    }

    public void setPoodle(Boolean poodle) {
        this.poodle = poodle;
    }

    public PoodleTLS getPoodleTLS() {
        return poodleTLS;
    }

    public void setPoodleTLS(PoodleTLS poodleTLS) {
        this.poodleTLS = poodleTLS;
    }

    public Boolean getFallbackScsv() {
        return fallbackScsv;
    }

    public void setFallbackScsv(Boolean fallbackScsv) {
        this.fallbackScsv = fallbackScsv;
    }

    public SSLtlsCompression getSsLtlsCompression() {
        return ssLtlsCompression;
    }

    public void setSsLtlsCompression(SSLtlsCompression ssLtlsCompression) {
        this.ssLtlsCompression = ssLtlsCompression;
    }

    public Boolean getSupportsRc4() {
        return supportsRc4;
    }

    public void setSupportsRc4(Boolean supportsRc4) {
        this.supportsRc4 = supportsRc4;
    }

    public Boolean getHeartBeat() {
        return heartBeat;
    }

    public void setHeartBeat(Boolean heartBeat) {
        this.heartBeat = heartBeat;
    }

    public Boolean getHeartBleed() {
        return heartBleed;
    }

    public void setHeartBleed(Boolean heartBleed) {
        this.heartBleed = heartBleed;
    }

    public OpenSSLccs getOpenSSLccs() {
        return openSSLccs;
    }

    public void setOpenSSLccs(OpenSSLccs openSSLccs) {
        this.openSSLccs = openSSLccs;
    }

    public ForwardSecrecy getForwardSecrecy() {
        return forwardSecrecy;
    }

    public void setForwardSecrecy(ForwardSecrecy forwardSecrecy) {
        this.forwardSecrecy = forwardSecrecy;
    }

    public Boolean getNpn() {
        return npn;
    }

    public void setNpn(Boolean npn) {
        this.npn = npn;
    }

    public SessionResumption getSessionResumption() {
        return sessionResumption;
    }

    public void setSessionResumption(SessionResumption sessionResumption) {
        this.sessionResumption = sessionResumption;
    }

    public SessionTickets getSessionTickets() {
        return sessionTickets;
    }

    public void setSessionTickets(SessionTickets sessionTickets) {
        this.sessionTickets = sessionTickets;
    }

    public Boolean getOcspStapling() {
        return ocspStapling;
    }

    public void setOcspStapling(Boolean ocspStapling) {
        this.ocspStapling = ocspStapling;
    }

    public HstsPolicy getHstsPolicy() {
        return hstsPolicy;
    }

    public void setHstsPolicy(HstsPolicy hstsPolicy) {
        this.hstsPolicy = hstsPolicy;
    }

    public HpkpPolicy getHpkpPolicy() {
        return hpkpPolicy;
    }

    public void setHpkpPolicy(HpkpPolicy hpkpPolicy) {
        this.hpkpPolicy = hpkpPolicy;
    }

    public HpkpPolicy getHpkpROPolicy() {
        return hpkpROPolicy;
    }

    public void setHpkpROPolicy(HpkpPolicy hpkpROPolicy) {
        this.hpkpROPolicy = hpkpROPolicy;
    }

    public DhUsesKnownPrimes getDhUsesKnownPrimes() {
        return dhUsesKnownPrimes;
    }

    public void setDhUsesKnownPrimes(DhUsesKnownPrimes dhUsesKnownPrimes) {
        this.dhUsesKnownPrimes = dhUsesKnownPrimes;
    }

    public Boolean getDhYsReuse() {
        return dhYsReuse;
    }

    public void setDhYsReuse(Boolean dhYsReuse) {
        this.dhYsReuse = dhYsReuse;
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

            if (endpointDetailsJSONObject.has(RENEGOTATOIN_SUPPORT)) {
                this.setRenegotationSupport(RenegotationSupport.getByCode(JSONHelper.getIntIfExists(endpointDetailsJSONObject, RENEGOTATOIN_SUPPORT)));
            }

            if (endpointDetailsJSONObject.has(VULNERABLE_BEAST)) {
                this.setVulnerableBeast(JSONHelper.getBooleanIfExists(endpointDetailsJSONObject, VULNERABLE_BEAST));
            }

            if (endpointDetailsJSONObject.has(POODLE)) {
                this.setPoodle(JSONHelper.getBooleanIfExists(endpointDetailsJSONObject, POODLE));
            }

            if (endpointDetailsJSONObject.has(POODLE_TLS)) {
                this.setPoodleTLS(PoodleTLS.getByCode(JSONHelper.getIntIfExists(endpointDetailsJSONObject, POODLE_TLS)));
            }

            if (endpointDetailsJSONObject.has(DOWNGRADE_ATTACK_PREVENTION)) {
                this.setFallbackScsv(JSONHelper.getBooleanIfExists(endpointDetailsJSONObject, DOWNGRADE_ATTACK_PREVENTION));
            }

            if (endpointDetailsJSONObject.has(SSL_TLS_COMPRESSION)) {
                this.setSsLtlsCompression(SSLtlsCompression.getByCode(JSONHelper.getIntIfExists(endpointDetailsJSONObject, SSL_TLS_COMPRESSION)));
            }

            if (endpointDetailsJSONObject.has(RC4)) {
                this.setSupportsRc4(JSONHelper.getBooleanIfExists(endpointDetailsJSONObject, RC4));
            }

            if (endpointDetailsJSONObject.has(HEARTBEAT)) {
                this.setHeartBeat(JSONHelper.getBooleanIfExists(endpointDetailsJSONObject, HEARTBEAT));
            }

            if (endpointDetailsJSONObject.has(HEARTBLEED)) {
                this.setHeartBleed(JSONHelper.getBooleanIfExists(endpointDetailsJSONObject, HEARTBLEED));
            }

            if (endpointDetailsJSONObject.has(OPEN_SSL_CCS)) {
                this.setOpenSSLccs(OpenSSLccs.getByCode(JSONHelper.getIntIfExists(endpointDetailsJSONObject, OPEN_SSL_CCS)));
            }

            if (endpointDetailsJSONObject.has(FORWARD_SECRECY)) {
                this.setForwardSecrecy(ForwardSecrecy.getByCode(JSONHelper.getIntIfExists(endpointDetailsJSONObject, FORWARD_SECRECY)));
            }

            if (endpointDetailsJSONObject.has(NPN)) {
                this.setNpn(JSONHelper.getBooleanIfExists(endpointDetailsJSONObject, NPN));
            }

            if (endpointDetailsJSONObject.has(SESSION_RESUMPTION)) {
                this.setSessionResumption(SessionResumption.getByCode(JSONHelper.getIntIfExists(endpointDetailsJSONObject, SESSION_RESUMPTION)));
            }

            if (endpointDetailsJSONObject.has(SESSION_TICKETS)) {
                this.setSessionTickets(SessionTickets.getByCode(JSONHelper.getIntIfExists(endpointDetailsJSONObject, SESSION_TICKETS)));
            }

            if (endpointDetailsJSONObject.has(OCSP_STAPLING)) {
                this.setOcspStapling(JSONHelper.getBooleanIfExists(endpointDetailsJSONObject, OCSP_STAPLING));
            }

            if (endpointDetailsJSONObject.has(HSTS_POLICY)) {
                JSONObject hstsPolicyJSONObject = endpointDetailsJSONObject.getJSONObject(HSTS_POLICY);

                if (hstsPolicyJSONObject != null) {
                    this.setHstsPolicy(new HstsPolicy(hstsPolicyJSONObject));
                } else {
                    this.setHstsPolicy(new HstsPolicy(null));
                }
            }

            if (endpointDetailsJSONObject.has(HPKP_POLICY)) {
                JSONObject hpkpPolicyJSONObject = endpointDetailsJSONObject.getJSONObject(HPKP_POLICY);

                if (hpkpPolicyJSONObject != null) {
                    this.setHpkpPolicy(new HpkpPolicy(hpkpPolicyJSONObject));
                }
            }

            if (endpointDetailsJSONObject.has(HPKP_RO_POLICY)) {
                JSONObject hpkpROPolicyJSONObject = endpointDetailsJSONObject.getJSONObject(HPKP_RO_POLICY);

                if (hpkpROPolicyJSONObject != null) {
                    this.setHpkpROPolicy(new HpkpPolicy(hpkpROPolicyJSONObject));
                }
            }

            if (endpointDetailsJSONObject.has(DH_USES_KNOWN_PRIMES)) {
                this.setDhUsesKnownPrimes(DhUsesKnownPrimes.getByCode(JSONHelper.getIntIfExists(endpointDetailsJSONObject, DH_USES_KNOWN_PRIMES)));
            }

            if (endpointDetailsJSONObject.has(DH_YS_REUSE)) {
                this.setDhYsReuse(JSONHelper.getBooleanIfExists(endpointDetailsJSONObject, DH_YS_REUSE));
            }
        }
    }
}
