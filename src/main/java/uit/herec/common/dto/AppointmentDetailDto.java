package uit.herec.common.dto;

import java.io.Serializable;

public class AppointmentDetailDto implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 5352862306675151611L;
    private String key;
    private String phoneNumber;
    private AppointmentDto appointment;

    public AppointmentDetailDto(String key, String phoneNumber, AppointmentDto appointment) {
        super();
        this.key = key;
        this.phoneNumber = phoneNumber;
        this.appointment = appointment;
    }

    public AppointmentDetailDto() {
        super();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public AppointmentDto getAppointment() {
        return appointment;
    }

    public void setAppointment(AppointmentDto appointment) {
        this.appointment = appointment;
    }

}
