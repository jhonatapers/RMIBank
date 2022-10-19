package main.java.br.com.rmibank.corebanking.adapters.database;

import java.util.ArrayList;
import java.util.List;

import main.java.br.com.rmibank.corebanking.domain.entity.Cliente;
import main.java.br.com.rmibank.corebanking.domain.entity.Transacao;

public class RmiBankSchema {

    public List<Cliente> clientes;

    public List<Transacao> transacoes;

    public List<Integer> transactions;

    public List<Integer> generatedIdempotencies;

    public RmiBankSchema() {
        this.clientes = new ArrayList<Cliente>();
        this.transacoes = new ArrayList<Transacao>();
        this.transactions = new ArrayList<Integer>();
        this.generatedIdempotencies = new ArrayList<>();
    }

}
