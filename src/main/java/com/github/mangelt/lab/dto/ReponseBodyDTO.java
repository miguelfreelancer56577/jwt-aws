package com.github.mangelt.lab.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(Include.NON_NULL)
@Builder
@AllArgsConstructorpublic class ReponseBodyDTO<T> {
	Integer status;
	String message;
	T content;
	public ReponseBodyDTO(int status, String message) {
		this.status=status;
		this.message=message;
	}
}
