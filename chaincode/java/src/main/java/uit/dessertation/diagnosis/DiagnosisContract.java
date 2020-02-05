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
import org.hyperledger.fabric.shim.ledger.KeyValue;
import org.hyperledger.fabric.shim.ledger.QueryResultsIterator;

import com.google.common.base.Objects;
import com.google.gson.Gson;

@Contract(name = "Diagnosis", info = @Info(title = "Diagnosis contract", description = "The hypeledger Diagnosis contract", version = "0.0.1", license = @License(name = "Apache 2.0 License", url = "http://www.apache.org/licenses/LICENSE-2.0.html")))
@Default
public class DiagnosisContract implements ContractInterface {
    private final static Gson gson = new Gson();

    @Transaction()
    public void initLedger(Context ctx) {
        ChaincodeStub stub = ctx.getStub();
        List<Allergy> allergies = new ArrayList<Allergy>();
        allergies.add(new Allergy("Paracetamol", "Nhẹ", "Ho"));
        Diagnosis diagnosis = new Diagnosis("Bệnh viện quận 12", "Vũ Mạnh Cường @DT001", "20200201");
        diagnosis.getSymptons().add("Ho");
        diagnosis.getSymptons().add("Sot cao");
        diagnosis.getMedications().add(new Medication(6, "2 lần / ngày", "Thuốc ho cho bé", "Uống vào buổi sáng và tối",
                "20200101", "20200103"));
        diagnosis.getAllergies().add(new Allergy("Paracetamol", "Nhẹ", "Ho"));
        DiagnosisDetail detail = new DiagnosisDetail("D001", "0783550324", diagnosis);
        stub.putStringState(detail.getKey(), gson.toJson(detail));
        
        List<Allergy> allergies1 = new ArrayList<Allergy>();
        allergies1.add(new Allergy("Paracetamol", "Nhẹ", "Ho"));
        Diagnosis diagnosis1 = new Diagnosis("Bệnh viện quận Tân Phú", "Vũ Mạnh Cường @DT001", "20200201");
        diagnosis1.getSymptons().add("Ho");
        diagnosis1.getSymptons().add("Sot cao");
        diagnosis1.getMedications().add(new Medication(6, "2 lần / ngày", "Thuốc ho cho bé", "Uống vào buổi sáng và tối",
                "20200101", "20200103"));
        diagnosis1.getAllergies().add(new Allergy("Paracetamol", "Nhẹ", "Ho"));
        DiagnosisDetail detail1 = new DiagnosisDetail("D002", "0783550324", diagnosis1);
        stub.putStringState(detail1.getKey(), gson.toJson(detail1));
        
        Appointment appointment = new Appointment("Bệnh viện quận 12", "Vũ Mạnh Cường@DT001", "20200101", "20200120");
        AppointmentDetail appDetail = new AppointmentDetail("A001", "0783550324", appointment);
        stub.putStringState(appDetail.getKey(), gson.toJson(appDetail));
    }

    @Transaction()
    public DiagnosisDetail queryDiagnosis(Context ctx, String key) {
        ChaincodeStub stub = ctx.getStub();
        String diagnosisState = stub.getStringState(key);
        if (diagnosisState.isEmpty()) {
            String errorMess = String.format("Diagnosis %s does not exist", key);
            throw new ChaincodeException(errorMess);
        }
        DiagnosisDetail detail = gson.fromJson(diagnosisState, DiagnosisDetail.class);
        return detail;
    }


    /**
     * Them diagnosis vao patient hien tai
     * @param ctx
     * @param patientId
     * @param diagnosis
     * @return
     */
    @Transaction()
    public DiagnosisDetail addNewDiagnosisRecord(Context ctx, final String phoneNumber, final String diagnosisStr) {
        ChaincodeStub stub = ctx.getStub();
        Diagnosis diagnosis = gson.fromJson(diagnosisStr, Diagnosis.class);
        int index = this.countDiagnosisInChannel(ctx) + 1;
        DiagnosisDetail detail = new DiagnosisDetail(String.format("D%03d", index), phoneNumber, diagnosis);
        stub.putStringState(detail.getKey(), gson.toJson(detail));
        return detail;
    }
    
    @Transaction()
    public int countDiagnosisInChannel(Context ctx) {
        ChaincodeStub stub = ctx.getStub();
        final String startKey = "D001";
        final String endKey = "D999";
        QueryResultsIterator<KeyValue> results = stub.getStateByRange(startKey, endKey);
        int count = 0;
        for (@SuppressWarnings("unused") KeyValue result : results) {
           count++;
        }
        return count;
    }
    
    @Transaction()
    public String queryAllDiagnosisByPhoneNumber(Context ctx, String phoneNumber) {
        List<DiagnosisDetail> details = new ArrayList<>();
        ChaincodeStub stub = ctx.getStub();
        final String startKey = "D001";
        final String endKey = "D999";
        QueryResultsIterator<KeyValue> results = stub.getStateByRange(startKey, endKey);
        for (KeyValue result : results) {
            DiagnosisDetail detail = gson.fromJson(result.getStringValue(), DiagnosisDetail.class);
            if (Objects.equal(phoneNumber, detail.getPhoneNumber())) {
                details.add(detail);
            }
        }
        return gson.toJson(details);
    }
    
    @Transaction()
    public AppointmentDetail queryAppointment(Context ctx, String key) {
        ChaincodeStub stub = ctx.getStub();
        String state = stub.getStringState(key);
        if (state.isEmpty()) {
            String errorMess = String.format("Appointment %s does not exist", key);
            throw new ChaincodeException(errorMess);
        }
        AppointmentDetail detail = gson.fromJson(state, AppointmentDetail.class);
        return detail;
    }
    
    @Transaction()
    public int countAppointmentInChannel(Context ctx) {
        ChaincodeStub stub = ctx.getStub();
        final String startKey = "A001";
        final String endKey = "A999";
        QueryResultsIterator<KeyValue> results = stub.getStateByRange(startKey, endKey);
        int count = 0;
        for (@SuppressWarnings("unused") KeyValue result : results) {
           count++;
        }
        return count;
    }
    
    @Transaction()
    public AppointmentDetail addNewAppointmentRecord(Context ctx, final String phoneNumber, final String appointmentStr) {
        ChaincodeStub stub = ctx.getStub();
        Appointment appointment = gson.fromJson(appointmentStr, Appointment.class);
        int index = this.countAppointmentInChannel(ctx) + 1;
        AppointmentDetail detail = new AppointmentDetail(String.format("A%03d", index), phoneNumber, appointment);
        stub.putStringState(detail.getKey(), gson.toJson(detail));
        return detail;
    }
    
    @Transaction()
    public String queryAllAppointmentByPhoneNumber(Context ctx, String phoneNumber) {
        List<AppointmentDetail> details = new ArrayList<>();
        ChaincodeStub stub = ctx.getStub();
        final String startKey = "A001";
        final String endKey = "A999";
        QueryResultsIterator<KeyValue> results = stub.getStateByRange(startKey, endKey);
        for (KeyValue result : results) {
            AppointmentDetail detail = gson.fromJson(result.getStringValue(), AppointmentDetail.class);
            if (Objects.equal(phoneNumber, detail.getPhoneNumber())) {
                details.add(detail);
            }
        }
        return gson.toJson(details);
    }
}
