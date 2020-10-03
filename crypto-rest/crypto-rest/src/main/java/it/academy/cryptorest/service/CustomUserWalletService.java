package it.academy.cryptorest.service;

import it.academy.cryptorest.pojo.UserWallet;
import it.academy.cryptorest.repository.UserWalletRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
public class CustomUserWalletService {

    @Autowired
    private UserWalletRepository userWalletRepository;

    public UserWallet save(UserWallet userWallet) {
        return userWalletRepository.saveAndFlush(userWallet);
    }

}
