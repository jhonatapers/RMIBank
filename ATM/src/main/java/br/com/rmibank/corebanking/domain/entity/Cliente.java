package main.java.br.com.rmibank.corebanking.domain.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import main.java.br.com.rmibank.corebanking.domain.entity.aggregate.ContaCorrente;

public class Cliente implements Serializable {

    private long cpf;

    private String nome;

    private List<ContaCorrente> contasCorrentes;

    public Cliente(long cpf, String nome) {
        this.cpf = cpf;
        this.nome = nome;
        this.contasCorrentes = new ArrayList<ContaCorrente>();
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
