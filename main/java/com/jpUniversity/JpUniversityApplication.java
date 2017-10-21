package com.jpUniversity;

import com.jpUniversity.domain.User;
import com.jpUniversity.domain.security.Role;
import com.jpUniversity.domain.security.UserRole;
import com.jpUniversity.service.UserService;
import com.jpUniversity.utility.SecurityUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class JpUniversityApplication implements CommandLineRunner {

	@Autowired
	private UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(JpUniversityApplication.class, args);
	}

	/*add new user every time the application runs*/
	@Override
	public void run(String... strings) throws Exception {

		/*add default user when program first runs*/
		User user1 = new User();
		user1.setFirstName("John");
		user1.setLastName("Adams");
		user1.setUsername("j");
		user1.setPassword(SecurityUtility.passwordEncoder().encode("p"));
		user1.setEmail("JAdams@gmail.com");
		Set<UserRole> userRoles = new HashSet<>();
		Role role1 = new Role();
		role1.setRoleId(1);
		role1.setName("ROLE_USER");
		userRoles.add(new UserRole(user1, role1));

		userService.createUser(user1, userRoles);
	}
}
