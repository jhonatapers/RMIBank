package main.java.br.com.rmibank.corebanking.domain.service;

public interface IIdempotencyService {

    public int newIdempotency();

    public boolean verifyIdempotency(int idempotency);

    public boolean existsTransaction(int idempotency);

    public void concludeTransaction(int idempotency);

}
