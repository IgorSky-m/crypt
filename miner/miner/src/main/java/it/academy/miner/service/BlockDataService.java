package it.academy.miner.service;

import it.academy.miner.Status;
import it.academy.miner.pojo.BlockData;
import it.academy.miner.pojo.BlockTransaction;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Setter
@Getter
public class BlockDataService {

    @Autowired
    private HashService hashService;

    @Value("${block.data.version}")
    private String version;

    public BlockData buildBlockData(List<BlockTransaction> transactions) {

            BlockData blockData = getBlockData();

            String merkleRootHash = hashService.createMerkleRootHash(transactions);
            blockData.setTransactionsMerkleRoot(merkleRootHash);

            blockData.setTransactionCount(transactions.size());
            blockData.setStatus(Status.IN_PROGRESS);


            String hash = hashService.createHash(
                    blockData.getVersion() +
                            blockData.getTransactionCount() +
                            blockData.getTransactionsMerkleRoot());

            blockData.setDataHash(hash);

            return blockData;


    }


    private BlockData getBlockData() {
        return BlockData.builder().version(version).build();
    }
}
