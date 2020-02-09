package uit.herec.service;

import uit.herec.common.dto.AppointmentDetailDto;

public interface IAppointmentService {
    AppointmentDetailDto getAppointmentByKey(String hyperledgerName, String key);
}
