package uit.herec.service.org.quan12.form;

import java.io.Serializable;
import java.util.List;

public class Quan12DiagnosisFormDetail implements Serializable {

    private static final long serialVersionUID = 316237708437833283L;
    private String clinician;
    private List<String> symptons;
    private List<Quan12DiagnosisFormMedication> medications;
    private List<Quan12DiagnosisFormAllergy> allergies;

    public Quan12DiagnosisFormDetail(String clinician, List<String> symptons,
            List<Quan12DiagnosisFormMedication> medications, List<Quan12DiagnosisFormAllergy> allergies) {
        super();
        this.clinician = clinician;
        this.symptons = symptons;
        this.medications = medications;
        this.allergies = allergies;
    }

    public Quan12DiagnosisFormDetail() {
        super();
    }

    public String getClinician() {
        return clinician;
    }

    public void setClinician(String clinician) {
        this.clinician = clinician;
    }

    public List<String> getSymptons() {
        return symptons;
    }

    public void setSymptons(List<String> symptons) {
        this.symptons = symptons;
    }

    public List<Quan12DiagnosisFormMedication> getMedications() {
        return medications;
    }

    public void setMedications(List<Quan12DiagnosisFormMedication> medications) {
        this.medications = medications;
    }

    public List<Quan12DiagnosisFormAllergy> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<Quan12DiagnosisFormAllergy> allergies) {
        this.allergies = allergies;
    }

}
