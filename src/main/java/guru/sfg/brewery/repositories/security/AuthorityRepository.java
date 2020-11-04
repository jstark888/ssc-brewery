package guru.sfg.brewery.repositories.security;

import guru.sfg.brewery.domain.security.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Jeff Stark on 11/3/2020
 */


public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
