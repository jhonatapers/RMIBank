package main.java.br.com.rmibank.corebanking.domain.repository;

import java.util.Optional;

public interface IIdempotencyRepository {

    public int newIdempotency();
    
    public void saveGeneratedIdempotency(int idempotency);

    public Optional<Integer> findGeneratedIdempotency(int idempotency);

    public Optional<Integer> findTransaction(int idempotency);

    public void concludeTransaction(int idempotency);

}
