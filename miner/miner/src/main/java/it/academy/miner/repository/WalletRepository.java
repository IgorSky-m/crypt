package it.academy.miner.repository;


import it.academy.miner.pojo.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet,String> {
}
