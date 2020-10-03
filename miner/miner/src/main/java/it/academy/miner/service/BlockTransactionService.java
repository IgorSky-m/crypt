package it.academy.miner.service;

import it.academy.miner.BackendConnector;
import it.academy.miner.Status;
import it.academy.miner.exception.BlockTransactionServiceException;
import it.academy.miner.pojo.BlockTransaction;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Service
@Getter
@Setter
public class BlockTransactionService {

    @Autowired
    private BackendConnector backendConnector;

    @Autowired
    private HashService hashService;



    public List<BlockTransaction> findAllBlockTransactionsByStatus(Status status) {
        ResponseEntity<List> collection = backendConnector.getListRequests("/transactions/status_" + status);
        if (collection == null) throw new BlockTransactionServiceException("can't find transactions");

        return build(collection.getBody());

    }

    private List<BlockTransaction> build(List<LinkedHashMap<String, Object>> transactions) {
        List<BlockTransaction> blockTransactions = new LinkedList<>();
        for (LinkedHashMap<String, Object> transaction : transactions) {
            String timestamp = String.valueOf(transaction.get("timestamp"));
            blockTransactions.add(
                    BlockTransaction.builder()
                            .hash((String) transaction.get("hash"))
                            .walletIdTo((String) transaction.get("walletIdTo"))
                            .signature((String) transaction.get("secretKey"))
                            .value((Integer) transaction.get("value"))
                            .timestamp(Long.parseLong(timestamp))
                            .status(Status.valueOf((String) transaction.get("status")))
                            .build()

            );


        }
        return blockTransactions;
    }

    public List<BlockTransaction> setBlockHash(List<BlockTransaction> transactions, String blockHash) {
        for (BlockTransaction transaction : transactions) {
            transaction.setBlockHash(blockHash);
        }
        return transactions;
    }

    public BlockTransaction buildBlockTransaction(String walletIdTo) {
        String blockKey = backendConnector.getOneRequest("/blocks/key").getBody();
        String reward = backendConnector.getOneRequest("/blocks/reward").getBody();
        BlockTransaction transaction = BlockTransaction.builder()
                .timestamp(System.currentTimeMillis())
                .value(Integer.parseInt(Objects.requireNonNull(reward)))
                .walletIdTo(walletIdTo)
                .signature(blockKey)
                .build();
        transaction.setHash(hashService.createHash(transaction));

        return transaction;
    }
}