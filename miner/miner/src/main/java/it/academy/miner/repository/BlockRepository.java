package it.academy.miner.repository;

import it.academy.miner.pojo.Block;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockRepository extends JpaRepository<Block,String> {
}
