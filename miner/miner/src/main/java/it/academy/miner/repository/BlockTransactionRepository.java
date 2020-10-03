package it.academy.miner.repository;

import it.academy.miner.pojo.BlockTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockTransactionRepository extends JpaRepository<BlockTransaction,String> {
}
