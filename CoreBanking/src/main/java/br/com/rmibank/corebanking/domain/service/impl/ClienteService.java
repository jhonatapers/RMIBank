package main.java.br.com.rmibank.corebanking.domain.service.impl;

import java.math.BigDecimal;
import java.util.List;

import main.java.br.com.rmibank.corebanking.domain.dto.OperacaoEnum;
import main.java.br.com.rmibank.corebanking.domain.entity.Cliente;
import main.java.br.com.rmibank.corebanking.domain.entity.Transacao;
import main.java.br.com.rmibank.corebanking.domain.entity.aggregate.ContaCorrente;
import main.java.br.com.rmibank.corebanking.domain.exception.IdempotencyException;
import main.java.br.com.rmibank.corebanking.domain.repository.IClienteRepository;
import main.java.br.com.rmibank.corebanking.domain.service.IClienteService;
import main.java.br.com.rmibank.corebanking.domain.service.IIdempotencyService;
import main.java.br.com.rmibank.corebanking.util.ValidaCpf;

public class ClienteService implements IClienteService {

    IClienteRepository clienteRepository;

    IIdempotencyService idempotencyService;

    public ClienteService(IClienteRepository clienteRepository, IIdempotencyService idempotencyService) {
        this.clienteRepository = clienteRepository;
        this.idempotencyService = idempotencyService;
    }

    @Override
    public void cadastro(int idempotency, Cliente cliente) {

        if (!idempotencyService.verifyIdempotency(idempotency))
            throw new RuntimeException("Idempotency incorreto");

        if (ValidaCpf.isCPF(cliente.getCpf())) {

            if (idempotencyService.existsTransaction(idempotency))
                throw new RuntimeException("idempotency já utilizado (idempotency error)");

            clienteRepository.save(cliente);

            idempotencyService.concludeTransaction(idempotency);

        } else {
            throw new RuntimeException("CPF Informado inválido!");
        }
    }

    @Override
    public void aberturaContaCorrente(int idempotency, long cpfCliente, ContaCorrente contaCorrente) {

        if (!idempotencyService.verifyIdempotency(idempotency))
            throw new RuntimeException("Idempotency incorreto");

        if (idempotencyService.existsTransaction(idempotency))
            throw new RuntimeException("idempotency já utilizado (idempotency error)");

        try {
            clienteRepository.newContaCorrente(cpfCliente, contaCorrente);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            idempotencyService.concludeTransaction(idempotency);
        }

    }

    @Override
    public void enccerraContaCorrente(int idempotency, int agencia, long codigoContaCorrente) {

        if (!idempotencyService.verifyIdempotency(idempotency))
            throw new RuntimeException("Idempotency incorreto");

        if (idempotencyService.existsTransaction(idempotency))
            throw new RuntimeException("idempotency já utilizado (idempotency error)");

        clienteRepository.encerraContaCorrente(agencia, codigoContaCorrente);

        idempotencyService.concludeTransaction(idempotency);
    }

    @Override
    public Transacao saque(int idempotency, int agencia, long codigoContaCorrente, BigDecimal valor) {

        if (!idempotencyService.verifyIdempotency(idempotency))
            throw new RuntimeException("Idempotency incorreto");

        ContaCorrente contaCorrente = clienteRepository.getContaCorrente(agencia, codigoContaCorrente);
        if (contaCorrente.getSaldo().compareTo(valor) == -1) {
            idempotencyService.concludeTransaction(idempotency);
            throw new RuntimeException("Saldo insuficiente");
        }

        try {

            if (idempotencyService.existsTransaction(idempotency))
                throw new RuntimeException("idempotency já utilizado (idempotency error)");

            contaCorrente.setSaldo(contaCorrente.getSaldo().subtract(valor));

            Transacao transacao = new Transacao(idempotency, agencia, codigoContaCorrente, valor, OperacaoEnum.SAQUE);

            return transacao;

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro durante operacao de saque!");
        }

    }

    @Override
    public Transacao deposito(int idempotency, int agencia, long codigoContaCorrente, BigDecimal valor) {

        if (!idempotencyService.verifyIdempotency(idempotency))
            throw new RuntimeException("Idempotency incorreto");

        ContaCorrente contaCorrente = clienteRepository.getContaCorrente(agencia, codigoContaCorrente);
        if (new BigDecimal(0.01).compareTo(valor) == 1) {
            idempotencyService.concludeTransaction(idempotency);
            throw new RuntimeException("Valor inválido");
        }

        try {

            if (idempotencyService.existsTransaction(idempotency))
                throw new RuntimeException("idempotency já utilizado (idempotency error)");

            contaCorrente.setSaldo(contaCorrente.getSaldo().add(valor));

            Transacao transacao = new Transacao(idempotency, agencia, codigoContaCorrente, valor,
                    OperacaoEnum.DEPOSITO);

            return transacao;

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro durante operacao de deposito!");
        }

    }

    @Override
    public ContaCorrente saldo(int agencia, long codigoContaCorrente) {
        return clienteRepository.getContaCorrente(agencia, codigoContaCorrente);
    }

    @Override
    public List<ContaCorrente> contas(long cpfCliente) {
        return clienteRepository.getAllContarCorrentes(cpfCliente);
    }

}
