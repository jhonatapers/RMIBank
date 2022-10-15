package main.java.br.com.rmibank.corebanking.domain.service.impl;

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

}
