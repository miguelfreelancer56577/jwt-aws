package com.github.mangelt.lab.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.github.mangelt.lab.entity.AuthGroup;
import com.github.mangelt.lab.entity.User;

@Repository
public interface AuthGroupRepository extends MongoRepository<AuthGroup, String>{}
