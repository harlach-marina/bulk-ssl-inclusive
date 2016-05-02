package api.lw.ssl.analyze;

import api.lw.ssl.analyze.enums.Protocol;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zmushko_m on 29.04.2016.
 */
public class ProtocolContainer {
    private List<ProtocolDetails> protocolDetailsList;

    public ProtocolContainer() {
        protocolDetailsList = new ArrayList<>();
        protocolDetailsList.add(new ProtocolDetails(Protocol.TLS_1_0));
        protocolDetailsList.add(new ProtocolDetails(Protocol.TLS_1_1));
        protocolDetailsList.add(new ProtocolDetails(Protocol.TLS_1_2));
        protocolDetailsList.add(new ProtocolDetails(Protocol.SSL_2));
        protocolDetailsList.add(new ProtocolDetails(Protocol.SSL_3));
    }

    public void setProtocolInfoByNameAndVersion(String name, String version, boolean isAvailible, boolean isInsecure) {
        if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(version)) {
            for (ProtocolDetails protocolDetails : protocolDetailsList) {
                if (protocolDetails.getProtocol().idetifyProtocol(name, version)) {
                    protocolDetails.setAvailible(isAvailible);
                    protocolDetails.setInsecure(isInsecure);
                }
            }
        }
    }

    public ProtocolDetails getProtocolDetailByNameAndVersion(String name, String version) {
        if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(version)) {
            for (ProtocolDetails protocolDetails : protocolDetailsList) {
                if (protocolDetails.getProtocol().idetifyProtocol(name, version)) {
                    return protocolDetails;
                }
            }
        }

        return null;
    }
}
