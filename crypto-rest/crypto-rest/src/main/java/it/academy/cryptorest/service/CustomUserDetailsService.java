package it.academy.cryptorest.service;

import it.academy.cryptorest.pojo.CustomUser;
import it.academy.cryptorest.pojo.CustomUserRole;
import it.academy.cryptorest.repository.CustomUserRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Getter
@Setter
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private CustomUserRepository customUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        CustomUser customUser = customUserRepository.findByUserName(username);
        if (customUser == null) {
            throw new UsernameNotFoundException("unknown user "+username);
        }
        String[] roles = new String[customUser.getCustomUserRoles().size()];
        int i = 0;
        for (CustomUserRole customUserRole : customUser.getCustomUserRoles()) {
            roles[i] = customUserRole.getUserRole();
            i++;
        }

        return User.builder()
                .username(customUser.getUserName())
                .password(customUser.getUserPassword())
                .roles(roles)
                .build();

    }
}
