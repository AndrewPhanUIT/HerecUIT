package uit.herec.common.form;

public class Form {
    private String code;
    private String phoneNumber;

    public Form(String code, String phoneNumber) {
        super();
        this.code = code;
        this.phoneNumber = phoneNumber;
    }

    public Form() {
        super();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
