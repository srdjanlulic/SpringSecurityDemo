package com.ftn.backend.services;

import java.util.List;

import com.ftn.backend.model.User;

public interface UserService extends CRUDService<User>{

	User findByUsername(String username);

}
