package it.academy.cryptorest.exception;


public class UpdateException extends RuntimeException {

    public UpdateException(String id, Class clazz) {
        super("can't update "+clazz.getName()+" "+id);
    }
}
