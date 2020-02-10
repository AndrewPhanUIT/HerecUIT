package uit.herec.service.mapper;

import java.text.SimpleDateFormat;

import org.springframework.stereotype.Component;

import uit.herec.common.dto.AppointmentDetailDto;
import uit.herec.common.dto.AppointmentDto;
import uit.herec.dao.entity.Appointment;

@Component
public class AppointmentMapper {
    public AppointmentDetailDto mapEntityToDto(Appointment entity, String phoneNumber) {
        if(entity == null) return null;
        AppointmentDetailDto dto = new AppointmentDetailDto();
        dto.setPhoneNumber(phoneNumber);
        dto.setKey(entity.getKey());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        AppointmentDto appointmentDto = new AppointmentDto(entity.getOrganization().getName(), entity.getClinician(), sdf.format(entity.getCreatedAt()), sdf1.format(entity.getAppointmentTime()));
        dto.setAppointment(appointmentDto);
        return dto;
    }
}
