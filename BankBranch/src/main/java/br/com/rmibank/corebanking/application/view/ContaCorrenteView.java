package main.java.br.com.rmibank.corebanking.application.view;

import java.math.BigDecimal;
import java.util.Scanner;

import main.java.br.com.rmibank.corebanking.domain.entity.aggregate.ContaCorrente;

public class ContaCorrenteView {

    public static void view(ContaCorrente contaCorrente) {
        System.out.println(" ------------------ ");
        System.out.println(" AGENCIA: " + contaCorrente.getAgencia());
        System.out.println(" CONTA  : " + contaCorrente.getCodigoContaCorrente());
        System.out.println(" SALDO  : " + contaCorrente.getSaldo());
        System.out.println(" ------------------ ");
    }

    public static ContaCorrente monta(Scanner sc) {

        System.out.println("\r\nInforme a AGENCIA:\r\n");
        int agencia = Integer.parseInt(sc.nextLine());

        System.out.println("\r\nInforme o numero da CONTA:\r\n");
        long conta = Long.parseLong(sc.nextLine());

        return new ContaCorrente(conta, agencia, new BigDecimal(0.0d));
    }
}
