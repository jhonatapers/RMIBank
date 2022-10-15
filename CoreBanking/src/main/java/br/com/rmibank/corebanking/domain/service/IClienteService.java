package main.java.br.com.rmibank.corebanking.domain.service;

import java.math.BigDecimal;
import java.util.List;

import main.java.br.com.rmibank.corebanking.domain.entity.Cliente;
import main.java.br.com.rmibank.corebanking.domain.entity.Transacao;
import main.java.br.com.rmibank.corebanking.domain.entity.aggregate.ContaCorrente;

public interface IClienteService {

    public void cadastro(Cliente cliente);

    public void aberturaContaCorrente(long cpfCliente, ContaCorrente contaCorrente);

    public void enccerraContaCorrente(int agencia, long codigoContaCorrente);

    public Transacao saque(int agencia, long codigoContaCorrente, BigDecimal valor);

    public Transacao deposito(int agencia, long codigoContaCorrente, BigDecimal valor);

    public ContaCorrente saldo(int agencia, long codigoContaCorrente);

    public List<ContaCorrente> contas(long cpf);

}
