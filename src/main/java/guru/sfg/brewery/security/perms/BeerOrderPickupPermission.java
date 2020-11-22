package guru.sfg.brewery.security.perms;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Jeff Stark on 11/22/2020
 */

@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAuthority('order.pickup') OR " +
        "hasAuthority('customer.order.pickup') " +
        "AND @beerOrderAuthenticationManager.customerIdMatches(authentication, #customerId)")
public @interface BeerOrderPickupPermission {
}
