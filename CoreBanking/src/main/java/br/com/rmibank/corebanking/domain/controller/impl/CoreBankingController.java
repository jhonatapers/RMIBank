package main.java.br.com.rmibank.corebanking.domain.controller.impl;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import main.java.br.com.rmibank.corebanking.domain.controller.IAgenciaController;
import main.java.br.com.rmibank.corebanking.domain.controller.IAtmController;
import main.java.br.com.rmibank.corebanking.domain.dto.OperacaoEnum;
import main.java.br.com.rmibank.corebanking.domain.entity.Cliente;
import main.java.br.com.rmibank.corebanking.domain.entity.Transacao;
import main.java.br.com.rmibank.corebanking.domain.entity.aggregate.ContaCorrente;
import main.java.br.com.rmibank.corebanking.domain.service.IClienteService;
import main.java.br.com.rmibank.corebanking.domain.service.ITransacaoService;

public class CoreBankingController extends UnicastRemoteObject implements IAgenciaController, IAtmController {

    private IClienteService clienteService;

    private ITransacaoService transacaoService;

    public CoreBankingController(IClienteService contaCorrenteService, ITransacaoService transacaoService)
            throws RemoteException {
        super();
        this.clienteService = contaCorrenteService;
        this.transacaoService = transacaoService;
    }

    @Override
    public void cadastroCliente(int idempotecy, Cliente cliente) throws RemoteException {
        clienteService.cadastro(cliente);
    }

    @Override
    public List<ContaCorrente> consultaContasCorrentes(int idempotecy, long cpfCliente) throws RemoteException {
        return clienteService.contas(cpfCliente);
    }

    @Override
    public void aberturaContaCorrente(int idempotecy, long cpfCliente, ContaCorrente contaCorrente)
            throws RemoteException {
        try {
            clienteService.aberturaContaCorrente(cpfCliente, contaCorrente);
        } catch (Exception e) {
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public void fechamentoContaCorrente(int idempotecy, int agencia, long codigoContaCorrente) throws RemoteException {
        clienteService.enccerraContaCorrente(agencia, codigoContaCorrente);
    }

    @Override
    public Transacao saque(int idempotecy, int agencia, long codigoContaCorrente, BigDecimal valor)
            throws RemoteException {

        try {

            Transacao transacao = clienteService.saque(agencia, codigoContaCorrente, valor);
            transacaoService.efetuaTransacao(new Transacao(agencia, codigoContaCorrente, valor, OperacaoEnum.SAQUE));

            return transacao;

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public Transacao deposito(int idempotecy, int agencia, long codigoContaCorrente, BigDecimal valor)
            throws RemoteException {
        try {

            Transacao transacao = clienteService.deposito(agencia, codigoContaCorrente, valor);
            transacaoService.efetuaTransacao(new Transacao(agencia, codigoContaCorrente, valor, OperacaoEnum.DEPOSITO));

            return transacao;

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }

    }

    @Override
    public ContaCorrente saldo(int idempotecy, int agencia, long codigoContaCorrente) throws RemoteException {
        return clienteService.saldo(agencia, codigoContaCorrente);
    }

    @Override
    public List<Transacao> extrato(int idempotecy, int agencia, long codigoContaCorrente) throws RemoteException {
        return transacaoService.extrato(agencia, codigoContaCorrente);
    }

}
