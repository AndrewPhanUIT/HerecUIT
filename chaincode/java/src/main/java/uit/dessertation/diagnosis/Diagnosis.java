package uit.dessertation.diagnosis;

import java.util.ArrayList;
import java.util.List;

public class Diagnosis {

    private String id;
    private String organization;
    private String clincian;
    private String createdAt;
    private List<Allergy> allergies = new ArrayList<Allergy>();
    private List<String> symptons = new ArrayList<>();
    private List<Medication> medications = new ArrayList<>();

    public Diagnosis(String id, String organization, String clincian, String createdAt, List<Allergy> allergies,
            List<String> symptons, List<Medication> medications) {
        super();
        this.id = id;
        this.organization = organization;
        this.clincian = clincian;
        this.createdAt = createdAt;
        this.allergies = allergies;
        this.symptons = symptons;
        this.medications = medications;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public List<Allergy> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<Allergy> allergies) {
        this.allergies = allergies;
    }

    public List<String> getSymptons() {
        return symptons;
    }

    public void setSymptons(List<String> symptons) {
        this.symptons = symptons;
    }

    public List<Medication> getMedications() {
        return medications;
    }

    public void setMedications(List<Medication> medications) {
        this.medications = medications;
    }

}
