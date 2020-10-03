package it.academy.cryptorest.repository;

import it.academy.cryptorest.pojo.BlockData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlockDataRepository extends JpaRepository<BlockData,String> {
    Optional<BlockData> findByBlockHash(String blockHash);
}
