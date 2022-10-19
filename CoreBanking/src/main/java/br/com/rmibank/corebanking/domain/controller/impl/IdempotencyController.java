package main.java.br.com.rmibank.corebanking.domain.controller.impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import main.java.br.com.rmibank.corebanking.domain.controller.IIdempotencyController;
import main.java.br.com.rmibank.corebanking.domain.service.IIdempotencyService;

public class IdempotencyController extends UnicastRemoteObject implements IIdempotencyController {

    private IIdempotencyService idempotencyService;

    public IdempotencyController(IIdempotencyService idempotencyService) throws RemoteException {
        super();
        this.idempotencyService = idempotencyService;
    }

    @Override
    public int newIdempotency() throws RemoteException {
        return idempotencyService.newIdempotency();
    }

}
