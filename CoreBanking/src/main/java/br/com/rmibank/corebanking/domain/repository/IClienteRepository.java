package main.java.br.com.rmibank.corebanking.domain.repository;

import java.util.List;
import java.util.Optional;

import main.java.br.com.rmibank.corebanking.domain.entity.Cliente;
import main.java.br.com.rmibank.corebanking.domain.entity.aggregate.ContaCorrente;

public interface IClienteRepository {

    public Optional<Cliente> findByCpf(long cpf);

    public void save(Cliente cliente);

    public void newContaCorrente(long cpfCliente, ContaCorrente contaCorrente);

    public void encerraContaCorrente(int agencia, long codigoContaCorrente);

    public List<ContaCorrente> getAllContarCorrentes(long cpf);

}
