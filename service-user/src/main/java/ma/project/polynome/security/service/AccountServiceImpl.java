package ma.project.polynome.security.service;

import ma.project.polynome.security.entities.AppRole;
import ma.project.polynome.security.entities.AppUser;
import ma.project.polynome.security.repository.AppRoleRepository;
import ma.project.polynome.security.repository.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AccountServiceImpl implements AccountService{


    private AppUserRepository appUserRepository;
    private AppRoleRepository appRoleRepository;
    private PasswordEncoder passwordEncoder;

    public AccountServiceImpl(AppUserRepository appUserRepository, AppRoleRepository appRoleRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.appRoleRepository = appRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public AppUser addNewUser(AppUser user) {
        String pw=user.getPassword();
        user.setPassword(passwordEncoder.encode(pw));
        return appUserRepository.save(user);
    }

    @Override
    public AppRole addNewRole(AppRole role) {
        return appRoleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        AppUser user = appUserRepository.findByUsername(username);
        AppRole role = appRoleRepository.findByRoleName(roleName);
        user.getAppRoles().add(role);
    }

    @Override
    public AppUser loadUserByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }

    @Override
    public List<AppUser> listUsers() {
        return appUserRepository.findAll();
    }
}
