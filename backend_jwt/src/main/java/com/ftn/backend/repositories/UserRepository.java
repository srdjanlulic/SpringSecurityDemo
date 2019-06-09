package com.ftn.backend.repositories;

import org.springframework.data.repository.CrudRepository;

import com.ftn.backend.model.User;

public interface UserRepository extends CrudRepository<User, Integer>{

	User findByUsername(String username);
}
