package uit.dessertation.diagnosis;

import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

@DataType()
public final class DiagnosisDetail {

    @Property
    private final String key;
    @Property
    private final String phoneNumber;
    @Property()
    private final Diagnosis diagnosis;

    public DiagnosisDetail(String key, String phoneNumber, Diagnosis diagnosis) {
        super();
        this.key = key;
        this.phoneNumber = phoneNumber;
        this.diagnosis = diagnosis;
    }

    public String getKey() {
        return key;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Diagnosis getDiagnosis() {
        return diagnosis;
    }

}
