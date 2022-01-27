package com.github.mangelt.lab.entity;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Document("auth_group")
@Data
@Builder
public class AuthGroup implements Serializable{
	private static final long serialVersionUID = 6066695479206456139L;
	@Id
	private String shortName;
	private String longName;
	private String description;
}
