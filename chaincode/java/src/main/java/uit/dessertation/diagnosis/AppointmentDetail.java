package uit.dessertation.diagnosis;

import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

@DataType()
public class AppointmentDetail {

    @Property()
    private final String key;
    @Property()
    private final String phoneNumber;
    @Property()
    private final Appointment appointment;

    public AppointmentDetail(String key, String phoneNumber, Appointment appointment) {
        super();
        this.key = key;
        this.phoneNumber = phoneNumber;
        this.appointment = appointment;
    }

    public String getKey() {
        return key;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Appointment getAppointment() {
        return appointment;
    }

}
