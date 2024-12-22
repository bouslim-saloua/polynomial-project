package ma.project.polynome.security.service;

import ma.project.polynome.security.entities.AppRole;
import ma.project.polynome.security.entities.AppUser;

import java.util.List;

public interface AccountService {

    AppUser addNewUser(AppUser user) ;
    AppRole addNewRole(AppRole role) ;
    void addRoleToUser(String username, String roleName);
    AppUser loadUserByUsername(String username);
    List<AppUser> listUsers();
}
