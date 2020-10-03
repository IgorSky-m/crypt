package it.academy.cryptorest.repository;


import it.academy.cryptorest.pojo.BlockHashRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BlockHashRuleRepository extends JpaRepository<BlockHashRule, Long> {
    @Query("from BlockHashRule b where b.timestamp = (select max(timestamp) from BlockHashRule)")
    BlockHashRule findLastRuleByTimestamp();
}
