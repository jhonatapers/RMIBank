package main.java.br.com.rmibank.corebanking.domain.service.impl;

import java.util.concurrent.Semaphore;

import main.java.br.com.rmibank.corebanking.domain.repository.IIdempotencyRepository;
import main.java.br.com.rmibank.corebanking.domain.service.IIdempotencyService;

public class IdempotencyService implements IIdempotencyService {

    IIdempotencyRepository idempotencyRepository;

    private Semaphore mutex;

    public IdempotencyService(IIdempotencyRepository idempotencyRepository) {
        this.idempotencyRepository = idempotencyRepository;
        this.mutex = new Semaphore(1);
    }

    @Override
    public int newIdempotency() {
        try {
            mutex.acquire();
            int idempotency = idempotencyRepository.newIdempotency();
            idempotencyRepository.saveGeneratedIdempotency(idempotency);
            return idempotency;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            mutex.release();
        }
    }

    @Override
    public boolean verifyIdempotency(int idempotency) {

        try {
            mutex.acquire();
            return idempotencyRepository.findGeneratedIdempotency(idempotency).isPresent();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            mutex.release();
        }

    }

    @Override
    public boolean existsTransaction(int idempotency) {

        try {
            mutex.acquire();
            return idempotencyRepository.findTransaction(idempotency).isPresent();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            mutex.release();
        }

    }

    @Override
    public void concludeTransaction(int idempotency) {

        try {
            mutex.acquire();
            idempotencyRepository.concludeTransaction(idempotency);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            mutex.release();
        }

    }

}
