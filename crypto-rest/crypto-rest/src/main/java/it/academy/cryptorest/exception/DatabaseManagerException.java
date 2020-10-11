package it.academy.cryptorest.exception;

public class DatabaseManagerException extends RuntimeException {
    public DatabaseManagerException(Exception e) {
        super(e.getMessage());

    }
}
