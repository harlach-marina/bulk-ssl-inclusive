package api.lw.ssl.analyze.enums;

/**
 * Created by zmushko_m on 29.04.2016.
 */
public enum Protocol {
    TLS_1_0("TLS", "1.0"),
    TLS_1_1("TLS", "1.1"),
    TLS_1_2("TLS", "1.2"),
    SSL_2("SSL", "2.0"),
    SSL_3("SSL", "3.0");

    Protocol(String name, String version) {
        this.name = name;
        this.version = version;
    }

    private String name;
    private String version;

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public boolean identifyProtocol(String name, String version) {
        return name.equals(this.name) && this.version.equals(version);
    }
}
