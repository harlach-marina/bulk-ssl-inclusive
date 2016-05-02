package lw.ssl.analyze.pojo;

/**
 * Created by zmushko_m on 21.04.2016.
 */
public class WebResourceDescription {
    public WebResourceDescription() {

    }

    public WebResourceDescription(String host) {
        this.host = host;
    }

    public WebResourceDescription(String host, String port) {
        this.host = host;
        this.port = port;
    }

    private String host;
    private String port;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
