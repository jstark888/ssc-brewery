package guru.sfg.brewery.security.perms;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Jeff Stark on 11/5/2020
 */

@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAuthority('customer.read')")
public @interface CustomerReadPermission {
}
