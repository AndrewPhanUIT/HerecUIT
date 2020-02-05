package uit.dessertation.diagnosis;

import java.util.ArrayList;
import java.util.List;

public class Diagnosis {
    private final String organization;

    private final String clincian;

    private String createdAt;

    private final List<Allergy> allergies = new ArrayList<Allergy>();

    private final List<String> symptons = new ArrayList<>();

    private final List<Medication> medications = new ArrayList<>();

    public Diagnosis(String organization, String clincian, String createdAt) {
        super();
        this.organization = organization;
        this.clincian = clincian;
        this.createdAt = createdAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getOrganization() {
        return organization;
    }

    public String getClincian() {
        return clincian;
    }

    public List<Allergy> getAllergies() {
        return allergies;
    }

    public List<String> getSymptons() {
        return symptons;
    }

    public List<Medication> getMedications() {
        return medications;
    }

   
}
