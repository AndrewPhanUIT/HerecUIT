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

@Contract(name = "Diagnosis", info = @Info(title = "Diagnosis contract", description = "The hypeledger Diagnosis contract", version = "0.0.1", license = @License(name = "Apache 2.0 License", url = "http://www.apache.org/licenses/LICENSE-2.0.html")))
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
        
        Appointment appointment = new Appointment("A001", "ORG001", "Bệnh viện quận 12", "Vũ Mạnh Cường@DT001", "20200101", "20200120");
        Appointment[] lstAppointments = { appointment };
        AppointmentDetail appDetail = new AppointmentDetail("PT001", "Andrew Phan", "19971226", "0783550324",
                "Phường Tân Thới Nhất, Quận 12, Thành phố Hồ Chí Minh", gson.toJson(lstAppointments));
        String appointmentState = genson.serialize(appDetail);
        stub.putStringState("APM001", appointmentState);
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
    public DiagnosisDetail addNewDiagnosisRecord(Context ctx, final String patientInfo, final String diagnosis) {
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
            return newDiagnosisDetail;
        }
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
    
    @Transaction()
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
    public QueryResultsIterator<KeyModification> getStateHistoryByKey(Context ctx, String key) {
        ChaincodeStub stub = ctx.getStub();
        QueryResultsIterator<KeyModification> history = stub.getHistoryForKey(key);
        return history;
    }
    
    @Transaction()
    public DiagnosisDetail queryAllDiagnosisByPhoneNumber(Context ctx, String phoneNumber) {
        ChaincodeStub stub = ctx.getStub();
        final String startKey = "DIG001";
        final String endKey = "DIG999";
        QueryResultsIterator<KeyValue> results = stub.getStateByRange(startKey, endKey);
        for (KeyValue result : results) {
            DiagnosisDetail detail = genson.deserialize(result.getStringValue(), DiagnosisDetail.class);
            if (Objects.equal(phoneNumber, detail.getPhoneNumber())) {
                return detail;
            }
        }
        return null;
    }
    
    @Transaction()
    public AppointmentDetail queryAppointment(Context ctx, String key) {
        ChaincodeStub stub = ctx.getStub();
        String appointmentState = stub.getStringState(key);
        if (appointmentState.isEmpty()) {
            String errorMess = String.format("Appointment %s does not exist", key);
            throw new ChaincodeException(errorMess);
        }
        AppointmentDetail detail = genson.deserialize(appointmentState, AppointmentDetail.class);
        return detail;
    }
    
    @Transaction()
    public AppointmentDetail createAppointment(Context ctx, final String patientInfo, final String appointment) {
        ChaincodeStub stub = ctx.getStub();
        int currentIndex = this.countAppointmentInChannel(ctx) + 1;
        AppointmentDetail appointmentDetail = genson.deserialize(patientInfo, AppointmentDetail.class);
        String[] lstAppointments = {appointment};
        AppointmentDetail newAppointmentsDetail = AppointmentDetail.create(appointmentDetail, genson.serialize(lstAppointments));
        stub.putStringState(String.format("APM%30d", currentIndex), genson.serialize(newAppointmentsDetail));
        return newAppointmentsDetail;
    }
    
    @Transaction()
    public int countAppointmentInChannel(Context ctx) {
        ChaincodeStub stub = ctx.getStub();
        final String startKey = "APM001";
        final String endKey = "APM999";
        QueryResultsIterator<KeyValue> results = stub.getStateByRange(startKey, endKey);
        int count = 0;
        for (KeyValue result : results) {
           count++;
        }
        return count;
    }
    
    @Transaction()
    public AppointmentDetail addNewAppointmentRecord(Context ctx, final String patientInfo, final String appointment) {
        ChaincodeStub stub = ctx.getStub();
        AppointmentDetail detail = genson.deserialize(patientInfo, AppointmentDetail.class);
        String keyPhoneNumber = this.queryAppointmentByPatientPhoneNumber(ctx, detail.getPhoneNumber());
        if("".equals(keyPhoneNumber)) {
            return createAppointment(ctx, patientInfo, appointment);
        }else {
            AppointmentDetail appointmentDetail = genson.deserialize(patientInfo, AppointmentDetail.class);
            AppointmentDetail newAppointmentDetail = AppointmentDetail.addAppointment(appointmentDetail, appointment);
            stub.putStringState(keyPhoneNumber, genson.serialize(newAppointmentDetail));
            return newAppointmentDetail;
        }
    }
    
    @Transaction()
    public String queryAppointmentByPatientPhoneNumber(Context ctx, String phoneNumber) {
        ChaincodeStub stub = ctx.getStub();
        final String startKey = "APM001";
        final String endKey = "APM999";
        QueryResultsIterator<KeyValue> results = stub.getStateByRange(startKey, endKey);
        for (KeyValue result : results) {
            AppointmentDetail detail = genson.deserialize(result.getStringValue(), AppointmentDetail.class);
            if (Objects.equal(phoneNumber, detail.getPhoneNumber())) {
                return result.getKey();
            }
        }
        return "";
    }
    
    @Transaction()
    public AppointmentDetail queryAllAppointmentByPhoneNumber(Context ctx, String phoneNumber) {
        ChaincodeStub stub = ctx.getStub();
        final String startKey = "APM001";
        final String endKey = "APM999";
        QueryResultsIterator<KeyValue> results = stub.getStateByRange(startKey, endKey);
        for (KeyValue result : results) {
            AppointmentDetail detail = genson.deserialize(result.getStringValue(), AppointmentDetail.class);
            if (Objects.equal(phoneNumber, detail.getPhoneNumber())) {
                return detail;
            }
        }
        return null;
    }
}
