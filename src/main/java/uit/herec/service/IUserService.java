package uit.herec.service;

import uit.herec.common.dto.ApiResponseDto;
import uit.herec.common.form.RegisterForm;

public interface IUserService {
    ApiResponseDto registerPatient(RegisterForm form);
    String getHyperledgerName(String phoneNumber);

}
