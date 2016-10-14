package lw.ssl.analyze.pojo.ssllabs.ssllabsentity;

import api.lw.ssl.analyze.enums.*;
import lw.ssl.analyze.utils.JSONHelper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zmushko_m on 29.04.2016.
 */
public class EndpointDetails {
    private static final String KEY = "key";
    private static final String CERT = "cert";
    private static final String PROTOCOLS = "protocols";
    private static final String SUITES = "suites";
    private static final String RENEGOTATOIN_SUPPORT = "renegSupport";
    private static final String VULNERABLE_BEAST = "vulnBeast";
    private static final String POODLE = "poodle";
    private static final String POODLE_TLS = "poodleTls";
    private static final String DOWNGRADE_ATTACK_PREVENTION = "fallbackScsv";
    private static final String SSL_TLS_COMPRESSION = "compressionMethods";
    private static final String RC4 = "supportsRc4";
    private static final String HEARTBEAT = "heartbeat";
    private static final String HEARTBLEED = "heartbleed";
    private static final String OPEN_SSL_CCS = "openSslCcs";
    private static final String FORWARD_SECRECY = "forwardSecrecy";
    private static final String NPN = "supportsNpn";
    private static final String SESSION_RESUMPTION = "sessionResumption";
    private static final String SESSION_TICKETS = "sessionTickets";
    private static final String OCSP_STAPLING = "ocspStapling";
    private static final String HSTS_POLICY = "hstsPolicy";
    private static final String HPKP_POLICY = "hpkpPolicy";
    private static final String HPKP_RO_POLICY = "hpkpRoPolicy";
    private static final String DH_USES_KNOWN_PRIMES = "dhUsesKnownPrimes";
    private static final String DH_YS_REUSE = "dhYsReuse";

    private Certificate certificate;
    private Key key;
    private ProtocolContainer protocols = new ProtocolContainer();
    private RenegotationSupport renegotationSupport;
    private Boolean vulnerableBeast;
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
    private List<Suite> suites = new ArrayList<>();

    EndpointDetails(JSONObject endpointDetailsJSON) {
        fillFromJSONObject(endpointDetailsJSON);
    }

    private void fillFromJSONObject(JSONObject endpointDetailsJSONObject) {
        if (endpointDetailsJSONObject != null) {
            if (endpointDetailsJSONObject.has(CERT)) {
                JSONObject certJSONObject = endpointDetailsJSONObject.getJSONObject(CERT);
                this.certificate = new Certificate(certJSONObject);
            } else {
                this.certificate = new Certificate(null);
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
                        boolean isAvailable = true;
                        Integer insecureFlag = JSONHelper.getIntIfExists(protocolJSONObject, ProtocolDetails.INSECURE_FLAG);
                        boolean isInsecure = false;
                        if (insecureFlag != null && insecureFlag == 0) {
                            isInsecure = true;
                        }

                        protocols.setProtocolInfoByNameAndVersion(name, version, isAvailable, isInsecure);
                    }
                }
            }

            if (endpointDetailsJSONObject.has(SUITES)) {
                JSONArray suitesJson = endpointDetailsJSONObject.getJSONObject("suites").getJSONArray("list");
                if (suitesJson != null) {
                    for (int suitesPointer = 0; suitesPointer < suitesJson.length(); suitesPointer++) {
                        JSONObject suitesJSONObject = suitesJson.getJSONObject(suitesPointer);

                        String name = suitesJSONObject.getString("name");
                        Integer strength = suitesJSONObject.getInt("cipherStrength");

                        suites.add(new Suite(name, strength));
                    }
                }
            }

            if (endpointDetailsJSONObject.has(RENEGOTATOIN_SUPPORT)) {
                renegotationSupport = RenegotationSupport.getByCode(JSONHelper.getIntIfExists(endpointDetailsJSONObject, RENEGOTATOIN_SUPPORT));
            }

            if (endpointDetailsJSONObject.has(VULNERABLE_BEAST)) {
                vulnerableBeast = JSONHelper.getBooleanIfExists(endpointDetailsJSONObject, VULNERABLE_BEAST);
            }

            if (endpointDetailsJSONObject.has(POODLE)) {
                poodle = JSONHelper.getBooleanIfExists(endpointDetailsJSONObject, POODLE);
            }

            if (endpointDetailsJSONObject.has(POODLE_TLS)) {
                poodleTLS = PoodleTLS.getByCode(JSONHelper.getIntIfExists(endpointDetailsJSONObject, POODLE_TLS));
            }

            if (endpointDetailsJSONObject.has(DOWNGRADE_ATTACK_PREVENTION)) {
                fallbackScsv = JSONHelper.getBooleanIfExists(endpointDetailsJSONObject, DOWNGRADE_ATTACK_PREVENTION);
            }

            if (endpointDetailsJSONObject.has(SSL_TLS_COMPRESSION)) {
                ssLtlsCompression = SSLtlsCompression.getByCode(JSONHelper.getIntIfExists(endpointDetailsJSONObject, SSL_TLS_COMPRESSION));
            }

            if (endpointDetailsJSONObject.has(RC4)) {
                supportsRc4 = JSONHelper.getBooleanIfExists(endpointDetailsJSONObject, RC4);
            }

