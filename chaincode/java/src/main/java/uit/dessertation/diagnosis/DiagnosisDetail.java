package uit.dessertation.diagnosis;

import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

import com.owlike.genson.annotation.JsonProperty;

@DataType()
public final class DiagnosisDetail {

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
    private final String diagnosis;

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

    public String getDiagnosis() {
        return diagnosis;
    }

    public DiagnosisDetail(@JsonProperty("patientId") String patientId, @JsonProperty("fullName") String fullName,
            @JsonProperty("dob") String dob, @JsonProperty("phoneNumber") String phoneNumber,
            @JsonProperty("address") String address, @JsonProperty("diagnosis") String diagnosis) {
        super();
        this.patientId = patientId;
        this.fullName = fullName;
        this.dob = dob;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.diagnosis = diagnosis;
    }

}
