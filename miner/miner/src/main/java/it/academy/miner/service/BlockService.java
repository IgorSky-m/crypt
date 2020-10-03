package it.academy.miner.service;

import it.academy.miner.BackendConnector;
import it.academy.miner.exception.BlockCreatorException;
import it.academy.miner.exception.BlockServiceEception;
import it.academy.miner.exception.ServerConnectException;
import it.academy.miner.pojo.Block;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@Service
@Getter
@Setter
public class BlockService {

    private final Logger logger = Logger.getLogger(BlockService.class.getName());

    @Autowired
    private BackendConnector backendConnector;

    @Autowired
    private HashService hashService;

    private static boolean isCalc;

    public static void setFalseCalc() {
        isCalc = false;
    }

    public static void setTrueCalc(){
        isCalc = true;
    }


    public byte[] getActualBlockHashRule() {
        String rule =  backendConnector
                .getOneRequest("/rules/last").getBody();
        if (rule == null) throw new ServerConnectException("can't connect to server from the "+ BlockService.class.getName());
        return rule.getBytes();
    }


    public Block getLastBlock() {
        Block lastBlock = backendConnector.getBlockRequest("/blocks/last").getBody();
        if (lastBlock == null) throw new BlockServiceEception("can't read block");
        return lastBlock;

    }

    public Block calculateBlock(String blockDataHash){
        Block block =Block.builder()
                .timestamp(System.currentTimeMillis())
                .build();

        block.setBlockDataHash(blockDataHash);

        block.setPreviousHash(
                getLastBlock()
                .getHash()
        );


        final byte[] ruleBytes = getActualBlockHashRule();
        byte[] blockValidateBytes;
        long nonce =0;

        setTrueCalc();

        long start = System.currentTimeMillis();

        while (isCalc) {
            block.setNonce(nonce);
            block.setHash(hashService.createHash(block));

            blockValidateBytes = Arrays.copyOf(
                    block.getHash().getBytes(),
                    ruleBytes.length
            );

            if (Arrays.equals(blockValidateBytes, ruleBytes)) break;

            nonce++;

            if (nonce < 0) {
                logger.info("nonce: negative");
                throw new BlockCreatorException("cant find block hash");
            }

        }

        long end = System.currentTimeMillis();
        if (!isCalc) throw new BlockCreatorException("calculate interrupt");

        block.setNonce(nonce);

        String infoMsg = "Create time: " + TimeUnit.SECONDS.toSeconds((end - start));
        logger.info(infoMsg);

        return block;
    }

}
