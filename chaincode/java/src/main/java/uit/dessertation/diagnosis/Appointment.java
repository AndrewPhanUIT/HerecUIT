package uit.dessertation.diagnosis;

public class Appointment {
    private final String organization;

    private final String clincian;

    private final String createdAt;

    private final String appointmentDate;

    public Appointment(String organization, String clincian, String createdAt, String appointmentDate) {
        super();
        this.organization = organization;
        this.clincian = clincian;
        this.createdAt = createdAt;
        this.appointmentDate = appointmentDate;
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
