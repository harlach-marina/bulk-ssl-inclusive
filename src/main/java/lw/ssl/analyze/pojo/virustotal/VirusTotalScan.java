package lw.ssl.analyze.pojo.virustotal;

/**
 * Created on 06.10.2016;
 *
 * @author p.sinitskiy (adronex303@gmail.com);
 * @since 1.0.
 */
public class VirusTotalScan {

    private String antivirus;
    private Boolean detected;
    private String result;
    private String detail;

    VirusTotalScan(String antivirus, Boolean detected, String result, String detail) {
        this.antivirus = antivirus;
        this.detected = detected;
        this.result = result;
        this.detail = detail;
    }

    public String getAntivirus() {
        return antivirus;
    }

    public Boolean getDetected() {
        return detected;
    }

    public String getResult() {
        return result;
    }

    public String getDetail() {
        return detail;
    }
}
