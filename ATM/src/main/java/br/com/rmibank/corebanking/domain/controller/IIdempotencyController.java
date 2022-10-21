package main.java.br.com.rmibank.corebanking.domain.controller;

import java.rmi.Remote;

public interface IIdempotencyController extends Remote {

    public int newIdempotency();

}
