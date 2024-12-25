package ma.project.polynome.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ma.project.polynome.entity.ERole;
import ma.project.polynome.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
	Role findByName(ERole name);
}
