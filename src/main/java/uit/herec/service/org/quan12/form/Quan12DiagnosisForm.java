package uit.herec.service.org.quan12.form;

import java.io.Serializable;

public class Quan12DiagnosisForm implements Serializable {

    private static final long serialVersionUID = 1L;
    private String code;
    private String patientPhoneNumber;
    private String createdAt;
    private Quan12DiagnosisFormDetail data;

    public Quan12DiagnosisForm(String code, String patientPhoneNumber, String createdAt,
            Quan12DiagnosisFormDetail data) {
        super();
        this.code = code;
        this.patientPhoneNumber = patientPhoneNumber;
        this.createdAt = createdAt;
        this.data = data;
    }

    public Quan12DiagnosisForm() {
        super();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPatientPhoneNumber() {
        return patientPhoneNumber;
    }

    public void setPatientPhoneNumber(String patientPhoneNumber) {
        this.patientPhoneNumber = patientPhoneNumber;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Quan12DiagnosisFormDetail getData() {
        return data;
    }

    public void setData(Quan12DiagnosisFormDetail data) {
        this.data = data;
    }

}
