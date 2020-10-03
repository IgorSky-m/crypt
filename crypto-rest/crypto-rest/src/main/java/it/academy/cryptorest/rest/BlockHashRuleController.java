package it.academy.cryptorest.rest;

import it.academy.cryptorest.pojo.BlockHashRule;
import it.academy.cryptorest.service.BlockHashRuleService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Getter
@Setter
public class BlockHashRuleController {

    @Autowired
    private BlockHashRuleService blockHashRuleService;


    @GetMapping("/rules/last")
    public ResponseEntity<String> getLast(){
        BlockHashRule blockHashRule = blockHashRuleService.findLastRuleByTimestamp();
        if (blockHashRule == null) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
        return ResponseEntity.ok(blockHashRule.getHashRule());
    }
}
