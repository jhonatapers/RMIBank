package main.java.br.com.rmibank.corebanking.domain.repository;

import java.util.List;

import main.java.br.com.rmibank.corebanking.domain.entity.Transacao;

public interface ITransacaoRepository {

    public List<Transacao> findByContaCorrente(int agencia, long codigoContaCorrente);

    public void save(Transacao transacao);

}
