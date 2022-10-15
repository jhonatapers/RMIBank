package main.java.br.com.rmibank.corebanking.domain.service.impl;

import java.math.BigDecimal;
import java.util.List;

import main.java.br.com.rmibank.corebanking.domain.dto.OperacaoEnum;
import main.java.br.com.rmibank.corebanking.domain.entity.Cliente;
import main.java.br.com.rmibank.corebanking.domain.entity.Transacao;
import main.java.br.com.rmibank.corebanking.domain.entity.aggregate.ContaCorrente;
import main.java.br.com.rmibank.corebanking.domain.repository.IClienteRepository;
import main.java.br.com.rmibank.corebanking.domain.service.IClienteService;
import main.java.br.com.rmibank.corebanking.util.ValidaCpf;

public class ClienteService implements IClienteService {

    IClienteRepository clienteRepository;

    public ClienteService(IClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public void cadastro(Cliente cliente) {
        if (ValidaCpf.isCPF(cliente.getCpf()))
            clienteRepository.save(cliente);
        else
            throw new RuntimeException("CPF Informado inválido!");
    }

    @Override
    public void aberturaContaCorrente(long cpfCliente, ContaCorrente contaCorrente) {
        clienteRepository.newContaCorrente(cpfCliente, contaCorrente);
    }

    @Override
    public void enccerraContaCorrente(int agencia, long codigoContaCorrente) {
        clienteRepository.encerraContaCorrente(agencia, codigoContaCorrente);
    }

    @Override
    public Transacao saque(int agencia, long codigoContaCorrente, BigDecimal valor) {

        ContaCorrente contaCorrente = clienteRepository.getContaCorrente(agencia, codigoContaCorrente);
        if (contaCorrente.getSaldo().compareTo(valor) == -1)
            throw new RuntimeException("Saldo insuficiente");

        contaCorrente.getSaldo().subtract(valor);

        return new Transacao(agencia, codigoContaCorrente, valor, OperacaoEnum.SAQUE);

    }

    @Override
    public Transacao deposito(int agencia, long codigoContaCorrente, BigDecimal valor) {

        ContaCorrente contaCorrente = clienteRepository.getContaCorrente(agencia, codigoContaCorrente);
        if (contaCorrente.getSaldo().compareTo(valor) == 1)
            throw new RuntimeException("Valor inválido");

        contaCorrente.getSaldo().add(valor);

        return new Transacao(agencia, codigoContaCorrente, valor, OperacaoEnum.DEPOSITO);

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
