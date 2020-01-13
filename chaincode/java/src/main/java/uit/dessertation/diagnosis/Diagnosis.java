package uit.dessertation.diagnosis;

import java.util.ArrayList;
import java.util.List;

import org.hyperledger.fabric.contract.annotation.Property;

import com.owlike.genson.annotation.JsonProperty;

import org.hyperledger.fabric.contract.annotation.DataType;

@DataType()
public class Diagnosis {
    @Property()
    private final String id;

    @Property()
    private final String organization;

    @Property()
    private final String clincian;

    @Property()
    private String createdAt;

    @Property()
    private final List<Allergy> allergies = new ArrayList<Allergy>();

    @Property()
    private final List<String> symptons = new ArrayList<>();

    @Property()
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

    public Diagnosis(@JsonProperty("id") String id, @JsonProperty("organization") String organization,
            @JsonProperty("clinician") String clincian, @JsonProperty("createdAt") String createdAt) {
        super();
        this.id = id;
        this.organization = organization;
        this.clincian = clincian;
        this.createdAt = createdAt;
    }
}
