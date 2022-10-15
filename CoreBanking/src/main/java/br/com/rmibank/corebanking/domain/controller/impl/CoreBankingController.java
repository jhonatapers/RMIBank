package main.java.br.com.rmibank.corebanking.domain.controller.impl;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import main.java.br.com.rmibank.corebanking.domain.controller.IAgenciaController;
import main.java.br.com.rmibank.corebanking.domain.controller.IAtmController;
import main.java.br.com.rmibank.corebanking.domain.dto.Comprovante;
import main.java.br.com.rmibank.corebanking.domain.entity.Cliente;
import main.java.br.com.rmibank.corebanking.domain.entity.aggregate.ContaCorrente;
import main.java.br.com.rmibank.corebanking.domain.service.IClienteService;

public class CoreBankingController extends UnicastRemoteObject implements IAgenciaController, IAtmController {

    private IClienteService clienteService;

    public CoreBankingController(IClienteService contaCorrenteService) throws RemoteException {
        super();
        this.clienteService = contaCorrenteService;
    }

    @Override
    public void cadastroCliente(Cliente cliente) throws RemoteException {
        clienteService.cadastro(cliente);
    }

    @Override
    public List<ContaCorrente> consultaContasCorrentes(long cpfCliente) throws RemoteException {
        return clienteService.contas(cpfCliente);
    }

    @Override
    public void aberturaContaCorrente(long cpfCliente, ContaCorrente contaCorrente) throws RemoteException {
        try {
            clienteService.aberturaContaCorrente(cpfCliente, contaCorrente);
        } catch (Exception e) {
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public void fechamentoContaCorrente(int agencia, long codigoContaCorrente) throws RemoteException {
        clienteService.enccerraContaCorrente(agencia, codigoContaCorrente);
    }

    @Override
    public Comprovante saque(int agencia, long codigoContaCorrente, BigDecimal valor) throws RemoteException {
        return clienteService.saque(agencia, codigoContaCorrente, valor);
    }

    @Override
    public Comprovante deposito(int agencia, long codigoContaCorrente, BigDecimal valor) throws RemoteException {
        return clienteService.deposito(agencia, codigoContaCorrente, valor);
    }

    @Override
    public Comprovante saldo(int agencia, long codigoContaCorrente) throws RemoteException {
        return clienteService.saldo(agencia, codigoContaCorrente);
    }

}
