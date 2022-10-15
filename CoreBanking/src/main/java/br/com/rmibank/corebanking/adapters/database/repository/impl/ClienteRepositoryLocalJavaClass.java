package main.java.br.com.rmibank.corebanking.adapters.database.repository.impl;

import java.util.List;
import java.util.Optional;

import main.java.br.com.rmibank.corebanking.adapters.database.RmiBankSchema;
import main.java.br.com.rmibank.corebanking.domain.entity.Cliente;
import main.java.br.com.rmibank.corebanking.domain.entity.aggregate.ContaCorrente;
import main.java.br.com.rmibank.corebanking.domain.repository.IClienteRepository;

public class ClienteRepositoryLocalJavaClass implements IClienteRepository {

    private RmiBankSchema dataBase;

    public ClienteRepositoryLocalJavaClass(RmiBankSchema dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public Optional<Cliente> findByCpf(long cpf) {

        return dataBase.clientes
                .stream()
                .filter(cliente -> cliente.getCpf() == cpf)
                .findFirst();

    }

    @Override
    public void save(Cliente cliente) {

        if (dataBase.clientes.stream().filter(c -> c.getCpf() == cliente.getCpf()).findFirst().isPresent())
            throw new RuntimeException("Cliente já cadastrado");

        dataBase.clientes.add(cliente);

    }

    public void update(Cliente cliente) {

        dataBase.clientes.removeIf(c -> c.getCpf() == cliente.getCpf());
        dataBase.clientes.add(cliente);

    }

    @Override
    public void newContaCorrente(long cpfCliente, ContaCorrente contaCorrente) {

        final long codigoContaCorrente = contaCorrente.getCodigoContaCorrente();
        final int agencia = contaCorrente.getAgencia();

        dataBase.clientes
                .stream()
                .filter(c -> c.getContasCorrentes()
                        .stream()
                        .filter(cc -> {
                            return cc.getAgencia() == agencia && cc.getCodigoContaCorrente() == codigoContaCorrente;
                        })
                        .findFirst()
                        .isPresent())
                .findFirst()
                .ifPresentOrElse((value) -> {
                    throw new RuntimeException("Conta e Agencia já existem!");
                }, () -> {

                    findByCpf(cpfCliente)
                            .ifPresentOrElse((c) -> {

                                c.addContaCorrente(contaCorrente);

                                dataBase.clientes.removeIf(c2 -> c2.getCpf() == cpfCliente);

                                dataBase.clientes.add(c);

                            }, () -> {
                                new RuntimeException("Cliente não cadastrado");
                            });
                });

    }

    @Override
    public void encerraContaCorrente(int agencia, long codigoContaCorrente) {

        dataBase.clientes
                .stream()
                .filter(c -> c.getContasCorrentes()
                        .stream()
                        .filter(cc -> cc.getAgencia() == agencia
                                && cc.getCodigoContaCorrente() == codigoContaCorrente)
                        .findFirst()
                        .isPresent())
                .findFirst()
                .ifPresentOrElse((cliente) -> {
                    cliente.getContasCorrentes()
                            .removeIf(cc -> cc.getAgencia() == agencia
                                    && cc.getCodigoContaCorrente() == codigoContaCorrente);
                }, () -> {
                    throw new RuntimeException("Cliente não cadastrado!");
                });

    }

    @Override
    public ContaCorrente getContaCorrente(int agencia, long codigoContaCorrente) {

        return dataBase.clientes
                .stream()
                .filter(c -> c.getContasCorrentes()
                        .stream()
                        .filter(cc -> cc.getAgencia() == agencia
                                && cc.getCodigoContaCorrente() == codigoContaCorrente)
                        .findFirst()
                        .isPresent())
                .findFirst()
                .orElseThrow(() -> {
                    throw new RuntimeException("Conta não cadastrada!");
                })
                .getContasCorrentes()
                .stream()
                .filter(cc -> cc.getAgencia() == agencia
                        && cc.getCodigoContaCorrente() == codigoContaCorrente)
                .findFirst()
                .orElseThrow(() -> {
                    throw new RuntimeException("Conta não cadastrada!");
                });

    }

    @Override
    public List<ContaCorrente> getAllContarCorrentes(long cpfCliente) {

        return dataBase.clientes
                .stream()
                .filter(c -> c.getCpf() == cpfCliente)
                .findFirst()
                .orElseThrow(() -> {
                    throw new RuntimeException("Cliente não cadastrado!");
                }).getContasCorrentes();

    }

}
