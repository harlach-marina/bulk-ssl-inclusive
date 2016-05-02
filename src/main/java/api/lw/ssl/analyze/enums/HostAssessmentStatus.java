package api.lw.ssl.analyze.enums;

/**
 * Created by zmushko_m on 29.04.2016.
 */
public enum HostAssessmentStatus {
    READY("READY"),
    DNS("DNS"),
    IN_PROGRESS("IN_PROGRESS"),
    ERROR("ERROR");

    HostAssessmentStatus(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }

    public static HostAssessmentStatus getByName(String name) {
        if (name != null) {
            for(HostAssessmentStatus e : values()) {
                if(e.name.equals(name)) {
                    return e;
                }
            }
        }

        return HostAssessmentStatus.ERROR;
    }
}
