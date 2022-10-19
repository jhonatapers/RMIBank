package main.java.br.com.rmibank.corebanking.domain.exception;

public class IdempotencyException extends RuntimeException {

    public IdempotencyException(String string) {
        super(string);
    }

    public IdempotencyException(String string, Exception e) {
        super(string, e);
    }

}
