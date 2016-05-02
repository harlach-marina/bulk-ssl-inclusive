package api.lw.ssl.analyze.enums;

/**
 * Created by zmushko_m on 29.04.2016.
 */
public enum WeakKeyDebian {

    WEAK(true, "Yes"),
    STRONG(false, "No");

    WeakKeyDebian(boolean isWeak, String value) {
        this.isWeak = isWeak;
        this.value = value;
    }

    private boolean isWeak;
    private String value;

    public boolean isWeak() {
        return isWeak;
    }

    public String getValue() {
        return value;
    }

    public static WeakKeyDebian getByIsWeak(Boolean isWeak) {
        if (isWeak != null) {
            if (isWeak) {
                return WeakKeyDebian.WEAK;
            }

            return WeakKeyDebian.STRONG;
        }

        return null;
    }
}
