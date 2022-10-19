package main.java.br.com.rmibank.corebanking.adapters.database.repository.impl;

import java.util.Optional;
import java.util.concurrent.Semaphore;

import main.java.br.com.rmibank.corebanking.adapters.database.RmiBankSchema;
import main.java.br.com.rmibank.corebanking.domain.repository.IIdempotencyRepository;

public class IdempotencyRepositoryLocalJavaClass implements IIdempotencyRepository {

    private RmiBankSchema dataBase;

    private Semaphore mutex;

    public IdempotencyRepositoryLocalJavaClass(RmiBankSchema dataBase) {
        this.dataBase = dataBase;
        this.mutex = new Semaphore(1);
    }

    @Override
    public int newIdempotency() {

        try {
            mutex.acquire();

            if (dataBase.transactions.size() == 0)
                return 1;

            return dataBase.transactions.size();

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            mutex.release();
        }

    }

    @Override
    public void saveGeneratedIdempotency(int idempotency) {
        try {
            mutex.acquire();

            dataBase.generatedIdempotencies.add(idempotency);

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            mutex.release();
        }
    }

    @Override
    public Optional<Integer> findGeneratedIdempotency(int idempotency) {
        try {
            mutex.acquire();
            return dataBase.generatedIdempotencies.stream().filter(g -> g == idempotency).findFirst();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            mutex.release();
        }
    }

    @Override
    public Optional<Integer> findTransaction(int idempotency) {
        try {
            mutex.acquire();
            return dataBase.transactions.stream().filter(t -> t == idempotency).findFirst();
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
            dataBase.transactions.add(idempotency);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            mutex.release();
        }
    }

}
