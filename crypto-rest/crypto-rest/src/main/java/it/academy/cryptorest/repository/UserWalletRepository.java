package it.academy.cryptorest.repository;

import it.academy.cryptorest.pojo.UserWallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserWalletRepository extends JpaRepository<UserWallet,String> {
    List<UserWallet> findAllByUserId(String userId);
    List<UserWallet> findAllByWalletId (String walletId);

}