            if (endpointDetailsJSONObject.has(HEARTBEAT)) {
                heartBeat = JSONHelper.getBooleanIfExists(endpointDetailsJSONObject, HEARTBEAT);
            }

            if (endpointDetailsJSONObject.has(HEARTBLEED)) {
                heartBleed = JSONHelper.getBooleanIfExists(endpointDetailsJSONObject, HEARTBLEED);
            }

            if (endpointDetailsJSONObject.has(OPEN_SSL_CCS)) {
                openSSLccs = OpenSSLccs.getByCode(JSONHelper.getIntIfExists(endpointDetailsJSONObject, OPEN_SSL_CCS));
            }

            if (endpointDetailsJSONObject.has(FORWARD_SECRECY)) {
                forwardSecrecy = ForwardSecrecy.getByCode(JSONHelper.getIntIfExists(endpointDetailsJSONObject, FORWARD_SECRECY));
            }

            if (endpointDetailsJSONObject.has(NPN)) {
                npn = JSONHelper.getBooleanIfExists(endpointDetailsJSONObject, NPN);
            }

            if (endpointDetailsJSONObject.has(SESSION_RESUMPTION)) {
                sessionResumption = SessionResumption.getByCode(JSONHelper.getIntIfExists(endpointDetailsJSONObject, SESSION_RESUMPTION));
            }

            if (endpointDetailsJSONObject.has(SESSION_TICKETS)) {
                sessionTickets = SessionTickets.getByCode(JSONHelper.getIntIfExists(endpointDetailsJSONObject, SESSION_TICKETS));
            }

            if (endpointDetailsJSONObject.has(OCSP_STAPLING)) {
                ocspStapling = JSONHelper.getBooleanIfExists(endpointDetailsJSONObject, OCSP_STAPLING);
            }

            if (endpointDetailsJSONObject.has(HSTS_POLICY)) {
                JSONObject hstsPolicyJSONObject = endpointDetailsJSONObject.getJSONObject(HSTS_POLICY);
                hstsPolicy = new HstsPolicy(hstsPolicyJSONObject);
            }

            if (endpointDetailsJSONObject.has(HPKP_POLICY)) {
                JSONObject hpkpPolicyJSONObject = endpointDetailsJSONObject.getJSONObject(HPKP_POLICY);

                if (hpkpPolicyJSONObject != null) {
                    hpkpPolicy = new HpkpPolicy(hpkpPolicyJSONObject);
                }
            }

            if (endpointDetailsJSONObject.has(HPKP_RO_POLICY)) {
                JSONObject hpkpROPolicyJSONObject = endpointDetailsJSONObject.getJSONObject(HPKP_RO_POLICY);

                if (hpkpROPolicyJSONObject != null) {
                    hpkpROPolicy = new HpkpPolicy(hpkpROPolicyJSONObject);
                }
            }

            if (endpointDetailsJSONObject.has(DH_USES_KNOWN_PRIMES)) {
                dhUsesKnownPrimes = DhUsesKnownPrimes.getByCode(JSONHelper.getIntIfExists(endpointDetailsJSONObject, DH_USES_KNOWN_PRIMES));
            }

            if (endpointDetailsJSONObject.has(DH_YS_REUSE)) {
                dhYsReuse = JSONHelper.getBooleanIfExists(endpointDetailsJSONObject, DH_YS_REUSE);
            }
        }
    }

    public Certificate getCertificate() {
        return certificate;
    }

    public Key getKey() {
        return key;
    }

    public ProtocolContainer getProtocols() {
        return protocols;
    }

    public List<Suite> getSuites() {
        return suites;
    }

    public RenegotationSupport getRenegotationSupport() {
        return renegotationSupport;
    }

    public Boolean getVulnerableBeast() {
        return vulnerableBeast;
    }

    public Boolean getPoodle() {
        return poodle;
    }

    public PoodleTLS getPoodleTLS() {
        return poodleTLS;
    }

    public Boolean getFallbackScsv() {
        return fallbackScsv;
    }

    public SSLtlsCompression getSsLtlsCompression() {
        return ssLtlsCompression;
    }

    public Boolean getSupportsRc4() {
        return supportsRc4;
    }

    public Boolean getHeartBeat() {
        return heartBeat;
    }

    public Boolean getHeartBleed() {
        return heartBleed;
    }

    public OpenSSLccs getOpenSSLccs() {
        return openSSLccs;
    }

    public ForwardSecrecy getForwardSecrecy() {
        return forwardSecrecy;
    }

    public Boolean getNpn() {
        return npn;
    }

    public SessionResumption getSessionResumption() {
        return sessionResumption;
    }

    public SessionTickets getSessionTickets() {
        return sessionTickets;
    }

    public Boolean getOcspStapling() {
        return ocspStapling;
    }

    public HstsPolicy getHstsPolicy() {
        return hstsPolicy;
    }

    public HpkpPolicy getHpkpPolicy() {
        return hpkpPolicy;
    }

    public HpkpPolicy getHpkpROPolicy() {
        return hpkpROPolicy;
    }

    public DhUsesKnownPrimes getDhUsesKnownPrimes() {
        return dhUsesKnownPrimes;
    }

    public Boolean getDhYsReuse() {
        return dhYsReuse;
    }
}
