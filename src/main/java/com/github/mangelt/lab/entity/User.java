package com.github.mangelt.lab.entity;

import java.io.Serializable;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Document("users")
@Builder
@Data
public class User implements Serializable{
	private static final long serialVersionUID = -5317923909869474448L;
	@Id
	private String userName;
    private String firstName;
    private String lastName;
    private String password;
    private String age;
    @DBRef
    private Set<AuthGroup> authGroups;
    private Boolean isAccountNonExpired;
    private Boolean isAccountNonLocked;
    private Boolean isCredentialsNonExpired;
    private Boolean isEnabled;
}
