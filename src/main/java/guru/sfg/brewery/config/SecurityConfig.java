package guru.sfg.brewery.config;

import guru.sfg.brewery.security.SfgPasswordEncoderFactories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.ldap.LdapServerBeanDefinitionParser;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * Created by Jeff Stark on 11/1/2020
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    PasswordEncoder passwordEncoder() {
        //return NoOpPasswordEncoder.getInstance();
        //return new LdapShaPasswordEncoder();
        //return new StandardPasswordEncoder();
        //return new BCryptPasswordEncoder();
        //return PasswordEncoderFactories.createDelegatingPasswordEncoder(); //this is the default with Spring auto-config
        return SfgPasswordEncoderFactories.createDelegatingPasswordEncoder(); //custom encoder factories
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorize -> {
                    authorize
                            .antMatchers("/", "/webjars/**", "/login", "/resources/**").permitAll()
                            .antMatchers("/beers/find", "/beers*").permitAll()
                            .antMatchers(HttpMethod.GET, "/api/v1/beer/**").permitAll()
                            .mvcMatchers(HttpMethod.GET, "/api/v1/beerUpc/{upc}").permitAll();
                })
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin().and()
                .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("spring")
                //.password("{noop}guru")
                //.password("guru")
                .password("{bcrypt}$2a$10$mjaS6qpjPFWasizDkqRcz.nCn6ZhfCyJr6V21ZHHL/vFcpZdnftBm")
                .roles("ADMIN")
                .and()
                .withUser("user")
                //.password("{noop}password") //inline reference to password encoder
                //.password("{SSHA}5G+t0ECst9IqoeArwtUAxRcam6vW8oqUTUQxaw==") //LDAP hashed password
                //.password("f5ccefc60826b7df96bff5576a4374883e6abf2811ccd720ea8055e6f983648ae010f0dd9ab9a8e6") //SHA-256 hashed password
                //.password("$2a$10$3Kmebmbq1oVRSXfkQYZKhOsDqBaZAnzUZYiWXrZUg4UC90ju9BNDm") //Bcrypt hashed password
                //.password("password") //plain text password, hashed using default or configured encoder
                .password("{sha256}1443907df472a2ab675ed32d4f532e0093dc82c5b662d4d00ff76817e7cb6f427495e9a2d790edb6")
                .roles("USER")
                .and()
                .withUser("scott")
                //.password("{noop}tiger")
                //.password("tiger")
                //.password("{ldap}{SSHA}BqJK0c/so24wZi6hLPp42sK0jUX5FcUanCwPcQ==")
                .password("{bcrypt15}$2a$15$1n64TBc3rvdDfSjwydsqyO6sgJMlZzfzwHRgn30InnoqeJqCChney")
                .roles("CUSTOMER");
    }

    /*
    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("spring")
                .password("guru")
                .roles("ADMIN")
                .build();

        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }
    */

}
