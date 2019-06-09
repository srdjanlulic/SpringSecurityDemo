package com.ftn.backend.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.backend.model.User;
import com.ftn.backend.repositories.UserRepository;

/**
 * Implementacija CRUD servisnog interfejsa za manipulaciju <code>User</code> entitetima.
 * 
 * @author Srdjan Lulic
 */
@Service("userService")
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EncryptionService encryptionService;
	
	@Override
	public List<User> listAll() {
		List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add); //Java 8 Magija :)
        return users;
	}

	@Override
	public User getById(Integer id) {
		 Optional<User> user = userRepository.findById(id);
		 
		 if (user.isPresent()) {
			 return user.get();
		 } else {
			 return null;
		 }
	}

	@Override
	public User saveOrUpdate(User user) {
		
		 if(user.getPassword() != null){
	            user.setEncryptedPassword(encryptionService.encryptString(user.getPassword()));
	        }
	        return userRepository.save(user);
	}

	@Override
	public void delete(Integer id) {
		userRepository.deleteById(id);
	}

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

}
