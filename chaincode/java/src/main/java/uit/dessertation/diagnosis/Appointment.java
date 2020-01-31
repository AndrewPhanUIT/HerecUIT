package uit.dessertation.diagnosis;

import com.owlike.genson.annotation.JsonProperty;

public class Appointment {
    private final String id; 

    private final String idOrg;

    private final String organization;

    private final String clincian;

    private final String createdAt;

    private final String appointmentDate;

    public Appointment(@JsonProperty("id") String id, @JsonProperty("idOrg") String idOrg,
            @JsonProperty("organization") String organization, @JsonProperty("clincian") String clincian,
            @JsonProperty("createdAt") String createdAt, @JsonProperty("appointmentDate") String appointmentDate) {
        super();
        this.id = id;
        this.idOrg = idOrg;
        this.organization = organization;
        this.clincian = clincian;
        this.createdAt = createdAt;
        this.appointmentDate = appointmentDate;
    }

    public String getId() {
        return id;
    }

    public String getIdOrg() {
        return idOrg;
    }

    public String getOrganization() {
        return organization;
    }

    public String getClincian() {
        return clincian;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

}
