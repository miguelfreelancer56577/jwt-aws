package com.github.mangelt.lab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.mangelt.lab.dto.ReponseBodyDTO;
import com.github.mangelt.lab.dto.TokenDTO;
import com.github.mangelt.lab.dto.UserDTO;
import com.github.mangelt.lab.service.AuthenticatorService;
import com.github.mangelt.lab.util.ApiConstants;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = ApiConstants.API_VERSION + ApiConstants.MAPPING_AUTHENTICATION)
class AuthController {
	@Autowired
	AuthenticatorService authenticatorService;
	
	@PostMapping(path = ApiConstants.MAPPING_GENERATE)
	public ResponseEntity<ReponseBodyDTO<TokenDTO>> generateToken(@RequestBody UserDTO userDTO){
		return authenticatorService.generateToken(userDTO);
	} 
}
