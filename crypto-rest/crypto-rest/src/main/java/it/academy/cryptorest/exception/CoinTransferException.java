package it.academy.cryptorest.exception;

public class CoinTransferException extends RuntimeException {
    public CoinTransferException(String value) {
        super("operation rejected. Value: "+ value+" greater then wallet have");
    }
}
