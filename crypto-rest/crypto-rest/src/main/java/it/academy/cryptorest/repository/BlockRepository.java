package it.academy.cryptorest.repository;

import it.academy.cryptorest.pojo.Block;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockRepository extends JpaRepository<Block,String> {
    @Query("from Block b where b.hash not in (select previousHash from Block)")
    Block findLastBlock();
}
