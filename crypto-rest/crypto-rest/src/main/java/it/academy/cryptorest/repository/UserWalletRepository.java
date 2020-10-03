package it.academy.cryptorest.repository;

import it.academy.cryptorest.pojo.UserWallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserWalletRepository extends JpaRepository<UserWallet,String> {
    List<UserWallet> findAllByUserId(String userId);
    List<UserWallet> findAllByWalletId (String walletId);

}
