package main.java.br.com.rmibank.corebanking.domain.controller;

import java.math.BigDecimal;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import main.java.br.com.rmibank.corebanking.domain.entity.Cliente;
import main.java.br.com.rmibank.corebanking.domain.entity.Transacao;
import main.java.br.com.rmibank.corebanking.domain.entity.aggregate.ContaCorrente;

public interface IAgenciaController extends Remote {

    public void cadastroCliente(int idempotency, Cliente cliente) throws RemoteException;

    public List<ContaCorrente> consultaContasCorrentes(long cpfCliente) throws RemoteException;

    public void aberturaContaCorrente(int idempotency, long cpfCliente, ContaCorrente contaCorrente) throws RemoteException;

    public void fechamentoContaCorrente(int idempotency, int agencia, long codigoContaCorrente) throws RemoteException;

    public Transacao saque(int idempotency, int agencia, long codigoContaCorrente, BigDecimal valor) throws RemoteException;

    public Transacao deposito(int idempotency, int agencia, long codigoContaCorrente, BigDecimal valor) throws RemoteException;

    public ContaCorrente saldo(int agencia, long codigoContaCorrente) throws RemoteException;

    public List<Transacao> extrato(int agencia, long codigoContaCorrente) throws RemoteException;

}
