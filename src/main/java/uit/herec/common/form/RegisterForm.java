package uit.herec.common.form;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

public class RegisterForm implements Serializable {
    private static final long serialVersionUID = 3279153330062452035L;

    @NotBlank
    @Length(min = 5, max = 50)
    private String fullname;
    @NotBlank
    private String password;
    @NotBlank
    private String phoneNumber;

    public RegisterForm(@NotBlank @Length(min = 5, max = 50) String fullname, @NotBlank String password,
            @NotBlank String phoneNumber) {
        super();
        this.fullname = fullname;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public RegisterForm() {
        super();
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
