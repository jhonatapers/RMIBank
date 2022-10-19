package main.java.br.com.rmibank.corebanking.domain.controller;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IIdempotencyController extends Remote {

    public int newIdempotency() throws RemoteException;

}
