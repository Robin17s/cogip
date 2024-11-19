package org.becode.projects;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private UserRepository rep;
	
	public UserService() {
		
	}
	
	public List<User> getAllUsers(){
		return rep.findAll();
	}
}
