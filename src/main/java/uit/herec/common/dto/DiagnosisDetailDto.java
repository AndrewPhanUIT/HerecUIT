package uit.herec.common.dto;

public class DiagnosisDetailDto {
    private String patientId;
    private String fullName;
    private String dob;
    private String phoneNumber;
    private String address;
    private DiagnosisDto[] diagnosisDtos;
    private String diagnosis;

    public DiagnosisDetailDto(String patientId, String fullName, String dob, String phoneNumber, String address,
            DiagnosisDto[] diagnosisDtos, String diagnosis) {
        super();
        this.patientId = patientId;
        this.fullName = fullName;
        this.dob = dob;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.diagnosisDtos = diagnosisDtos;
        this.diagnosis = diagnosis;
    }

    public DiagnosisDetailDto() {
        super();
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public DiagnosisDto[] getDiagnosisDtos() {
        return diagnosisDtos;
    }

    public void setDiagnosisDtos(DiagnosisDto[] diagnosisDtos) {
        this.diagnosisDtos = diagnosisDtos;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    @Override
    public String toString() {
        return "DiagnosisDetailDto [patientId=" + patientId + ", fullName=" + fullName + ", dob=" + dob
                + ", phoneNumber=" + phoneNumber + ", address=" + address + ", diagnosis=" + diagnosis + "]";
    }

    
}
