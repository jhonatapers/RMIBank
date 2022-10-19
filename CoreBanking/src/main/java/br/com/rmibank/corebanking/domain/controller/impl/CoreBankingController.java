package main.java.br.com.rmibank.corebanking.domain.controller.impl;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import main.java.br.com.rmibank.corebanking.domain.controller.IAgenciaController;
import main.java.br.com.rmibank.corebanking.domain.controller.IAtmController;
import main.java.br.com.rmibank.corebanking.domain.entity.Cliente;
import main.java.br.com.rmibank.corebanking.domain.entity.Transacao;
import main.java.br.com.rmibank.corebanking.domain.entity.aggregate.ContaCorrente;
import main.java.br.com.rmibank.corebanking.domain.exception.IdempotencyException;
import main.java.br.com.rmibank.corebanking.domain.service.IClienteService;
import main.java.br.com.rmibank.corebanking.domain.service.IIdempotencyService;
import main.java.br.com.rmibank.corebanking.domain.service.ITransacaoService;

public class CoreBankingController extends UnicastRemoteObject implements IAgenciaController, IAtmController {

    private IClienteService clienteService;

    private ITransacaoService transacaoService;

    private IIdempotencyService idempotencyService;

    public CoreBankingController(
            IIdempotencyService idempotencyService,
            IClienteService contaCorrenteService,
            ITransacaoService transacaoService)
            throws RemoteException {
        super();
        this.idempotencyService = idempotencyService;
        this.clienteService = contaCorrenteService;
        this.transacaoService = transacaoService;
    }

    @Override
    public int newIdempotency() throws RemoteException {
        return idempotencyService.newIdempotency();
    }

    @Override
    public void cadastroCliente(int idempotency, Cliente cliente) throws RemoteException {
        clienteService.cadastro(idempotency, cliente);
    }

    @Override
    public List<ContaCorrente> consultaContasCorrentes(long cpfCliente) throws RemoteException {
        return clienteService.contas(cpfCliente);
    }

    @Override
    public void aberturaContaCorrente(int idempotency, long cpfCliente, ContaCorrente contaCorrente)
            throws RemoteException {
        try {
            clienteService.aberturaContaCorrente(idempotency, cpfCliente, contaCorrente);
        } catch (Exception e) {
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public void fechamentoContaCorrente(int idempotency, int agencia, long codigoContaCorrente) throws RemoteException {
        clienteService.enccerraContaCorrente(idempotency, agencia, codigoContaCorrente);
    }

    @Override
    public Transacao saque(int idempotency, int agencia, long codigoContaCorrente, BigDecimal valor)
            throws RemoteException {

        try {

            Transacao transacao = clienteService.saque(idempotency, agencia, codigoContaCorrente, valor);

            try {
                transacaoService.armazenaTransacao(idempotency, transacao);
            } catch (IdempotencyException ex) {
                clienteService.deposito(idempotency, agencia, codigoContaCorrente, valor);
                throw new RuntimeException(ex.getMessage(), ex);
            }

            idempotencyService.concludeTransaction(idempotency);

            return transacao;

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }

    }

    @Override
    public Transacao deposito(int idempotency, int agencia, long codigoContaCorrente, BigDecimal valor)
            throws RemoteException {
        try {

            Transacao transacao = clienteService.deposito(idempotency, agencia, codigoContaCorrente, valor);

            try {
                transacaoService.armazenaTransacao(idempotency, transacao);
            } catch (IdempotencyException ex) {
                clienteService.saque(idempotency, agencia, codigoContaCorrente, valor);
                throw new RuntimeException(ex.getMessage(), ex);
            }

            idempotencyService.concludeTransaction(idempotency);

            return transacao;

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }

    }

    @Override
    public ContaCorrente saldo(int agencia, long codigoContaCorrente) throws RemoteException {
        return clienteService.saldo(agencia, codigoContaCorrente);
    }

    @Override
    public List<Transacao> extrato(int agencia, long codigoContaCorrente) throws RemoteException {
        return transacaoService.extrato(agencia, codigoContaCorrente);
    }

}
