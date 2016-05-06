package api.lw.ssl.analyze;

import api.lw.ssl.analyze.enums.ExtendedValidation;
import api.lw.ssl.analyze.enums.RevocationInfo;
import api.lw.ssl.analyze.enums.RevocationStatus;
import lw.ssl.analyze.utils.JSONHelper;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by zmushko_m on 28.04.2016.
 */
public class Cert {
    public static final String SUBJECT = "subject";
    public static final String COMMON_NAMES = "commonNames";
    public static final String ALTERNATIVE_NAMES = "altNames";
    public static final String VALID_FROM_DATE = "notBefore";
    public static final String VALID_TO_DATE = "notAfter";

    public static final String ISSUER = "issuerLabel";
    public static final String SIGNATURE_ALGOTITHM = "sigAlg";
    public static final String VALIDATION_TYPE = "validationType";
    public static final String REVOCATION_STATUS = "revocationStatus";
    public static final String REVOCATION_INFO = "revocationInfo";


    public Cert() {
    }

    public Cert(JSONObject certJsonObject) {
        fillFromJSONObject(certJsonObject);
    }

    private String subject;
    private List<String> commonNames;
    private List<String> alternativeNames;
    private Calendar validFromDate;
    private Calendar validUntilDate;

    private String issuer;
    private String signatureAlgorithm;
    private ExtendedValidation extendedValidation;
    private RevocationStatus revocationStatus;
    private RevocationInfo revocationInfo;
    private String trusted;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<String> getCommonNames() {
        return commonNames;
    }

    public void setCommonNames(List<String> commonNames) {
        this.commonNames = commonNames;
    }

    public List<String> getAlternativeNames() {
        return alternativeNames;
    }

    public void setAlternativeNames(List<String> alternativeNames) {
        this.alternativeNames = alternativeNames;
    }

    public Calendar getValidFromDate() {
        return validFromDate;
    }

    public void setValidFromDate(Calendar validFromDate) {
        this.validFromDate = validFromDate;
    }

    public Calendar getValidUntilDate() {
        return validUntilDate;
    }

    public void setValidUntilDate(Calendar validUntilDate) {
        this.validUntilDate = validUntilDate;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getSignatureAlgorithm() {
        return signatureAlgorithm;
    }

    public void setSignatureAlgorithm(String signatureAlgorithm) {
        this.signatureAlgorithm = signatureAlgorithm;
    }

    public ExtendedValidation getExtendedValidation() {
        return extendedValidation;
    }

    public void setExtendedValidation(ExtendedValidation extendedValidation) {
        this.extendedValidation = extendedValidation;
    }

    public RevocationStatus getRevocationStatus() {
        return revocationStatus;
    }

    public void setRevocationStatus(RevocationStatus revocationStatus) {
        this.revocationStatus = revocationStatus;
    }

    public RevocationInfo getRevocationInfo() {
        return revocationInfo;
    }

    public void setRevocationInfo(RevocationInfo revocationInfo) {
        this.revocationInfo = revocationInfo;
    }

    public String getTrusted() {
        return trusted;
    }

    public void setTrusted(String trusted) {
        this.trusted = trusted;
    }
    
    public void fillFromJSONObject(JSONObject certJSONObject) {
        if (certJSONObject != null) {
            String subject = StringUtils.substringBetween(JSONHelper.getStringIfExists(certJSONObject, this.SUBJECT), "CN=", ",");
            if (StringUtils.isBlank(subject)) {
                subject = StringUtils.substringAfterLast(JSONHelper.getStringIfExists(certJSONObject, this.SUBJECT), "CN=");
            }
            this.setSubject(subject);

            if (certJSONObject.has(this.COMMON_NAMES)) {
                JSONArray commonNames = certJSONObject.getJSONArray(this.COMMON_NAMES);
                this.setCommonNames(JSONHelper.getStringListFromJSONArray(commonNames));
            }

            if (certJSONObject.has(this.ALTERNATIVE_NAMES)) {
                JSONArray alternativeNames = certJSONObject.getJSONArray(this.ALTERNATIVE_NAMES);
                this.setAlternativeNames(JSONHelper.getStringListFromJSONArray(alternativeNames));
            }

            Long validForm = JSONHelper.getLongIfExists(certJSONObject, this.VALID_FROM_DATE);
            if (validForm != null) {
                Calendar validFromCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                validFromCalendar.setTimeInMillis(validForm);
                this.setValidFromDate(validFromCalendar);
            }

            Long validTo = JSONHelper.getLongIfExists(certJSONObject, this.VALID_TO_DATE);
            if (validTo != null) {
                Calendar validToCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                validToCalendar.setTimeInMillis(validTo);
                this.setValidUntilDate(validToCalendar);
            }

            this.setIssuer(JSONHelper.getStringIfExists(certJSONObject, this.ISSUER));
            this.setSignatureAlgorithm(JSONHelper.getStringIfExists(certJSONObject, this.SIGNATURE_ALGOTITHM));

            this.setExtendedValidation(ExtendedValidation.getById(JSONHelper.getStringIfExists(certJSONObject, this.VALIDATION_TYPE)));
            this.setRevocationStatus(RevocationStatus.getId(JSONHelper.getIntIfExists(certJSONObject, this.REVOCATION_STATUS)));
            this.setRevocationInfo((RevocationInfo.getByCode(JSONHelper.getIntIfExists(certJSONObject, REVOCATION_INFO))));
        }
    }
}
