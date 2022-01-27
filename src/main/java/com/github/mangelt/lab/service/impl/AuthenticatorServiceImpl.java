package com.github.mangelt.lab.service.impl;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.github.mangelt.lab.component.JwtComponent;
import com.github.mangelt.lab.dto.ReponseBodyDTO;
import com.github.mangelt.lab.dto.TokenDTO;
import com.github.mangelt.lab.dto.UserDTO;
import com.github.mangelt.lab.exception.AppException;
import com.github.mangelt.lab.service.AuthenticatorService;
import com.github.mangelt.lab.util.ApiConstants;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthenticatorServiceImpl implements AuthenticatorService {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	JwtComponent jwtComponent;
	
	@Override
	public ResponseEntity<ReponseBodyDTO<TokenDTO>> generateToken(@Valid UserDTO payload) throws DisabledException, BadCredentialsException {
		final ReponseBodyDTO<TokenDTO> response = new ReponseBodyDTO<>(HttpStatus.OK.value(), ApiConstants.USER_SERVICE_SIGNIN_OK);
		final UserDetails userDetails;
		final UsernamePasswordAuthenticationToken usernamePasswordCredentials = new UsernamePasswordAuthenticationToken(payload.getUserName(), payload.getPassword());
		authenticationManager.authenticate(usernamePasswordCredentials);
		userDetails = userDetailsService.loadUserByUsername(payload.getUserName());
		response.setContent(TokenDTO.builder().token(jwtComponent.createToken(userDetails)).build());
		return ResponseEntity.ok(response);
	}

}
