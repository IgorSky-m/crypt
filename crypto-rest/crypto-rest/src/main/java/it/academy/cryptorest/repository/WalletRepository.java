package it.academy.cryptorest.repository;

import it.academy.cryptorest.pojo.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet,String> {
    Optional<Wallet> findBySecretKey(String secretKey);

    List<Wallet> findAllByIdIn(List<String> listId);
}
