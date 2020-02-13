package uit.herec.common.form;

import java.io.Serializable;

public class PermissionForm implements Serializable {

    private static final long serialVersionUID = -8738682299366865051L;
    private String orgHyperledgerName;
    private String phoneNumber;

    public PermissionForm(String orgHyperledgerName, String phoneNumber) {
        super();
        this.orgHyperledgerName = orgHyperledgerName;
        this.phoneNumber = phoneNumber;
    }

    public PermissionForm() {
        super();
    }

    public String getOrgHyperledgerName() {
        return orgHyperledgerName;
    }

    public void setOrgHyperledgerName(String orgHyperledgerName) {
        this.orgHyperledgerName = orgHyperledgerName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
