package main.java.br.com.rmibank.corebanking.domain.service.impl;

import java.util.List;

import main.java.br.com.rmibank.corebanking.domain.entity.Transacao;
import main.java.br.com.rmibank.corebanking.domain.repository.ITransacaoRepository;
import main.java.br.com.rmibank.corebanking.domain.service.ITransacaoService;

public class TransacaoService implements ITransacaoService {

    ITransacaoRepository transacaoRepository;

    public TransacaoService(ITransacaoRepository transacaoRepository) {
        this.transacaoRepository = transacaoRepository;
    }

    @Override
    public void efetuaTransacao(Transacao transacao) {
        transacaoRepository.save(transacao);
    }

    @Override
    public List<Transacao> extrato(int agencia, long codigoContaCorrente) {
        return transacaoRepository.findByContaCorrente(agencia, codigoContaCorrente);
    }

}
