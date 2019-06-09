package com.ftn.backend.services;

import java.util.List;

import com.ftn.backend.model.User;

/**
 * CRUD interfejs za upravljanje <code>User</code> entitetima.
 * @author Srdjan Lulic
 *
 */
public interface UserService extends CRUDService<User>{

	User findByUsername(String username);

}
