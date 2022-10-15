package main.java.br.com.rmibank.corebanking.application.view;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Scanner;

import main.java.br.com.rmibank.corebanking.domain.entity.Transacao;

public class TransacaoView {

    private static DecimalFormat format = new DecimalFormat("##.00");

    public static BigDecimal montaValor(Scanner sc) {

        System.out.println("\r\nInforme Valor:");
        return new BigDecimal(sc.nextLine());

    }

    public static void view(Transacao transacao) {

        System.out.println(" ------------------ ");
        System.out.println(" OPERACAO : " + transacao.getOperacao().toString());
        System.out.println(" VALOR    : " + format.format(transacao.getValor()));
        System.out.println(" DATA     : " + transacao.getData());
        System.out.println(" ------------------ ");

    }

}
