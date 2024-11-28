package org.becode.projects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.becode.projects.domain.Company;
import org.becode.projects.domain.User;
import org.becode.projects.repositories.CompanyRepository;
import org.becode.projects.repositories.UserRepository;
import org.becode.projects.services.CompanyService;
import org.becode.projects.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class UserServiceTest {

	@Mock
	private UserRepository userRepository;
	
	@InjectMocks
	private UserService userService;
	
	private User user;
	
	@BeforeEach
	public void setUp() throws Exception {
		user = new User();
		user.setId(1);
		user.setUsername("robin");
		user.setPassword("12345");
		user.setRole("admin");
		
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void getAllUsers() {
        List<User> users = Arrays.asList(user);
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("robin", result.get(0).getUsername());
        assertEquals("12345", result.get(0).getPassword());
        assertEquals("admin", result.get(0).getRole());
	}
	
	@Test
	void getSpecificUser() {
		when(userRepository.getById(1l)).thenReturn(user);
		
		User result = userService.getSpecificUser(1);
		
		 assertNotNull(result);
	     assertEquals("robin", result.getUsername());
	     assertEquals("12345", result.getPassword());
	     assertEquals("admin", result.getRole());
	}
	
	@Test
	void createNewUser() {
        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        String result = userService.createNewUser(user);

        assertEquals("New user successfully created", result);
        verify(userRepository, times(1)).save(user);
	}
	
	@Test
	void deleteUser() {
		int userId = 1;
		doNothing().when(userRepository).deleteById((long) userId);

        String result = userService.deleteUser(userId);

        assertEquals("User with id 1 successfully removed", result);
        verify(userRepository, times(1)).deleteById((long) userId);
	}
	
	@Test 
	void updateUser(){
		when(userRepository.save(user)).thenReturn(user);

        String result = userService.updateUser(user);

        assertEquals("User successfully updated", result);
        verify(userRepository, times(1)).save(user);
	}

}
