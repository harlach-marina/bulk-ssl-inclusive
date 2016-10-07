package lw.ssl.analyze.pojo.ssllabs.newpack;

/**
 * Created on 07.10.2016;
 *
 * @author p.sinitskiy (adronex303@gmail.com);
 * @since 1.0.
 */
class ProtocolDetails {
    private String renegotiationSupport;
    private Boolean beastAttack;
    private Boolean poodleSsl3;
    private String poodleTls;
    private String downgradeAttackPrevention;
    private Boolean sslTlsCompression;
    private Boolean rc4;
    private Boolean heartbeat;
    private Boolean heartBleed;
    private String openSslCcs;
    private String forwardSecrecy;
    private Boolean NPN;
    private String sessionResumptionCaching;
    private String sessionResumptionTickets;
    private Boolean ocspStapling;
    private String hstsStatus;
    private String hpkpStatus;
    private String hpkpRoStatus;
    private String usesCommonDhPrimes;
    private String dhPublicServerParam;

    private ProtocolDetails() {
    }

    public String getRenegotiationSupport() {
        return renegotiationSupport;
    }

    public Boolean getBeastAttack() {
        return beastAttack;
    }

    public Boolean getPoodleSsl3() {
        return poodleSsl3;
    }

    public String getPoodleTls() {
        return poodleTls;
    }

    public String getDowngradeAttackPrevention() {
        return downgradeAttackPrevention;
    }

    public Boolean getSslTlsCompression() {
        return sslTlsCompression;
    }

    public Boolean getRc4() {
        return rc4;
    }

    public Boolean getHeartbeat() {
        return heartbeat;
    }

    public Boolean getHeartBleed() {
        return heartBleed;
    }

    public String getOpenSslCcs() {
        return openSslCcs;
    }

    public String getForwardSecrecy() {
        return forwardSecrecy;
    }

    public Boolean getNPN() {
        return NPN;
    }

    public String getSessionResumptionCaching() {
        return sessionResumptionCaching;
    }

    public String getSessionResumptionTickets() {
        return sessionResumptionTickets;
    }

    public Boolean getOcspStapling() {
        return ocspStapling;
    }

    public String getHstsStatus() {
        return hstsStatus;
    }

    public String getHpkpStatus() {
        return hpkpStatus;
    }

    public String getHpkpRoStatus() {
        return hpkpRoStatus;
    }

    public String getUsesCommonDhPrimes() {
        return usesCommonDhPrimes;
    }

    public String getDhPublicServerParam() {
        return dhPublicServerParam;
    }
}
