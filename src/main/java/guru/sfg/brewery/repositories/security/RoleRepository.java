package guru.sfg.brewery.repositories.security;

import guru.sfg.brewery.domain.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Jeff Stark on 11/5/2020
 */


public interface RoleRepository extends JpaRepository<Role, Long> {
}
