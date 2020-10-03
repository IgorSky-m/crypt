package it.academy.miner.exception;

public class BlockCreatorException extends RuntimeException {
    public BlockCreatorException(String invalid_transactions) {
        super(invalid_transactions);
    }
}
