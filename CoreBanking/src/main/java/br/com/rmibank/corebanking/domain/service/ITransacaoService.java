package main.java.br.com.rmibank.corebanking.domain.service;

import java.util.List;

import main.java.br.com.rmibank.corebanking.domain.entity.Transacao;

public interface ITransacaoService {

    public void efetuaTransacao(Transacao transacao);

    public List<Transacao> extrato(int agencia, long codigoContaCorrente);

}
