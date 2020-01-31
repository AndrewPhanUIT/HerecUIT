package uit.herec.common.form;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

public class LoginForm implements Serializable {

    private static final long serialVersionUID = 8110355862993455723L;

    @NotBlank
    private String phoneNumber;

    @NotBlank
    private String password;

    public LoginForm(@NotBlank String phoneNumber, @NotBlank String password) {
        super();
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public LoginForm() {
        super();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
