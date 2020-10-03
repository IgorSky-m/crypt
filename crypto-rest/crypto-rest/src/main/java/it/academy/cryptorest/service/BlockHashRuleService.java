package it.academy.cryptorest.service;

import it.academy.cryptorest.pojo.BlockHashRule;
import it.academy.cryptorest.repository.BlockHashRuleRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
public class BlockHashRuleService {

    @Autowired
    private BlockHashRuleRepository blockHashRuleRepository;

    public BlockHashRule findLastRuleByTimestamp() {
        return blockHashRuleRepository.findLastRuleByTimestamp();
    }
}
