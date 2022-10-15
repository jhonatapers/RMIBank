package main.java.br.com.rmibank.corebanking.domain.entity.aggregate;

import java.io.Serializable;
import java.math.BigDecimal;

public class ContaCorrente implements Serializable {

    private long codigoContaCorrente;

    private int agencia;

    private BigDecimal saldo;

    public ContaCorrente(long codigoContaCorrente, int agencia, BigDecimal saldo) {
        this.codigoContaCorrente = codigoContaCorrente;
        this.agencia = agencia;
        this.saldo = saldo;
    }

    public long getCodigoContaCorrente() {
        return codigoContaCorrente;
    }

    public void setCodigoContaCorrente(long codigoContaCorrente) {
        this.codigoContaCorrente = codigoContaCorrente;
    }

    public int getAgencia() {
        return agencia;
    }

    public void setAgencia(int agencia) {
        this.agencia = agencia;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

}
