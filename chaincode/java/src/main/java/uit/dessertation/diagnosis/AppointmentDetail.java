package uit.dessertation.diagnosis;

import java.util.List;

import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.owlike.genson.annotation.JsonProperty;

@DataType()
public class AppointmentDetail {
    
    private final static Gson gson = new Gson();
    
    @Property()
    private final String patientId;
    @Property()
    private final String fullName;
    @Property()
    private final String dob;
    @Property()
    private final String phoneNumber;
    @Property()
    private final String address;
    @Property()
    private final String appointments;

    public AppointmentDetail(@JsonProperty("patientId") String patientId, @JsonProperty("fullName") String fullName,
            @JsonProperty("dob") String dob, @JsonProperty("phoneNumber") String phoneNumber,
            @JsonProperty("address") String address, @JsonProperty("appointments") String appointments) {
        super();
        this.patientId = patientId;
        this.fullName = fullName;
        this.dob = dob;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.appointments = appointments;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getDob() {
        return dob;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getAppointments() {
        return appointments;
    }
    
    public static AppointmentDetail create(AppointmentDetail detail, String appointments) {
        return new AppointmentDetail(detail.getPatientId(), detail.getFullName(), detail.getDob(), detail.getPhoneNumber(), detail.getAddress(), appointments);
    }

    public static AppointmentDetail addAppointment(AppointmentDetail detail, String appointment) {
        java.lang.reflect.Type type = new TypeToken<List<Appointment[]>>() {}.getType();
        List<Appointment> lsAppointments = gson.fromJson(detail.getAppointments(), type);
        Appointment appointment2 = gson.fromJson(appointment, Appointment.class);
        lsAppointments.add(appointment2);
        return new AppointmentDetail(detail.getPatientId(), detail.getFullName(), detail.getDob(), detail.getPhoneNumber(), detail.getAddress(), gson.toJson(lsAppointments));
    }
}
