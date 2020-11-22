package guru.sfg.brewery.repositories.security;

import guru.sfg.brewery.domain.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by Jeff Stark on 11/5/2020
 */


public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String customer);
}
