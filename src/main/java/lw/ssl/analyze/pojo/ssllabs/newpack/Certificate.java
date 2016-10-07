package lw.ssl.analyze.pojo.ssllabs.newpack;

import java.util.Date;
import java.util.List;

/**
 * Created on 07.10.2016;
 *
 * @author p.sinitskiy (adronex303@gmail.com);
 * @since 1.0.
 */
class Certificate {

    private String subject; // Server Key and Certificate
    private List<String> alternativeNames; // Server Key and Certificate
    private Date validFrom; // Server Key and Certificate
    private Date validUntil; // Server Key and Certificate
    private String key; // Server Key and Certificate
    private String weakKey; // Server Key and Certificate
    private String issuer; // Server Key and Certificate
    private String signatureAlgorithm; // Server Key and Certificate
    private Boolean extendedValidation; // Server Key and Certificate
    private String revocationInformation; // Server Key and Certificate
    private String revocationStatus; // Server Key and Certificate

    private Certificate() {
    }

    public String getSubject() {
        return subject;
    }

    public List<String> getAlternativeNames() {
        return alternativeNames;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public Date getValidUntil() {
        return validUntil;
    }

    public String getKey() {
        return key;
    }

    public String getWeakKey() {
        return weakKey;
    }

    public String getIssuer() {
        return issuer;
    }

    public String getSignatureAlgorithm() {
        return signatureAlgorithm;
    }

    public Boolean getExtendedValidation() {
        return extendedValidation;
    }

    public String getRevocationInformation() {
        return revocationInformation;
    }

    public String getRevocationStatus() {
        return revocationStatus;
    }
}
