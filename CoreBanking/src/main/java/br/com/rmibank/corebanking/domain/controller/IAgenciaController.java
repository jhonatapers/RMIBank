package main.java.br.com.rmibank.corebanking.domain.controller;

import java.math.BigDecimal;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import main.java.br.com.rmibank.corebanking.domain.entity.Cliente;
import main.java.br.com.rmibank.corebanking.domain.entity.Transacao;
import main.java.br.com.rmibank.corebanking.domain.entity.aggregate.ContaCorrente;

public interface IAgenciaController extends Remote {

    public void cadastroCliente(int idempotecy, Cliente cliente) throws RemoteException;

    public List<ContaCorrente> consultaContasCorrentes(int idempotecy, long cpfCliente) throws RemoteException;

    public void aberturaContaCorrente(int idempotecy, long cpfCliente, ContaCorrente contaCorrente) throws RemoteException;

    public void fechamentoContaCorrente(int idempotecy, int agencia, long codigoContaCorrente) throws RemoteException;

    public Transacao saque(int idempotecy, int agencia, long codigoContaCorrente, BigDecimal valor) throws RemoteException;

    public Transacao deposito(int idempotecy, int agencia, long codigoContaCorrente, BigDecimal valor) throws RemoteException;

    public ContaCorrente saldo(int idempotecy, int agencia, long codigoContaCorrente) throws RemoteException;

    public List<Transacao> extrato(int idempotecy, int agencia, long codigoContaCorrente) throws RemoteException;

}
