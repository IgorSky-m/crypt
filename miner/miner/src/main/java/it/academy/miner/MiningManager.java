package it.academy.miner;

import it.academy.comparator.BlockTransactionComparator;
import it.academy.miner.pojo.Block;
import it.academy.miner.pojo.BlockData;
import it.academy.miner.pojo.BlockTransaction;
import it.academy.miner.service.BlockDataService;
import it.academy.miner.service.BlockService;
import it.academy.miner.service.BlockTransactionService;
import it.academy.miner.service.WalletService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

@Service
@Getter
@Setter
@AllArgsConstructor
public class MiningManager {

    @Autowired
    private BackendConnector backendConnector;

    @Autowired
    private BlockDataService blockDataService;

    @Autowired
    private BlockService blockService;

    @Autowired
    private BlockTransactionService blockTransactionService;

    @Autowired
    private WalletService walletService;


    private final Logger logger;
    private boolean isStarted;

    public MiningManager() {
        logger = Logger.getLogger(MiningManager.class.getName());
        isStarted = false;
    }

    public void startMining() {
        if (isStarted) {
            logger.info("already started");
        } else {
            try {
                isStarted = true;
                logger.info("start block mining in thread: ");
                int time = 0;
                while (isStarted && time < 1) {
                    System.out.println("get transactions");
                    List<BlockTransaction> transactions = blockTransactionService.findAllBlockTransactionsByStatus(Status.APPROVED);
                    System.out.println("get wallet id");
                    String walletId = walletService.getWalletId();

                    transactions.add(blockTransactionService.buildBlockTransaction(walletId));
                    Collections.sort(transactions, new BlockTransactionComparator());
                    BlockData blockData = blockDataService.buildBlockData(transactions);
                    logger.info("start mining block");
                    Block block = blockService.calculateBlock(blockData.getDataHash());

                    blockData.setBlockHash(block.getHash());

                    transactions = blockTransactionService.setBlockHash(transactions, block.getHash());
                    for (BlockTransaction transaction : transactions) {
                        backendConnector.postRequest(transaction, "/transactions/" + transaction.getHash() + "/saved");
                    }


                    logger.info("create block with hash: " + block.getHash());
                    isStarted = false;
                    time++;

                }
            } finally {
                isStarted =false;
            }
        }
    }


    public void stopMining() {

    }
}
