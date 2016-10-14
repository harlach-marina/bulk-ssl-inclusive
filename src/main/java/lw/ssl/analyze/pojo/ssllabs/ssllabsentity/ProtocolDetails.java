package lw.ssl.analyze.pojo.ssllabs.ssllabsentity;

import api.lw.ssl.analyze.enums.Protocol;

/**
 * Created by zmushko_m on 29.04.2016.
 */
public class ProtocolDetails {
    public final static String NAME = "name";
    public final static String VERSION = "version";
    public final static String INSECURE_FLAG = "q";

    private Protocol protocol;
    private boolean isAvailible;
    private boolean isInsecure;

    ProtocolDetails(Protocol protocol) {
        this.protocol = protocol;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }

    public boolean isAvailible() {
        return isAvailible;
    }

    public void setAvailible(boolean availible) {
        isAvailible = availible;
    }

    public boolean isInsecure() {
        return isInsecure;
    }

    public void setInsecure(boolean insecure) {
        isInsecure = insecure;
    }
}
