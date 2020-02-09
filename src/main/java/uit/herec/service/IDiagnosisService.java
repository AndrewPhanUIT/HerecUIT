package uit.herec.service;

import uit.herec.common.dto.DiagnosisDetailDto;

public interface IDiagnosisService {
    DiagnosisDetailDto getDiagnosisDetailByKey(String hyperledgerName, String key);
}
