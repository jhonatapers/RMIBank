package main.java.br.com.rmibank.corebanking.domain.dto;

import java.math.BigDecimal;

public class Comprovante {

    private long id;

    private OperacaoEnum operacao;

    private BigDecimal valor;

    public Comprovante(long id, OperacaoEnum operacao, BigDecimal valor) {
        this.id = id;
        this.operacao = operacao;
        this.valor = valor;
    }

    public long getId() {
        return id;
    }

    public OperacaoEnum getOperacao() {
        return operacao;
    }

    public void setOperacao(OperacaoEnum operacao) {
        this.operacao = operacao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

}
