package uit.herec.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import uit.herec.common.Constant;
import uit.herec.common.RoleName;
import uit.herec.common.exception.BadRequestException;
import uit.herec.common.form.RegisterForm;
import uit.herec.common.message.Error;
import uit.herec.dao.entity.AppRole;
import uit.herec.dao.entity.AppUser;
import uit.herec.dao.repository.AppRoleRepository;

@Component
public class UserMapper {
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private AppRoleRepository roleRepository;

    public AppUser mapFromRegisterForm(RegisterForm form) {
        if (form == null) return null;
        AppUser user = new AppUser();
        user.setFullname(form.getFullname());
        user.setPhoneNumber(form.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        user.setPeerName("peer" + Constant.INDEX_CLIENT_PEER);
        user.setPort(Constant.CLIENT_PEER_PORTS[Constant.INDEX_CLIENT_PEER]);
        Constant.INDEX_CLIENT_PEER++;
        AppRole role = this.roleRepository.findByRole(RoleName.PATIENT)
                .orElseThrow(() -> new BadRequestException(String.format(Error.ROLE_NOT_FOUND, RoleName.PATIENT)));
        user.setAppRole(role);
        user.setHyperledgerName("user" + form.getPhoneNumber());
        return user;
    }
}
