package api.lw.ssl.analyze.enums;

/**
 * Created by zmushko_m on 28.04.2016.
 */
public enum ExtendedValidation {
//    E for Extended Validation certificates; may be null if unable to determine

    YES("E", "Yes"),
    No("D", "No");

    ExtendedValidation(String value, String code) {
        this.code = code;
        this.value = value;
    }

    private String code;
    private String value;

    public String getValue() {
        return value;
    }

    public String getCode() {
        return code;
    }

    public static ExtendedValidation getById(String code) {
        if (code != null) {
            for(ExtendedValidation e : values()) {
                if(e.value.equals(code)) {
                    return e;
                }
            }
        }

        return ExtendedValidation.No;
    }
}
