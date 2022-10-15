package main.java.br.com.rmibank.corebanking.adapters.database.repository.impl;

import java.util.List;

import main.java.br.com.rmibank.corebanking.adapters.database.RmiBankSchema;
import main.java.br.com.rmibank.corebanking.domain.entity.Transacao;
import main.java.br.com.rmibank.corebanking.domain.repository.ITransacaoRepository;

public class TransacaoRepositoryLocalJavaClass implements ITransacaoRepository {

    private RmiBankSchema dataBase;

    public TransacaoRepositoryLocalJavaClass(RmiBankSchema dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public List<Transacao> findByContaCorrente(int agencia, long codigoContaCorrente) {
        return dataBase.transacoes
                .stream()
                .filter(t -> t.getAgencia() == agencia && t.getCodigoContaCorrente() == codigoContaCorrente)
                .toList();
    }

    @Override
    public void save(Transacao transacao) {
        dataBase.transacoes.add(transacao);

    }

}
