package uit.herec.common.dto;

import java.io.Serializable;

public class AppointmentDetailDto implements Serializable {
    private String patientId;
    private String fullName;
    private String dob;
    private String phoneNumber;
    private String address;
    private String appointments;
    private AppointmentDto[] appointmentDtos;

    public AppointmentDetailDto(String patientId, String fullName, String dob, String phoneNumber, String address,
            String appointments, AppointmentDto[] appointmentDtos) {
        super();
        this.patientId = patientId;
        this.fullName = fullName;
        this.dob = dob;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.appointments = appointments;
        this.appointmentDtos = appointmentDtos;
    }

    public AppointmentDetailDto() {
        super();
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAppointments() {
        return appointments;
    }

    public void setAppointments(String appointments) {
        this.appointments = appointments;
    }

    public AppointmentDto[] getAppointmentDtos() {
        return appointmentDtos;
    }

    public void setAppointmentDtos(AppointmentDto[] appointmentDtos) {
        this.appointmentDtos = appointmentDtos;
    }

}
