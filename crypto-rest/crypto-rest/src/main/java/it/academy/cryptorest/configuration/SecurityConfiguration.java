package it.academy.cryptorest.configuration;

import it.academy.cryptorest.filter.CorsFilter;
import it.academy.cryptorest.filter.JwtFilter;
import it.academy.cryptorest.service.CustomUserDetailsService;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.ejb.HibernateEntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Map;

@Configuration
@Getter
@Setter
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private Map<String,String> roles;
    {
        roles = Map.of(
                "user", "ROLE_USER",
                "admin", "ROLE_ADMIN"
        );
    }

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private CorsFilter corsFilter;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;





    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());

    }

    @Bean
    public CustomUserDetailsService userDetails() {
        return new CustomUserDetailsService();
    }




    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean () throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().httpBasic().disable()
                .authorizeRequests()
                .antMatchers("/authenticate").permitAll()
                .antMatchers(HttpMethod.GET,"/rules/**").permitAll()
                .antMatchers(HttpMethod.POST, "/users").permitAll()
                .antMatchers(HttpMethod.GET,"/users").hasAuthority(roles.get("user"))

                .antMatchers("/users/**").hasAuthority(roles.get("user"))

                .antMatchers(HttpMethod.GET,"/generate/wallet").permitAll()
                .antMatchers(HttpMethod.POST,"/wallets").hasAuthority(roles.get("user"))
                .antMatchers("/wallets/**").hasAnyAuthority(roles.get("user"))
                .anyRequest().permitAll()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

    }

}






