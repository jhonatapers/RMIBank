package main.java.br.com.rmibank.corebanking.domain.controller.impl;

import main.java.br.com.rmibank.corebanking.domain.controller.IIdempotencyController;
import main.java.br.com.rmibank.corebanking.domain.service.IIdempotencyService;

public class IdempotencyController implements IIdempotencyController {

    private IIdempotencyService idempotencyService;

    public IdempotencyController(IIdempotencyService idempotencyService) {
        this.idempotencyService = idempotencyService;
    }

    @Override
    public int getIdempotency() {
        return idempotencyService.getIdempotency();
    }

}
