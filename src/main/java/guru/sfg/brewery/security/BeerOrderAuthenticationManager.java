package guru.sfg.brewery.security;

import guru.sfg.brewery.domain.security.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Created by Jeff Stark on 11/21/2020
 */

@Slf4j
@Component
public class BeerOrderAuthenticationManager {

    public boolean customerIdMatches(Authentication authentication, UUID customerId) {
        User authenticatedUser = (User) authentication.getPrincipal();
        log.debug("Auth User Customer Id: {}, Customer Id: {}", authenticatedUser.getCustomer().getId(), customerId);
        return authenticatedUser.getCustomer().getId().equals(customerId);
    }

}
