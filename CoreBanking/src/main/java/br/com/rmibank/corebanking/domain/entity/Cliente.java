package main.java.br.com.rmibank.corebanking.domain.entity;

import java.util.List;

import main.java.br.com.rmibank.corebanking.domain.entity.aggregate.ContaCorrente;

public class Cliente {

    private long cpf;

    private String nome;

    List<ContaCorrente> contasCorrentes;

    public Cliente(long cpf, String nome) {
        this.cpf = cpf;
        this.nome = nome;
    }

    public long getCpf() {
        return cpf;
    }

    public String getNome() {
        return nome;
    }

    public List<ContaCorrente> getContasCorrentes() {
        return this.contasCorrentes;
    }

    public void addContaCorrente(ContaCorrente contaCorrente) {
        contasCorrentes.add(contaCorrente);
    }

}
