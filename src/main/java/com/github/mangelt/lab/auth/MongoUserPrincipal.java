package com.github.mangelt.lab.auth;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.github.mangelt.lab.entity.AuthGroup;
import com.github.mangelt.lab.entity.User;

import lombok.Builder;

@Builder
public class MongoUserPrincipal implements UserDetails{

	private static final long serialVersionUID = 4635155147015068223L;
	
	User user;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return user
				.getAuthGroups()
				.stream()
				.map(AuthGroup::getShortName)
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toSet());
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUserName();
	}

	@Override
	public boolean isAccountNonExpired() {
		return user.getIsAccountNonExpired();
	}

	@Override
	public boolean isAccountNonLocked() {
		return user.getIsAccountNonLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return user.getIsAccountNonExpired();
	}

	@Override
	public boolean isEnabled() {
		return user.getIsEnabled();
	}
	
}
