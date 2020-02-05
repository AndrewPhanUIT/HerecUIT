package uit.herec.common.dto;

public class DiagnosisDetailDto {
    private String key;
    private String phoneNumber;
    private DiagnosisDto diagnosis;

    public DiagnosisDetailDto(String key, String phoneNumber, DiagnosisDto diagnosis) {
        super();
        this.key = key;
        this.phoneNumber = phoneNumber;
        this.diagnosis = diagnosis;
    }
    
    public DiagnosisDetailDto() {
        super();
    }



    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public DiagnosisDto getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(DiagnosisDto diagnosis) {
        this.diagnosis = diagnosis;
    }

}
