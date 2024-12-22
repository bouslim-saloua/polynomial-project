package ma.project.polynome.security.web;

import ma.project.polynome.security.entities.AppRole;
import ma.project.polynome.security.entities.AppUser;
import ma.project.polynome.security.entities.RoleUserForm;
import ma.project.polynome.security.service.AccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AccountRestController {

    private AccountService accountService;
    public AccountRestController(AccountService accountService) {
        this.accountService = accountService;
    }
    @GetMapping(path = "/home")
    public String homePage() {
        return "home"; // Render a home page view
    }

    @GetMapping(path = "/users")
    public List<AppUser> appUsers(){
        return accountService.listUsers();
    }

    @PostMapping(path = "/users")
    public AppUser saveUser(@RequestBody AppUser user){

        return accountService.addNewUser(user);
    }

    @PostMapping(path = "/roles")
    public AppRole saveRole(@RequestBody AppRole role){

        return accountService.addNewRole(role);
    }

    @PostMapping(path = "/addRoleToUser")
    public void addRoleToUser(@RequestBody RoleUserForm roleUserForm){

        accountService.addRoleToUser(roleUserForm.getUsername(),roleUserForm.getRoleName());
    }

}
