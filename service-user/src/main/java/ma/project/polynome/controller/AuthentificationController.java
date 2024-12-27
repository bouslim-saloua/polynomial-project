package ma.project.polynome.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ma.project.polynome.entity.CustomUtilisateurDetails;
import ma.project.polynome.entity.ERole;
import ma.project.polynome.entity.Role;
import ma.project.polynome.entity.Utilisateur;
import ma.project.polynome.payload.request.LoginRequest;
import ma.project.polynome.payload.request.SignupRequest;
import ma.project.polynome.payload.request.UtilisateurGetDto;
import ma.project.polynome.payload.response.JwtResponse;
import ma.project.polynome.payload.response.MessageResponse;
import ma.project.polynome.repository.RoleRepository;
import ma.project.polynome.repository.UtilisateurRepository;
import ma.project.polynome.security.JwtUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
@RestController
@RequestMapping("/api/auth")
public class AuthentificationController {

	AuthenticationManager authenticationManager;
	UtilisateurRepository userRepository;

	RoleRepository roleRepository;
	PasswordEncoder encoder;
	JwtUtils jwtUtils;

	public AuthentificationController(AuthenticationManager authenticationManager,UtilisateurRepository userRepository,RoleRepository roleRepository,
			PasswordEncoder encoder,JwtUtils jwtUtils) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.encoder = encoder;
		this.jwtUtils = jwtUtils;
	}

	//signIn

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		CustomUtilisateurDetails userDetails = (CustomUtilisateurDetails) authentication.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());
		//String accessToken, Long id, String email, String nom, String prenom,String telephone ,List<String> roles
		// public JwtResponse(String accessToken, Long id, String email, String nom, String prenom,String telephone ,List<String> roles) {	
		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getEmail(),
				userDetails.getNom(), userDetails.getPrenom(),roles));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}

		Utilisateur user = new Utilisateur(signUpRequest.getNom(), 
				signUpRequest.getPrenom(), 
				signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()));

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER);
			if(userRole == null) 
				throw new RuntimeException("Error: Role is not found.");
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN);
					if(adminRole == null)
						throw new RuntimeException("Error: Role is not found.");
					roles.add(adminRole);
					break;

				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER);
					if(userRole == null)
						throw new RuntimeException("Error: Role is not found.");
					roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);

		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findUserById(@PathVariable long id){
		Utilisateur utilisateur = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found !!"));
		UtilisateurGetDto uGet = new UtilisateurGetDto(utilisateur.getId(),
				utilisateur.getNom(),
				utilisateur.getPrenom(),
				utilisateur.getEmail());
		return new ResponseEntity<>(uGet, HttpStatus.OK);}

	@GetMapping("/findByEmail")
    public ResponseEntity<Utilisateur> findByEmail(@RequestParam String email) {
        return userRepository.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}