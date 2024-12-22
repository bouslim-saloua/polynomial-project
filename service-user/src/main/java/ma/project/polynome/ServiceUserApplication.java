package ma.project.polynome;

import ma.project.polynome.security.entities.AppRole;
import ma.project.polynome.security.entities.AppUser;
import ma.project.polynome.security.service.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@EnableDiscoveryClient
@SpringBootApplication
public class ServiceUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceUserApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
	//@Bean
	CommandLineRunner start(AccountService accountService){
		return args -> {
			accountService.addNewRole(new AppRole(null,"user"));
			accountService.addNewRole(new AppRole(null,"admin"));

			accountService.addNewUser(new AppUser(null,"admin","123",new ArrayList<>()));
			accountService.addNewUser(new AppUser(null,"user1","123",new ArrayList<>()));
			accountService.addNewUser(new AppUser(null,"user2","123",new ArrayList<>()));
			accountService.addNewUser(new AppUser(null,"user3","123",new ArrayList<>()));

			accountService.addRoleToUser("admin","admin");
			accountService.addRoleToUser("user1","user");
			accountService.addRoleToUser("user2","user");
			accountService.addRoleToUser("user3","admin");
			accountService.addRoleToUser("user3","user");
		};
	}
}
