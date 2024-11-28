package org.becode.projects.cui;

import org.becode.projects.controllers.UserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class UserCommands {

	@Autowired
	private UserController userController;
	
	public UserCommands() {
		
	}
	
	@ShellMethod(key = "hello", value="gives you a hello")
	public String hello() {
		return userController.test();
	}
	
}
