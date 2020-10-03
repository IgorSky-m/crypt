package it.academy.miner.service;

import it.academy.miner.exception.HashServiceException;
import it.academy.miner.pojo.Block;
import it.academy.miner.pojo.BlockData;
import it.academy.miner.pojo.BlockTransaction;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HashService {




    public String createMerkleRootHash(List<BlockTransaction> blockTransactions) {
        int mid;
        String leftHash;

//        for (BlockTransaction blockTransaction : blockTransactions) {
//            blockTransaction.setHash(createHash(blockTransaction));
//        }


        String rightHash;
        if (blockTransactions.size() >2) {
            mid = blockTransactions.size() / 2;

            List<BlockTransaction> transactionsLeft = blockTransactions.stream()
                    .limit(mid)
                    .collect(Collectors.toList());

            List<BlockTransaction> transactionsRight = blockTransactions.stream()
                    .skip(mid)
                    .collect(Collectors.toList());

            leftHash = createMerkleRootHash(transactionsLeft);
            rightHash = createMerkleRootHash(transactionsRight);

            return createHash(leftHash.concat(rightHash));
        }

        if (blockTransactions.size() == 1) {

            return createHash(blockTransactions.get(0).getHash()
                    .concat(blockTransactions.get(0).getHash()));
        } else if (blockTransactions.size() == 2) {
            return createHash(blockTransactions.get(0).getHash()
                    .concat(blockTransactions.get(1).getHash()));
        } else throw new HashServiceException("illegal transactions size");


    }

    public String createHash(String str){
        byte[] stringBytes = str.getBytes(StandardCharsets.UTF_8);
        MessageDigest digest;
        String encoded = null;
        try {
            digest = MessageDigest.getInstance("SHA3-256");
            byte[] byteHash = digest.digest(stringBytes);
            encoded = Base64.getUrlEncoder().encodeToString(byteHash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return encoded;
    }



    public String createHash(BlockTransaction blockTransaction){
        String transactionString =
                blockTransaction.getWalletIdTo() +
                blockTransaction.getSignature()+
                blockTransaction.getValue().toString()
                +blockTransaction.getTimestamp();

        return createHash(transactionString);

    }

    public String createHash(BlockData blockData){
        String blockDataStr =
                blockData.getVersion() +
                blockData.getTransactionCount()+
                blockData.getTransactionsMerkleRoot();

        return createHash(blockDataStr);
    }

    public String createHash(Block block){
        String  blockStr = block.getTimestamp() +
                block.getPreviousHash() +
                block.getBlockDataHash() +
                block.getNonce();

        return createHash(blockStr);
    }

}
