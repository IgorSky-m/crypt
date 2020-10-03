package it.academy.miner.repository;

import it.academy.miner.pojo.BlockData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockDataRepository extends JpaRepository<BlockData,String> {
}
