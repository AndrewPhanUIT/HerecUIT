package uit.herec.service.org.tanphu.form;

import java.io.Serializable;
import java.util.List;

import uit.herec.service.org.quan12.form.Quan12DiagnosisFormAllergy;
import uit.herec.service.org.quan12.form.Quan12DiagnosisFormMedication;

public class TanPhuDiagnosisFormDetail implements Serializable {

    private static final long serialVersionUID = 1275193966341919686L;
    private TanPhuClinicianForm clinician;
    private String symptons;
    private List<TanPhuDiagnosisFormMedication> medications;
    private List<TanPhuDiagnosisFormAllergy> allergies;

    public TanPhuDiagnosisFormDetail(TanPhuClinicianForm clinician, String symptons,
            List<TanPhuDiagnosisFormMedication> medications, List<TanPhuDiagnosisFormAllergy> allergies) {
        super();
        this.clinician = clinician;
        this.symptons = symptons;
        this.medications = medications;
        this.allergies = allergies;
    }

    public TanPhuDiagnosisFormDetail() {
        super();
    }

    public TanPhuClinicianForm getClinician() {
        return clinician;
    }

    public void setClinician(TanPhuClinicianForm clinician) {
        this.clinician = clinician;
    }

    public String getSymptons() {
        return symptons;
    }

    public void setSymptons(String symptons) {
        this.symptons = symptons;
    }

    public List<TanPhuDiagnosisFormMedication> getMedications() {
        return medications;
    }

    public void setMedications(List<TanPhuDiagnosisFormMedication> medications) {
        this.medications = medications;
    }

    public List<TanPhuDiagnosisFormAllergy> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<TanPhuDiagnosisFormAllergy> allergies) {
        this.allergies = allergies;
    }

}
