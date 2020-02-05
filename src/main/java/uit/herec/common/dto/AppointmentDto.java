package uit.herec.common.dto;

public class AppointmentDto {
    private String id;

    private String idOrg;

    private String organization;

    private String clincian;

    private String createdAt;

    private String appointmentDate;

    public AppointmentDto(String id, String idOrg, String organization, String clincian, String createdAt,
            String appointmentDate) {
        super();
        this.id = id;
        this.idOrg = idOrg;
        this.organization = organization;
        this.clincian = clincian;
        this.createdAt = createdAt;
        this.appointmentDate = appointmentDate;
    }

    public AppointmentDto() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdOrg() {
        return idOrg;
    }

    public void setIdOrg(String idOrg) {
        this.idOrg = idOrg;
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

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

}
