package main.java.br.com.rmibank.corebanking.domain.service.impl;

import main.java.br.com.rmibank.corebanking.domain.repository.IIdempotencyRepository;
import main.java.br.com.rmibank.corebanking.domain.service.IIdempotencyService;

public class IdempotencyService implements IIdempotencyService {

    IIdempotencyRepository idempotencyRepository;

    public IdempotencyService(IIdempotencyRepository idempotencyRepository) {
        this.idempotencyRepository = idempotencyRepository;
    }

    @Override
    public int getIdempotency() {
        return idempotencyRepository.nextIdempotency();
    }

    @Override
    public boolean verifyIdempotency(int idempotecy) {
        return idempotencyRepository.findIdempotency(idempotecy).isEmpty();
    }

}
