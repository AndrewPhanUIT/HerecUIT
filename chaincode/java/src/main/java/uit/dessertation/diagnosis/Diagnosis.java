package uit.dessertation.diagnosis;

import java.util.ArrayList;
import java.util.List;

import com.owlike.genson.annotation.JsonProperty;

public class Diagnosis {
    private final String id;

    private final String idOrg;
    
    private final String organization;

    private final String clincian;

    private String createdAt;

    private final List<Allergy> allergies = new ArrayList<Allergy>();

    private final List<String> symptons = new ArrayList<>();

    private final List<Medication> medications = new ArrayList<>();

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
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
    
    public String getIdOrg() {
        return this.idOrg;
    }

    public Diagnosis(@JsonProperty("id") String id, @JsonProperty("idOrg") String idOrg, @JsonProperty("organization") String organization,
            @JsonProperty("clinician") String clincian, @JsonProperty("createdAt") String createdAt) {
        super();
        this.id = id;
        this.idOrg = idOrg;
        this.organization = organization;
        this.clincian = clincian;
        this.createdAt = createdAt;
    }
}
