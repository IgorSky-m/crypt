package it.academy.cryptorest.repository;

import it.academy.cryptorest.pojo.BlockData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlockDataRepository extends JpaRepository<BlockData,String> {
    Optional<BlockData> findByBlockHash(String blockHash);
}
