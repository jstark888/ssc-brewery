package guru.sfg.brewery.domain.security;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Jeff Stark on 11/3/2020
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String permission;

    /*
    @ManyToMany(mappedBy = "authorities")
    private Set<User> users;
    */

    @ManyToMany(mappedBy = "authorities")
    private Set<Role> roles;
}
