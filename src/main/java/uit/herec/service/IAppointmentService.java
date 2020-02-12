package uit.herec.service;

import uit.herec.common.dto.AppointmentDetailDto;
import uit.herec.dao.entity.Appointment;

public interface IAppointmentService {
    AppointmentDetailDto getAppointmentByKey(String hyperledgerName, String key);
    boolean addNewAppointment(Appointment entity);
    int countAppointment();
}
