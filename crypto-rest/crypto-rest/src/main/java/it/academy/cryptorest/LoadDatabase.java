package it.academy.cryptorest;

import it.academy.cryptorest.pojo.BlockHashRule;
import it.academy.cryptorest.pojo.CustomUser;
import it.academy.cryptorest.pojo.CustomUserRole;
import it.academy.cryptorest.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    public CommandLineRunner initDatabase(BlockRepository blockRepository,
                                          BlockDataRepository blockDataRepository,
                                          BlockTransactionRepository blockTransactionRepository,
                                          CustomUserRepository userRepository,
                                          WalletRepository walletRepository,
                                          BlockHashRuleRepository blockHashRuleRepository

    ) {
        Random random = new Random();
        return args -> {
            BlockHashRule blockHashRule = blockHashRuleRepository.findLastRuleByTimestamp();

            CustomUser user = CustomUser.builder()
                    .userName("Name"+random.nextInt(100000))
                    .customUserRoles(createRoles())
                    .id(random.nextInt(1000000)+"")
                    .mobile(random.nextInt(100000)+"")
                    .userPassword("password"+(random.nextInt(100000)))
                    .emailAddress(random.nextInt(1000000)+"@mail.com")
                    .build();

            userRepository.save(user);



        };
    }

    private Set<CustomUserRole> createRoles() {
        CustomUserRole admin = new CustomUserRole();
        admin.setUserRole("ADMIN");
        admin.setId(0);
        CustomUserRole user = new CustomUserRole();
        admin.setUserRole("USER");
        admin.setId(1);
        Set<CustomUserRole> userRoles = new HashSet<>();
        if (Math.random() > 9.0) {
            userRoles.add(admin);
            userRoles.add(user);
        } else if (Math.random() > 7) {
            userRoles.add(admin);
        } else userRoles.add(user);

        return userRoles;
    }
}


