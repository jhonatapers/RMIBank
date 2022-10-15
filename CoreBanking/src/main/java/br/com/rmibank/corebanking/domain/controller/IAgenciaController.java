package main.java.br.com.rmibank.corebanking.domain.controller;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.List;

import main.java.br.com.rmibank.corebanking.domain.dto.Comprovante;
import main.java.br.com.rmibank.corebanking.domain.entity.Cliente;
import main.java.br.com.rmibank.corebanking.domain.entity.aggregate.ContaCorrente;

public interface IAgenciaController {

    public void cadastroCliente(Cliente cliente) throws RemoteException;

    public List<ContaCorrente> consultaContasCorrentes(long cpfCliente) throws RemoteException;

    public void aberturaContaCorrente(long cpfCliente, ContaCorrente contaCorrente) throws RemoteException;

    public void fechamentoContaCorrente(int agencia, long codigoContaCorrente) throws RemoteException;

    public Comprovante saque(int agencia, long codigoContaCorrente, BigDecimal valor) throws RemoteException;

    public Comprovante deposito(int agencia, long codigoContaCorrente, BigDecimal valor) throws RemoteException;

    public Comprovante saldo(int agencia, long codigoContaCorrente) throws RemoteException;

}
