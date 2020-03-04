package uit.herec.service.org.tanphu.form;

import java.io.Serializable;

public class TanPhuDiagnosisForm implements Serializable {
    private static final long serialVersionUID = 7490031598890731267L;
    private String code;
    private String patientPhoneNumber;
    private String createdAt;
    private TanPhuDiagnosisFormDetail data;

    public TanPhuDiagnosisForm(String code, String patientPhoneNumber, String createdAt,
            TanPhuDiagnosisFormDetail data) {
        super();
        this.code = code;
        this.patientPhoneNumber = patientPhoneNumber;
        this.createdAt = createdAt;
        this.data = data;
    }

    public TanPhuDiagnosisForm() {
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

    public TanPhuDiagnosisFormDetail getData() {
        return data;
    }

    public void setData(TanPhuDiagnosisFormDetail data) {
        this.data = data;
    }

}
