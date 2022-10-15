package main.java.br.com.rmibank.corebanking.application.view;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Scanner;

import main.java.br.com.rmibank.corebanking.domain.dto.Comprovante;

public class TransacaoView {

    private static DecimalFormat format = new DecimalFormat("##.00");

    public static BigDecimal montaValor(Scanner sc) {

        System.out.println("\r\nInforme Valor:");
        return new BigDecimal(sc.nextLine());

    }

    public static void viewComprovante(Comprovante comprovante) {

        System.out.println(" ------------------ ");
        System.out.println(" OPERACAO : " + comprovante.getOperacao().toString());
        System.out.println(" VALOR    : " + format.format(comprovante.getValor()));
        System.out.println(" ------------------ ");

    }

}
