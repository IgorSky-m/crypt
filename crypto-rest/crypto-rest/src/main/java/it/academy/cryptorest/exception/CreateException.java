package it.academy.cryptorest.exception;

public class CreateException extends RuntimeException {
    public CreateException(String id, Class clazz) {
        super("Can't create "+clazz.getName()+ " "+id);
    }

    public CreateException(String id,String cause,  Class clazz) {
        super("Can't create "+clazz.getName()+ " "+id+ ". Cause: "+ cause);
    }
}
