package api.lw.ssl.analyze.ssllabsentity;

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
public class Certificate {
    private static final String SUBJECT = "subject";
    private static final String COMMON_NAMES = "commonNames";
    private static final String ALTERNATIVE_NAMES = "altNames";
    private static final String VALID_FROM_DATE = "notBefore";
    private static final String VALID_TO_DATE = "notAfter";

    private static final String ISSUER = "issuerLabel";
    private static final String SIGNATURE_ALGOTITHM = "sigAlg";
    private static final String VALIDATION_TYPE = "validationType";
    private static final String REVOCATION_STATUS = "revocationStatus";
    private static final String REVOCATION_INFO = "revocationInfo";

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

    Certificate(JSONObject certJsonObject) {
        fillFromJSONObject(certJsonObject);
    }

    private void fillFromJSONObject(JSONObject certJSONObject) {
        if (certJSONObject != null) {
            String subject = StringUtils.substringBetween(JSONHelper.getStringIfExists(certJSONObject, SUBJECT), "CN=", ",");
            if (StringUtils.isBlank(subject)) {
                subject = StringUtils.substringAfterLast(JSONHelper.getStringIfExists(certJSONObject, SUBJECT), "CN=");
            }
            this.subject = subject;

            if (certJSONObject.has(COMMON_NAMES)) {
                JSONArray commonNames = certJSONObject.getJSONArray(COMMON_NAMES);
                this.commonNames = JSONHelper.getStringListFromJSONArray(commonNames);
            }

            if (certJSONObject.has(ALTERNATIVE_NAMES)) {
                JSONArray alternativeNames = certJSONObject.getJSONArray(ALTERNATIVE_NAMES);
                this.alternativeNames = (JSONHelper.getStringListFromJSONArray(alternativeNames));
            }

            Long validForm = JSONHelper.getLongIfExists(certJSONObject, VALID_FROM_DATE);
            if (validForm != null) {
                Calendar validFromCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                validFromCalendar.setTimeInMillis(validForm);
                this.validFromDate = validFromCalendar;
            }

            Long validTo = JSONHelper.getLongIfExists(certJSONObject, VALID_TO_DATE);
            if (validTo != null) {
                Calendar validToCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                validToCalendar.setTimeInMillis(validTo);
                this.validUntilDate = validToCalendar;
            }

            this.issuer = JSONHelper.getStringIfExists(certJSONObject, ISSUER);
            this.signatureAlgorithm = JSONHelper.getStringIfExists(certJSONObject, SIGNATURE_ALGOTITHM);

            this.extendedValidation = ExtendedValidation.getById(JSONHelper.getStringIfExists(certJSONObject, VALIDATION_TYPE));
            this.revocationStatus = RevocationStatus.getId(JSONHelper.getIntIfExists(certJSONObject, REVOCATION_STATUS));
            this.revocationInfo = (RevocationInfo.getByCode(JSONHelper.getIntIfExists(certJSONObject, REVOCATION_INFO)));
        }
    }

    public String getSubject() {
        return subject;
    }

    public List<String> getCommonNames() {
        return commonNames;
    }

    public List<String> getAlternativeNames() {
        return alternativeNames;
    }

    public Calendar getValidFromDate() {
        return validFromDate;
    }

    public Calendar getValidUntilDate() {
        return validUntilDate;
    }

    public String getIssuer() {
        return issuer;
    }

    public String getSignatureAlgorithm() {
        return signatureAlgorithm;
    }

    public ExtendedValidation getExtendedValidation() {
        return extendedValidation;
    }

    public RevocationStatus getRevocationStatus() {
        return revocationStatus;
    }

    public RevocationInfo getRevocationInfo() {
        return revocationInfo;
    }
}
