package it.academy.cryptorest.exception;

public class NullEntityException  extends RuntimeException{
    public NullEntityException(Class clazz,String action){
        super("Null entity came to "+clazz.getName()+". Action: "+action);
    }

    public NullEntityException(Class clazz) {
        super("Null entity came to "+clazz.getName());
    }
}
