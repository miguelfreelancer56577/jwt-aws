package com.github.mangelt.lab.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.github.mangelt.lab.auth.MongoUserPrincipal;
import com.github.mangelt.lab.entity.User;
import com.github.mangelt.lab.repository.UserRepository;
import com.github.mangelt.lab.util.ApiConstants;

@Service
public class MongoUserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String username){
		final Optional<User> returnedUser = repo.findById(username);
		return MongoUserPrincipal
			.builder()
			.user(returnedUser.orElseThrow(()->new UsernameNotFoundException(ApiConstants.EXP_ERROR_NOT_FOUND_USER.concat(username))))
			.build();
	}

}
