package uit.herec.service.Impl;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uit.herec.common.dto.ApiResponseDto;
import uit.herec.common.exception.BadRequestException;
import uit.herec.common.form.RegisterForm;
import uit.herec.common.message.Error;
import uit.herec.common.message.Success;
import uit.herec.dao.entity.AppUser;
import uit.herec.dao.entity.Organization;
import uit.herec.dao.repository.AppUserRepository;
import uit.herec.service.IOrganizationService;
import uit.herec.service.IUserService;
import uit.herec.service.mapper.UserMapper;

@Service
public class UserServiceImpl implements IUserService{
    
    private final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    
    @Autowired
    private AppUserRepository userRepository;
    
    @Autowired
    private IOrganizationService orgService;
    
    @Autowired
    private UserMapper userMapper;
    
    @Override
    public ApiResponseDto registerPatient(RegisterForm form) {
        if(userRepository.existsByPhoneNumber(form.getPhoneNumber())) {
            String errMess = String.format(Error.PHONE_NUMBER_IS_USED, form.getPhoneNumber());
            logger.error(errMess);
            return new ApiResponseDto(false, Arrays.asList(""), errMess);
        }
        AppUser user = this.userMapper.mapFromRegisterForm(form);
        Organization org = this.orgService.getOrgByHyperledgerName("quan12");
        user.getOrganizations().add(org);
        this.userRepository.saveAndFlush(user);
        return new ApiResponseDto(true, Arrays.asList(""), String.format(Success.REGISTER_NEW_USER, form.getFullname()));
    }

    @Override
    public String getHyperledgerName(String phoneNumber) {
        AppUser user = this.userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(()-> new BadRequestException(String.format(Error.PHONE_NUMBER_NOT_FOUND, phoneNumber)));
        return user.getHyperledgerName();
    }

    @Override
    public String getPhoneNumber(String hyperledgerName) {
        AppUser user = this.userRepository.findByHyperledgerName(hyperledgerName)
                .orElseThrow(()-> new BadRequestException(String.format(Error.HYPERLEDGER_NAME_NOT_FOUND, hyperledgerName)));
        return user.getPhoneNumber();
    }

    @Override
    public AppUser getUserByPhoneNumber(String phoneNumber) {
        return this.userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(()-> new BadRequestException(String.format(Error.PHONE_NUMBER_NOT_FOUND, phoneNumber)));
    }

    @Override
    public boolean saveOrUpdate(AppUser appUser) {
        return this.userRepository.saveAndFlush(appUser) != null;
    }

    @Override
    public AppUser getUserByHyperledgerName(String hyperledgerName) {
        return this.userRepository.findByHyperledgerName(hyperledgerName)
                .orElseThrow(()-> new BadRequestException(String.format(Error.HYPERLEDGER_NAME_NOT_FOUND, hyperledgerName)));
    }

}
