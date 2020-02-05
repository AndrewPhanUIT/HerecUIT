package uit.herec.common.dto;

import java.util.ArrayList;
import java.util.List;

public class DiagnosisDto {
    private String organization;

    private String clincian;

    private String createdAt;

    private List<AllergyDto> allergies = new ArrayList<AllergyDto>();

    private List<String> symptons = new ArrayList<>();

    private List<MedicationDto> medications = new ArrayList<>();

    public DiagnosisDto(String organization, String clincian, String createdAt, List<AllergyDto> allergies,
            List<String> symptons, List<MedicationDto> medications) {
        super();
        this.organization = organization;
        this.clincian = clincian;
        this.createdAt = createdAt;
        this.allergies = allergies;
        this.symptons = symptons;
        this.medications = medications;
    }

    public DiagnosisDto(String organization, String clincian, String createdAt) {
        super();
        this.organization = organization;
        this.clincian = clincian;
        this.createdAt = createdAt;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getClincian() {
        return clincian;
    }

    public void setClincian(String clincian) {
        this.clincian = clincian;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public List<AllergyDto> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<AllergyDto> allergies) {
        this.allergies = allergies;
    }

    public List<String> getSymptons() {
        return symptons;
    }

    public void setSymptons(List<String> symptons) {
        this.symptons = symptons;
    }

    public List<MedicationDto> getMedications() {
        return medications;
    }

    public void setMedications(List<MedicationDto> medications) {
        this.medications = medications;
    }
    
    
    
}
