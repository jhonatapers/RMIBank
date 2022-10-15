package main.java.br.com.rmibank.corebanking.domain.service;

import java.math.BigDecimal;
import java.util.List;

import main.java.br.com.rmibank.corebanking.domain.dto.Comprovante;
import main.java.br.com.rmibank.corebanking.domain.entity.Cliente;
import main.java.br.com.rmibank.corebanking.domain.entity.aggregate.ContaCorrente;

public interface IClienteService {

    public void cadastro(Cliente cliente);

    public void abertura(long cpfCliente, ContaCorrente contaCorrente);

    public void enccerraContaCorrente(int agencia, long codigoContaCorrente);

    public Comprovante saque(int agencia, long codigoContaCorrente, BigDecimal valor);

    public Comprovante deposito(int agencia, long codigoContaCorrente, BigDecimal valor);

    public Comprovante saldo(int agencia, long codigoContaCorrente);

    public List<ContaCorrente> contas(long cpf);

}
