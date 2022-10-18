package main.java.br.com.rmibank.corebanking.domain.service;

public interface IIdempotencyService {
    
    public int getIdempotency();

    public boolean verifyIdempotency(int idempotecy);

}
