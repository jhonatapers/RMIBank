package main.java.br.com.rmibank.corebanking.domain.repository;

import java.util.Optional;

public interface IIdempotencyRepository {

    public int nextIdempotency();

    public Optional<Integer> findIdempotency(int idempotecy);

}
