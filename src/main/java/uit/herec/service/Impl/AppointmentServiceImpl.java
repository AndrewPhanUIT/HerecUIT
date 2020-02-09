package uit.herec.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uit.herec.common.dto.AppointmentDetailDto;
import uit.herec.dao.entity.Appointment;
import uit.herec.dao.repository.AppointmentRepository;
import uit.herec.service.IAppointmentService;

@Service
public class AppointmentServiceImpl implements IAppointmentService{

    @Autowired
    private uit.herec.hyperledger.Service hyperledgerService;
    
    @Autowired
    private AppointmentRepository appointmentRepository;
    
    @Override
    public AppointmentDetailDto getAppointmentByKey(String hyperledgerName, String key) {
        AppointmentDetailDto dto = this.hyperledgerService.queryAppointment(hyperledgerName, "Client", "herecchannel", "diagnosis", key);
        if(dto == null) {
            Appointment appointment = this.appointmentRepository.findByKey(key)
                    .orElse(null);
            
        }
        return dto;
    }

}
