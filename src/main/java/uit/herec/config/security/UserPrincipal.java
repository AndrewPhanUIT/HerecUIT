package uit.herec.config.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import uit.herec.dao.entity.AppUser;

public class UserPrincipal implements UserDetails{

	
	private static final long serialVersionUID = 932965615799455879L;
	
	@JsonIgnore
	private AppUser appUser;

	private Collection<? extends GrantedAuthority> auth;
	
	public UserPrincipal(AppUser appUser, Collection<? extends GrantedAuthority> auth) {
		super();
		this.appUser = appUser;
		this.auth = auth;
	}
	
	public static UserPrincipal create(AppUser user) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(user.getAppRole().getRole()));
		return new UserPrincipal(user, authorities);
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.auth;
	}

	@Override
	public String getPassword() {
		return this.appUser.getPassword();
	}

	@Override
	public String getUsername() {
		return this.appUser.getPhoneNumber();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public AppUser getAppUser() {
		return appUser;
	}

	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
	}
	

}
