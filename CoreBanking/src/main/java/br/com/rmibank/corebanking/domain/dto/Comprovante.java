package main.java.br.com.rmibank.corebanking.domain.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class Comprovante implements Serializable {

    private OperacaoEnum operacao;

    private BigDecimal valor;

    public Comprovante(OperacaoEnum operacao, BigDecimal valor) {
        this.operacao = operacao;
        this.valor = valor;
    }

    public OperacaoEnum getOperacao() {
        return operacao;
    }

    public BigDecimal getValor() {
        return valor;
    }

}
