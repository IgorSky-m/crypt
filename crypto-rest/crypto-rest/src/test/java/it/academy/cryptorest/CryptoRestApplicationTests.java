package it.academy.cryptorest;

import it.academy.cryptorest.repository.CustomUserRepository;
import it.academy.cryptorest.service.CustomUserDetailsService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;



@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {CryptoRestApplication.class})
@ActiveProfiles("test")
@WebAppConfiguration
class CryptoRestApplicationTests {

	@Autowired
	CustomUserRepository customUserRepository;

	@Autowired
	CustomUserDetailsService customUserDetailsService;

	@Test
	void contextLoads() {
	}

}
