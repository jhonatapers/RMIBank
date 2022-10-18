package main.java.br.com.rmibank.corebanking.adapters.database.repository.impl;

import java.util.Optional;

import main.java.br.com.rmibank.corebanking.adapters.database.RmiBankSchema;
import main.java.br.com.rmibank.corebanking.domain.repository.IIdempotencyRepository;

public class IdempotencyRepositoryLocalJavaClass implements IIdempotencyRepository {

    private RmiBankSchema dataBase;

    public IdempotencyRepositoryLocalJavaClass(RmiBankSchema dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public int nextIdempotency() {

        if (dataBase.transactions.size() == 0)
            return 1;

        return dataBase.transactions.size();

    }

    @Override
    public Optional<Integer> findIdempotency(int idempotency) {

        return dataBase.transactions.stream().filter(t -> t == idempotency).findFirst();

    }

}
