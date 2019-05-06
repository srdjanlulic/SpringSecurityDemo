package com.ftn.backend.services;

import com.ftn.backend.model.User;

public interface UserService extends CRUDService<User>{

	User findByUsername(String username);
}
