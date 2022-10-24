package main.java.br.com.rmibank.corebanking.adapters.database.repository.impl;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Semaphore;

import main.java.br.com.rmibank.corebanking.adapters.database.RmiBankSchema;
import main.java.br.com.rmibank.corebanking.domain.entity.Cliente;
import main.java.br.com.rmibank.corebanking.domain.entity.aggregate.ContaCorrente;
import main.java.br.com.rmibank.corebanking.domain.repository.IClienteRepository;

public class ClienteRepositoryLocalJavaClass implements IClienteRepository {

    private RmiBankSchema dataBase;

    private Semaphore mutex;

    public ClienteRepositoryLocalJavaClass(RmiBankSchema dataBase) {
        this.dataBase = dataBase;
        this.mutex = new Semaphore(1);
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

        try {
            mutex.acquire();

            dataBase.clientes.add(cliente);

        } catch (Exception e) {
            throw new RuntimeException("Erro interno 500");
        } finally {
            mutex.release();
        }

    }

    public void update(Cliente cliente) {

        try {
            mutex.acquire();

            dataBase.clientes.removeIf(c -> c.getCpf() == cliente.getCpf());
            dataBase.clientes.add(cliente);

        } catch (Exception e) {
            throw new RuntimeException("Erro interno 500");
        } finally {
            mutex.release();
        }

    }

    @Override
    public void newContaCorrente(long cpfCliente, ContaCorrente contaCorrente) {

        final long codigoContaCorrente = contaCorrente.getCodigoContaCorrente();
        final int agencia = contaCorrente.getAgencia();

        try {
            mutex.acquire();

            dataBase.clientes
                    .stream()
                    .filter(c -> c.getContasCorrentes()
                            .stream()
                            .filter(cc -> cc.getAgencia() == agencia
                                    && cc.getCodigoContaCorrente() == codigoContaCorrente)
                            .findFirst()
                            .isPresent())
                    .findFirst()
                    .ifPresent(c -> {
                        throw new RuntimeException("Conta e Agencia já existem!");
                    });

            dataBase.clientes
                    .stream()
                    .filter(c -> c.getCpf() == cpfCliente)
                    .findFirst()
                    .orElseThrow(() -> {
                        throw new RuntimeException("Cliente não cadastrado");
                    })
                    .addContaCorrente(contaCorrente);

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro interno 500");
        } finally {
            mutex.release();
        }

    }

    @Override
    public void encerraContaCorrente(int agencia, long codigoContaCorrente) {

        try {
            mutex.acquire();

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

        } catch (Exception e) {
            throw new RuntimeException("Erro interno 500");
        } finally {
            mutex.release();
        }

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
