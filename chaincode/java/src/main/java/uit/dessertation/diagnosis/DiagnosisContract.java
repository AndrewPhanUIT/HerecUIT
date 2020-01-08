package uit.dessertation.diagnosis;

import java.util.ArrayList;
import java.util.Arrays;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.owlike.genson.Genson;

@Contract(name = "Diagnosis", info = @Info(title = "Diagnosis contract", description = "The hyperlegendary Diagnosis contract", version = "0.0.1", license = @License(name = "Apache 2.0 License", url = "http://www.apache.org/licenses/LICENSE-2.0.html")))
@Default
public class DiagnosisContract implements ContractInterface {
    private Genson genson = new Genson();
    private Logger logger = LoggerFactory.getLogger(DiagnosisContract.class);

    @Transaction()
    public void initLedger(Context ctx) {
        ChaincodeStub stub = ctx.getStub();
        List<Medication> medications = new ArrayList<Medication>();
        medications.add(new Medication(6, "2 lần / ngày", "Thuốc ho cho bé", "Uống vào buổi sáng và tối", "20200101",
                "20200103"));
        List<Allergy> allergies = new ArrayList<Allergy>();
        allergies.add(new Allergy("Paracetamol", "Nhẹ", "Ho"));
        List<String> symptons = new ArrayList<String>();
        symptons.add("Ho");
        symptons.add("Sốt cao");
        Diagnosis diagnosis = new Diagnosis("ORG001", "Bệnh viện quận 12", "20200201", "Vũ Mạnh Cường @DT001",
                allergies, symptons, medications);
        List<Diagnosis> lstDiagnosis = Arrays.asList(new Diagnosis[] {diagnosis});
        DiagnosisDetail detail = new DiagnosisDetail("DIAG001", "Andrew Phan", "19971226", "0783550324",
                "Phường Tân Thới Nhất, Quận 12, Thành phố Hồ Chí Minh", new Gson().toJson(lstDiagnosis));
        String diagnosisState = genson.serialize(detail);
        stub.putStringState(detail.getDiagnosisNumber(), diagnosisState);
        logger.info("Added " + detail);
    }

    @Transaction()
    public DiagnosisDetail queryDiagnosis(Context ctx, String key) {
        ChaincodeStub stub = ctx.getStub();
        String diagnosisState = stub.getStringState(key);
        if (diagnosisState.isEmpty()) {
            String errorMess = String.format("Diagnosis %s does not exist", key);
            logger.info("[ERRO] - " + errorMess);
            throw new ChaincodeException(errorMess);
        }
        DiagnosisDetail detail = genson.deserialize(diagnosisState, DiagnosisDetail.class);
        return detail;
    }
}
