package uit.herec.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uit.herec.common.dto.AppointmentDetailDto;
import uit.herec.dao.entity.Appointment;
import uit.herec.dao.repository.AppointmentRepository;
import uit.herec.service.IAppointmentService;
import uit.herec.service.IUserService;
import uit.herec.service.mapper.AppointmentMapper;

@Service
public class AppointmentServiceImpl implements IAppointmentService{

    @Autowired
    private uit.herec.hyperledger.Service hyperledgerService;
    
    @Autowired
    private IUserService userService;
    
    @Autowired
    private AppointmentRepository appointmentRepository;
    
    @Autowired
    private AppointmentMapper appointmentMapper;
    
    @Override
    public AppointmentDetailDto getAppointmentByKey(String hyperledgerName, String key) {
        AppointmentDetailDto dto = this.hyperledgerService.queryAppointmentCmd(hyperledgerName, "Client", "herecchannel", "diagnosis", key);
        if(dto == null) {
            Appointment appointment = this.appointmentRepository.findByKey(key)
                    .orElse(null);
            String phoneNumber = this.userService.getPhoneNumber(hyperledgerName);
            return this.appointmentMapper.mapEntityToDto(appointment, phoneNumber);
        }
        return dto;
    }

    @Override
    public boolean addNewAppointment(Appointment entity) {
        return this.appointmentRepository.saveAndFlush(entity) != null;
    }

    @Override
    public int countAppointment() {
        return (int) this.appointmentRepository.count();
    }

}
