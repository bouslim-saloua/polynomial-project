package ma.project.polynome.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ma.project.polynome.entity.Role;
import ma.project.polynome.repository.RoleRepository;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

	@Autowired
	private RoleRepository roleRepository; 
	
	@PostMapping("/")
	private ResponseEntity<?> save(@RequestBody Role role) {
		Role savedRole = roleRepository.save(role);
		return new ResponseEntity<>(savedRole, HttpStatus.CREATED);
	}
}
