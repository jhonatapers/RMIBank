package main.java.br.com.rmibank.corebanking.domain.service.impl;

import java.util.List;

import main.java.br.com.rmibank.corebanking.domain.entity.Transacao;
import main.java.br.com.rmibank.corebanking.domain.exception.IdempotencyException;
import main.java.br.com.rmibank.corebanking.domain.repository.ITransacaoRepository;
import main.java.br.com.rmibank.corebanking.domain.service.IIdempotencyService;
import main.java.br.com.rmibank.corebanking.domain.service.ITransacaoService;

public class TransacaoService implements ITransacaoService {

    ITransacaoRepository transacaoRepository;

    IIdempotencyService idempotencyService;

    public TransacaoService(ITransacaoRepository transacaoRepository, IIdempotencyService idempotencyService) {
        this.transacaoRepository = transacaoRepository;
        this.idempotencyService = idempotencyService;
    }

    @Override
    public void armazenaTransacao(int idempotency, Transacao transacao) {

        if (idempotencyService.existsTransaction(idempotency))
            throw new IdempotencyException("idempotency já utilizado (idempotency error)");

        try {
            transacaoRepository.save(transacao);
        } catch (Exception e) {
            throw new IdempotencyException("Erro durante armazenamento da transacao!", e);
        }

    }

    @Override
    public List<Transacao> extrato(int agencia, long codigoContaCorrente) {
        return transacaoRepository.findByContaCorrente(agencia, codigoContaCorrente);
    }

}
