package it.academy.comparator;

import it.academy.miner.pojo.BlockTransaction;

import java.util.Comparator;

public class BlockTransactionComparator implements Comparator<BlockTransaction> {
    @Override
    public int compare(BlockTransaction o1, BlockTransaction o2) {
           return o1.getHash().compareTo(o2.getHash());
    }
}
