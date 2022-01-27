package com.github.mangelt.lab.dto;

import javax.validation.constraints.NotBlank;

import com.github.mangelt.lab.util.ApiConstants;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {
	@NotBlank(message = ApiConstants.USER_SERVICE_USERID_MANDATORY)
	protected String userName;
	@NotBlank(message = ApiConstants.USER_SERVICE_PASSWORD_MANDATORY)
	protected String password;
}
