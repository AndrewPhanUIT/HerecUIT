package uit.dessertation.diagnosis;

import java.util.List;

import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.owlike.genson.annotation.JsonProperty;

@DataType()
public final class DiagnosisDetail {

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
    
    public static DiagnosisDetail create(DiagnosisDetail detail, String diagnosis) {
        return new DiagnosisDetail(detail.getPatientId(), detail.getFullName(), detail.getDob(), detail.getPhoneNumber(), detail.getAddress(), diagnosis);
    }

    public static DiagnosisDetail addDiagnosis(DiagnosisDetail detail, String diagnosis) {
        java.lang.reflect.Type type = new TypeToken<List<Diagnosis[]>>() {}.getType();
        List<Diagnosis> lstDiagnosis = gson.fromJson(detail.getDiagnosis(), type);
        Diagnosis diagnosis2 = gson.fromJson(diagnosis, Diagnosis.class);
        lstDiagnosis.add(diagnosis2);
        return new DiagnosisDetail(detail.getPatientId(), detail.getFullName(), detail.getDob(), detail.getPhoneNumber(), detail.getAddress(), gson.toJson(lstDiagnosis));
    }
}
