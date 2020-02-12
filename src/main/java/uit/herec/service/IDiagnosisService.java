package uit.herec.service;

import uit.herec.common.dto.DiagnosisDetailDto;
import uit.herec.dao.entity.Diagnosis;

public interface IDiagnosisService {
    DiagnosisDetailDto getDiagnosisDetailByKey(String hyperledgerName, String key);
    boolean addNewDiagnosis(Diagnosis entity);
    int count();
}
