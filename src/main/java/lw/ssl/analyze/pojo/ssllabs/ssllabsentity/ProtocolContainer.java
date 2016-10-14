package lw.ssl.analyze.pojo.ssllabs.ssllabsentity;

import api.lw.ssl.analyze.enums.Protocol;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zmushko_m on 29.04.2016.
 */
public class ProtocolContainer {
    private List<ProtocolDetails> protocolDetailsList;

    ProtocolContainer() {
        protocolDetailsList = new ArrayList<>();
        protocolDetailsList.add(new ProtocolDetails(Protocol.TLS_1_0));
        protocolDetailsList.add(new ProtocolDetails(Protocol.TLS_1_1));
        protocolDetailsList.add(new ProtocolDetails(Protocol.TLS_1_2));
        protocolDetailsList.add(new ProtocolDetails(Protocol.SSL_2));
        protocolDetailsList.add(new ProtocolDetails(Protocol.SSL_3));
    }

    void setProtocolInfoByNameAndVersion(String name, String version, boolean isAvailable, boolean isInsecure) {
        if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(version)) {
            for (ProtocolDetails protocolDetails : protocolDetailsList) {
                if (protocolDetails.getProtocol().identifyProtocol(name, version)) {
                    protocolDetails.setAvailible(isAvailable);
                    protocolDetails.setInsecure(isInsecure);
                }
            }
        }
    }

    public Boolean isProtocolHere(Protocol protocol) {
        return protocol != null &&
                protocolDetailsList.stream().filter(p -> protocol.equals(p.getProtocol())
                        && p.isAvailible()).count() > 0;
    }

    public ProtocolDetails getProtocolDetailByNameAndVersion(String name, String version) {
        if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(version)) {
            for (ProtocolDetails protocolDetails : protocolDetailsList) {
                if (protocolDetails.getProtocol().identifyProtocol(name, version)) {
                    return protocolDetails;
                }
            }
        }

        return null;
    }
}
