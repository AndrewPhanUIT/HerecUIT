package uit.herec.service;

import uit.herec.common.dto.ApiResponseDto;
import uit.herec.common.form.RegisterForm;
import uit.herec.dao.entity.AppUser;

public interface IUserService {
    ApiResponseDto registerPatient(RegisterForm form);
    String getHyperledgerName(String phoneNumber);
    String getPhoneNumber(String hyperledgerName);
    AppUser getUserByPhoneNumber(String phoneNumber);
    boolean saveOrUpdate(AppUser appUser);
}
