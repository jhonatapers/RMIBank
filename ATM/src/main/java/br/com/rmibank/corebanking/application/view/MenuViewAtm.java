package main.java.br.com.rmibank.corebanking.application.view;

import java.util.Scanner;

import main.java.br.com.rmibank.corebanking.domain.controller.IAtmController;
import main.java.br.com.rmibank.corebanking.domain.controller.IIdempotencyController;
import main.java.br.com.rmibank.corebanking.domain.entity.aggregate.ContaCorrente;

public class MenuViewAtm extends Thread {

    private Scanner sc = new Scanner(System.in);

    private IAtmController atmController;

    private IIdempotencyController idempotencyController;

    public MenuViewAtm(IAtmController atmController, IIdempotencyController idempotencyController) {
        this.atmController = atmController;
        this.idempotencyController = idempotencyController;
    }

    @Override
    public void run() {

        printLogo();

        while (true) {

            String in = sc.nextLine();

            switch (in) {
                case "--help":
                    System.out.println(" ------------------------------------------ ");
                    System.out.println(" ---------------  ACOES ATM  -------------- ");
                    System.out.println(" ------------------------------------------ ");
                    System.out.println("| 1 - saque                                 |");
                    System.out.println("| 2 - deposito                              |");
                    System.out.println("| 3 - saldo                                 |");
                    System.out.println("| 4 - extrato                               |");
                    System.out.println(" ------------------------------------------ ");
                    System.out.println(" ------------------------------------------ \r\n\r\n\r\n");
                    break;
                case "clear":
                    clearScreen();
                    break;
                case "1":
                    try {

                        System.out.println("--------SAQUE---------");

                        int idempotency = idempotencyController.newIdempotency();

                        ContaCorrente cc = ContaCorrenteView.monta(idempotency, sc);
                        TransacaoView
                                .view(atmController.saque(idempotency, cc.getAgencia(),
                                        cc.getCodigoContaCorrente(),
                                        TransacaoView.montaValor(sc)));

                        System.out.println("--------SAQUE---------\r\n\n\r");

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "2":
                    try {

                        System.out.println("--------DEPOSITO---------");

                        int idempotency = idempotencyController.newIdempotency();

                        ContaCorrente cc = ContaCorrenteView.monta(idempotency, sc);
                        TransacaoView
                                .view(
                                        atmController.deposito(idempotencyController.newIdempotency(),
                                                cc.getAgencia(), cc.getCodigoContaCorrente(),
                                                TransacaoView.montaValor(sc)));

                        System.out.println("--------DEPOSITO---------\r\n\r\n");

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "3":
                    try {

                        System.out.println("--------SALDO---------");

                        int idempotency = idempotencyController.newIdempotency();

                        ContaCorrente cc = ContaCorrenteView.monta(idempotency, sc);
                        ContaCorrenteView.view(atmController.saldo(cc.getAgencia(), cc.getCodigoContaCorrente()));

                        System.out.println("--------SALDO---------\r\n\r\n");

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "4":
                    try {

                        System.out.println("--------EXTRATO---------");

                        int idempotency = idempotencyController.newIdempotency();

                        ContaCorrente cc = ContaCorrenteView.monta(idempotency, sc);
                        atmController.extrato(cc.getAgencia(), cc.getCodigoContaCorrente())
                                .forEach(transacao -> {
                                    TransacaoView.view(transacao);
                                });

                        System.out.println("--------EXTRATO---------\r\n\r\n");

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                default:
                    System.out.println("Comando nao reconhecido");
                    break;
            }

        }

    }

    private static void printLogo() {
        System.out.println("  _____  __  __ _____ ____              _    ");
        System.out.println(" |  __ \\|  \\/  |_   _|  _ \\            | |   ");
        System.out.println(" | |__) | \\  / | | | | |_) | __ _ _ __ | | __");
        System.out.println(" |  _  /| |\\/| | | | |  _ < / _` | '_ \\| |/ /");
        System.out.println(" | | \\ \\| |  | |_| |_| |_) | (_| | | | |   < ");
        System.out.println(" |_|  \\_\\_|  |_|_____|____/ \\__,_|_| |_|_|\\_\\\r\n\r\n\r\n\r\n");
    }

    public static void clearScreen() {
        for (int i = 0; i < 100; i++) {
            System.out.println("");
        }

        printLogo();
    }
}
