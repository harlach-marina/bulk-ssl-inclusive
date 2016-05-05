package lw.ssl.analyze.pojo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by zmushko_m on 04.05.2016.
 */
public class UserData {
    public UserData(String userName, String eMail) {
        this.userName = userName;
        this.eMail = eMail;
    }

    private String userName;
    private String eMail;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof UserData)) return false;

        UserData userData = (UserData) o;

        return new EqualsBuilder()
                .append(userName, userData.userName)
                .append(eMail, userData.eMail)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(userName)
                .append(eMail)
                .toHashCode();
    }
}
