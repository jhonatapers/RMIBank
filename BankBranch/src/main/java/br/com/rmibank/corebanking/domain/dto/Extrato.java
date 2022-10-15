package main.java.br.com.rmibank.corebanking.domain.dto;

import java.io.Serializable;
import java.util.List;

import main.java.br.com.rmibank.corebanking.domain.entity.Transacao;

public class Extrato implements Serializable {
    
    public List<Transacao> trasacoes;

}
