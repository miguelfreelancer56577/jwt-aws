package com.github.mangelt.lab.service;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import com.github.mangelt.lab.dto.ReponseBodyDTO;
import com.github.mangelt.lab.dto.UserDTO;
import com.github.mangelt.lab.dto.TokenDTO;

@Validated
public interface AuthenticatorService {
	ResponseEntity<ReponseBodyDTO<TokenDTO>> generateToken(@Valid UserDTO payload);
}
