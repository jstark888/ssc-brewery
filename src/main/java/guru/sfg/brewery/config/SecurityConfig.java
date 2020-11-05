package guru.sfg.brewery.config;

import guru.sfg.brewery.security.RestHeaderAuthFilter;
import guru.sfg.brewery.security.RestUrlAuthFilter;
import guru.sfg.brewery.security.SfgPasswordEncoderFactories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Created by Jeff Stark on 11/1/2020
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /*
    public RestHeaderAuthFilter restHeaderAuthFilter(AuthenticationManager authenticationManager) {
        RestHeaderAuthFilter filter = new RestHeaderAuthFilter(new AntPathRequestMatcher("/api/**"));
        filter.setAuthenticationManager(authenticationManager);
        return filter;
    }

    public RestUrlAuthFilter restUrlAuthFilter(AuthenticationManager authenticationManager) {
        RestUrlAuthFilter filter = new RestUrlAuthFilter(new AntPathRequestMatcher("/api/**"));
        filter.setAuthenticationManager(authenticationManager);
        return filter;
    }
    */

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
        /*
        http
                .addFilterBefore(restHeaderAuthFilter(authenticationManager()),
                                UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(restUrlAuthFilter(authenticationManager()),
                                    UsernamePasswordAuthenticationFilter.class)
                .csrf().disable();
         */

        http
                .authorizeRequests(authorize -> {
                    authorize
                            .antMatchers("/h2-console/**").permitAll() //do not use in prod
                            .antMatchers("/", "/webjars/**", "/login", "/resources/**").permitAll()
                            .antMatchers(HttpMethod.GET, "/api/v1/beer/**")
                                .hasAnyRole("ADMIN","CUSTOMER","USER")
                            //.mvcMatchers(HttpMethod.DELETE, "/api/v1/beer/**").hasRole("ADMIN")
                            .mvcMatchers(HttpMethod.GET, "/api/v1/beerUpc/{upc}")
                                .hasAnyRole("ADMIN","CUSTOMER","USER")
                            .mvcMatchers("/brewery/breweries")
                                .hasAnyRole("ADMIN", "CUSTOMER")
                            .mvcMatchers(HttpMethod.GET, "/brewery/api/v1/breweries")
                                .hasAnyRole("ADMIN", "CUSTOMER")
                            .mvcMatchers("/beers/find", "/beers/{beerId}")
                                .hasAnyRole("ADMIN","CUSTOMER","USER");
                })
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin().and()
                .httpBasic()
                .and()
                .csrf().disable();

        //h2-console config
        http.headers().frameOptions().sameOrigin();
    }

    /* //this has bcrypt 15 passwords, but bcrypt 15 is no long in SfgPasswordEncoderFactories
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
    */

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
