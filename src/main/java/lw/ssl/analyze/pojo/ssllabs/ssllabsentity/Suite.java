package lw.ssl.analyze.pojo.ssllabs.ssllabsentity;

/**
 * Created on 10.10.2016;
 *
 * @author p.sinitskiy (adronex303@gmail.com);
 * @since 1.0.
 */
public class Suite {
    private String name;
    private Integer cipherStrength;

    Suite(String name, Integer cipherStrength) {
        this.name = name;
        this.cipherStrength = cipherStrength;
    }

    public String getName() {
        return name;
    }

    public Integer getCipherStrength() {
        return cipherStrength;
    }
}
