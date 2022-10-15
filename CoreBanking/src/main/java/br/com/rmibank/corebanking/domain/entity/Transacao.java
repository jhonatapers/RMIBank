package main.java.br.com.rmibank.corebanking.domain.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import main.java.br.com.rmibank.corebanking.domain.dto.OperacaoEnum;

public class Transacao implements Serializable {

    private long codigoContaCorrente;

    private int agencia;

    private BigDecimal valor;

    private OperacaoEnum operacao;

    private LocalDateTime data;

    public Transacao(int agencia, long codigoContaCorrente, BigDecimal valor, OperacaoEnum operacao) {
        this.codigoContaCorrente = codigoContaCorrente;
        this.valor = valor;
        this.operacao = operacao;
        this.data = LocalDateTime.now();
    }

    public long getCodigoContaCorrente() {
        return codigoContaCorrente;
    }

    public int getAgencia() {
        return agencia;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public OperacaoEnum getOperacao() {
        return operacao;
    }

    public LocalDateTime getData() {
        return data;
    }

}
