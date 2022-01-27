package com.github.mangelt.lab.dto;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FieldErrorDTO implements Serializable {
	private static final long serialVersionUID = -5173081991581412220L;
	String fieldName;
	String fieldMessage;
}
