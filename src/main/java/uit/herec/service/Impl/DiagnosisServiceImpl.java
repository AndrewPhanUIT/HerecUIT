package uit.herec.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uit.herec.common.dto.DiagnosisDetailDto;
import uit.herec.dao.entity.Diagnosis;
import uit.herec.dao.repository.DiagnosisRepository;
import uit.herec.service.IDiagnosisService;
import uit.herec.service.IUserService;
import uit.herec.service.mapper.DiagnosisMapper;

@Service
public class DiagnosisServiceImpl implements IDiagnosisService{

    @Autowired
    private uit.herec.hyperledger.Service hyperledgerService;
    
    @Autowired
    private IUserService userService;
    
    @Autowired
    private DiagnosisRepository repository;
    
    @Autowired
    private DiagnosisMapper diagnosisMapper;
    
    @Override
    public DiagnosisDetailDto getDiagnosisDetailByKey(String hyperledgerName, String key) {
        DiagnosisDetailDto dto = this.hyperledgerService.queryDiagnosis(hyperledgerName, "Client", "herecchannel", "diagnosis", key);
        if(dto == null) {
            Diagnosis diagnosis = this.repository.findByKey(key)
                    .orElse(null);
            String phoneNumber = this.userService.getPhoneNumber(hyperledgerName);
            dto = this.diagnosisMapper.mapDiagnosisToDiagnosisDetail(diagnosis, phoneNumber);
        }
        return dto;
    }

}
