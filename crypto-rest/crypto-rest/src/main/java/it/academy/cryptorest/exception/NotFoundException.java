package it.academy.cryptorest.exception;


public class NotFoundException extends RuntimeException {
        public NotFoundException(String id, Class clazz) {
            super("No such "+ clazz.getName()+" "+id);
        }
}
