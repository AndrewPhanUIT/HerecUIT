package uit.herec.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uit.herec.config.security.UserPrincipal;
import uit.herec.dao.entity.AppUser;
import uit.herec.dao.repository.AppUserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private AppUserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
		AppUser appUser = this.userRepository.findByPhoneNumber(phoneNumber)
				.orElseThrow(() -> new UsernameNotFoundException(String.format(uit.herec.common.message.Error.PHONE_NUMBER_NOT_FOUND, phoneNumber)));
		return UserPrincipal.create(appUser);
	}
	
	@Transactional
	public UserDetails loadUserByPhoneNumber(String phoneNumber) throws UsernameNotFoundException {
        AppUser appUser = this.userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(uit.herec.common.message.Error.PHONE_NUMBER_NOT_FOUND, phoneNumber)));
        return UserPrincipal.create(appUser);
    }
	
}
