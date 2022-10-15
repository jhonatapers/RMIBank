package main.java.br.com.rmibank.corebanking.domain.service.impl;

import java.math.BigDecimal;
import java.util.List;

import main.java.br.com.rmibank.corebanking.domain.dto.Comprovante;
import main.java.br.com.rmibank.corebanking.domain.dto.OperacaoEnum;
import main.java.br.com.rmibank.corebanking.domain.entity.Cliente;
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
            throw new RuntimeException("CPF Informado invalido!");
    }

    @Override
    public void aberturaContaCorrente(long cpfCliente, ContaCorrente contaCorrente) {
        clienteRepository.newContaCorrente(cpfCliente, contaCorrente);
    }

    @Override
    public void enccerraContaCorrente(int agencia, long codigoContaCorrente) {
        clienteRepository.encerraContaCorrente(codigoContaCorrente, agencia, codigoContaCorrente);
    }

    @Override
    public Comprovante saque(int agencia, long codigoContaCorrente, BigDecimal valor) {
        return new Comprovante(OperacaoEnum.SAQUE, new BigDecimal(0.1));
    }

    @Override
    public Comprovante deposito(int agencia, long codigoContaCorrente, BigDecimal valor) {
        return new Comprovante(OperacaoEnum.DEPOSITO, new BigDecimal(0.1));
    }

    @Override
    public Comprovante saldo(int agencia, long codigoContaCorrente) {
        return new Comprovante(OperacaoEnum.SALDO, new BigDecimal(0.1));
    }

    @Override
    public List<ContaCorrente> contas(long cpfCliente) {
        return clienteRepository.getAllContarCorrentes(cpfCliente);
    }

}
