package main.java.br.com.rmibank.corebanking.domain.controller;

import java.math.BigDecimal;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import main.java.br.com.rmibank.corebanking.domain.entity.Transacao;
import main.java.br.com.rmibank.corebanking.domain.entity.aggregate.ContaCorrente;

public interface IAtmController extends Remote {

    public Transacao saque(int agencia, long codigoContaCorrente, BigDecimal valor) throws RemoteException;

    public Transacao deposito(int agencia, long codigoContaCorrente, BigDecimal valor) throws RemoteException;

    public ContaCorrente saldo(int agencia, long codigoContaCorrente) throws RemoteException;

    public List<Transacao> extrato(int agencia, long codigoContaCorrente) throws RemoteException;

}
