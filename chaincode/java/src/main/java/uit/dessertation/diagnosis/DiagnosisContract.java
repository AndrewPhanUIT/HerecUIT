package uit.dessertation.diagnosis;

import java.util.ArrayList;
import java.util.List;

import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Default;
import org.hyperledger.fabric.contract.annotation.Info;
import org.hyperledger.fabric.contract.annotation.License;
import org.hyperledger.fabric.contract.annotation.Transaction;
import org.hyperledger.fabric.shim.ChaincodeException;
import org.hyperledger.fabric.shim.ChaincodeStub;
import org.hyperledger.fabric.shim.ledger.KeyModification;
import org.hyperledger.fabric.shim.ledger.KeyValue;
import org.hyperledger.fabric.shim.ledger.QueryResultsIterator;

import com.google.common.base.Objects;
import com.google.gson.Gson;
import com.owlike.genson.Genson;

@Contract(name = "Diagnosis", info = @Info(title = "Diagnosis contract", description = "The hyperlegendary Diagnosis contract", version = "0.0.1", license = @License(name = "Apache 2.0 License", url = "http://www.apache.org/licenses/LICENSE-2.0.html")))
@Default
public class DiagnosisContract implements ContractInterface {
    private Genson genson = new Genson();
    private final static Gson gson = new Gson();

    @Transaction()
    public void initLedger(Context ctx) {
        ChaincodeStub stub = ctx.getStub();
        List<Allergy> allergies = new ArrayList<Allergy>();
        allergies.add(new Allergy("Paracetamol", "Nhẹ", "Ho"));
        Diagnosis diagnosis = new Diagnosis("D001", "ORG001", "Bệnh viện quận 12", "20200201", "Vũ Mạnh Cường @DT001");
        diagnosis.getSymptons().add("Ho");
        diagnosis.getSymptons().add("Sot cao");
        diagnosis.getMedications().add(new Medication(6, "2 lần / ngày", "Thuốc ho cho bé", "Uống vào buổi sáng và tối",
                "20200101", "20200103"));
        diagnosis.getAllergies().add(new Allergy("Paracetamol", "Nhẹ", "Ho"));
        Diagnosis[] lstDiagnosis = { diagnosis };
        DiagnosisDetail detail = new DiagnosisDetail("PT001", "Andrew Phan", "19971226", "0783550324",
                "Phường Tân Thới Nhất, Quận 12, Thành phố Hồ Chí Minh", gson.toJson(lstDiagnosis));
        String diagnosisState = genson.serialize(detail);
        stub.putStringState("DIG001", diagnosisState);
    }

    @Transaction()
    public DiagnosisDetail queryDiagnosis(Context ctx, String key) {
        ChaincodeStub stub = ctx.getStub();
        String diagnosisState = stub.getStringState(key);
        if (diagnosisState.isEmpty()) {
            String errorMess = String.format("Diagnosis %s does not exist", key);
            throw new ChaincodeException(errorMess);
        }
        DiagnosisDetail detail = genson.deserialize(diagnosisState, DiagnosisDetail.class);
        return detail;
    }

    public static void main(String[] args) {
    }

    /**
     * Tao diagnosis cho patient moi
     * @param ctx
     * @param diagnosis
     * @return
     */
    @Transaction()
    public DiagnosisDetail createDiagnosis(Context ctx, final String patientInfo, final String diagnosis) {
        ChaincodeStub stub = ctx.getStub();
        int currentIndex = this.countDiagnosisInChannel(ctx) + 1;
        DiagnosisDetail diagnosisDetail = genson.deserialize(patientInfo, DiagnosisDetail.class);
        String[] lstDiagnosis = {diagnosis};
        DiagnosisDetail newDiagnosisDetail = DiagnosisDetail.create(diagnosisDetail, genson.serialize(lstDiagnosis));
        stub.putStringState(String.format("DIA%30d", currentIndex), genson.serialize(newDiagnosisDetail));
        return newDiagnosisDetail;
    }

    /**
     * Them diagnosis vao patient hien tai
     * @param ctx
     * @param patientId
     * @param diagnosis
     * @return
     */
    @Transaction()
    public DiagnosisDetail addNewRecord(Context ctx, final String patientInfo, final String diagnosis) {
        ChaincodeStub stub = ctx.getStub();
        DiagnosisDetail detail = genson.deserialize(patientInfo, DiagnosisDetail.class);
        String keyPatientId = queryDiagnosisByPatientId(ctx, detail.getPatientId());
        String keyPhoneNumber = this.queryDiagnosisByPatientPhoneNumber(ctx, detail.getPhoneNumber());
        if("".equals(keyPatientId) && "".equals(keyPhoneNumber)) {
            return createDiagnosis(ctx, patientInfo, diagnosis);
        }else {
            DiagnosisDetail diagnosisDetail = genson.deserialize(patientInfo, DiagnosisDetail.class);
            DiagnosisDetail newDiagnosisDetail = DiagnosisDetail.addDiagnosis(diagnosisDetail, diagnosis);
            String key = !"".equals(keyPatientId) ? keyPatientId : keyPhoneNumber;
            stub.putStringState(key, genson.serialize(newDiagnosisDetail));
        }
        return null;
    }

    @Transaction()
    public String queryDiagnosisByPatientId(Context ctx, String patientId) {
        ChaincodeStub stub = ctx.getStub();
        final String startKey = "DIG001";
        final String endKey = "DIG999";
        QueryResultsIterator<KeyValue> results = stub.getStateByRange(startKey, endKey);
        for (KeyValue result : results) {
            DiagnosisDetail detail = genson.deserialize(result.getStringValue(), DiagnosisDetail.class);
            if (Objects.equal(patientId, detail.getPatientId())) {
                return result.getKey();
            }
        }
        return "";
    }
    
    @Transaction()
    public String queryDiagnosisByPatientPhoneNumber(Context ctx, String phoneNumber) {
        ChaincodeStub stub = ctx.getStub();
        final String startKey = "DIG001";
        final String endKey = "DIG999";
        QueryResultsIterator<KeyValue> results = stub.getStateByRange(startKey, endKey);
        for (KeyValue result : results) {
            DiagnosisDetail detail = genson.deserialize(result.getStringValue(), DiagnosisDetail.class);
            if (Objects.equal(phoneNumber, detail.getPhoneNumber())) {
                return result.getKey();
            }
        }
        return "";
    }
    
    public int countDiagnosisInChannel(Context ctx) {
        ChaincodeStub stub = ctx.getStub();
        final String startKey = "DIG001";
        final String endKey = "DIG999";
        QueryResultsIterator<KeyValue> results = stub.getStateByRange(startKey, endKey);
        int count = 0;
        for (KeyValue result : results) {
           count++;
        }
        return count;
    }

    @Transaction()
    public List<String> getStateHistoryByKey(Context ctx, String key) {
        ChaincodeStub stub = ctx.getStub();
        List<String> results = new ArrayList<String>();
        QueryResultsIterator<KeyModification> history = stub.getHistoryForKey(key);
        return results;
    }
}
