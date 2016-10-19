package lw.ssl.analyze.utils.notificators;

/**
 * Created on 17.10.2016;
 *
 * @author p.sinitskiy (adronex303@gmail.com);
 * @since 1.0.
 */
public enum FileExtension {
    PDF(".pdf"),
    XLS(".xls");

    private String value;

    FileExtension(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
