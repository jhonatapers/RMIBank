package main.java.br.com.rmibank.corebanking.application.view;

import java.util.Scanner;

import main.java.br.com.rmibank.corebanking.domain.controller.IAgenciaController;
import main.java.br.com.rmibank.corebanking.domain.entity.aggregate.ContaCorrente;

public class MenuView extends Thread {

    private Scanner sc = new Scanner(System.in);

    private IAgenciaController agenciaController;

    public MenuView(IAgenciaController agenciaController) {
        this.agenciaController = agenciaController;
    }

    @Override
    public void run() {

        printLogo();

        while (true) {

            String in = sc.nextLine();

            switch (in) {
                case "--help":
                    System.out.println(" ------------------------------------------ ");
                    System.out.println(" ---------------ACOES AGENCIA-------------- ");
                    System.out.println(" ------------------------------------------ ");
                    System.out.println("| 1 - cadastrarCliente                      |");
                    System.out.println("| 2 - consultarContasCorrentes              |");
                    System.out.println("| 3 - aberturaContaCorrente                 |");
                    System.out.println("| 4 - fechamentoContaCorrente               |");
                    System.out.println("| 5 - saque                                 |");
                    System.out.println("| 6 - deposito                              |");
                    System.out.println("| 7 - saldo                                 |");
                    System.out.println(" ------------------------------------------ ");
                    System.out.println(" ------------------------------------------ \r\n\r\n\r\n");
                    break;
                case "clear":
                    clearScreen();
                    break;
                case "1":
                    try {
                        System.out.println("\r\nFormulario de cadastro de cliente:\r\n");
                        agenciaController.cadastroCliente(ClienteView.monta(sc));
                        System.out.println("Cadastro Efetuado!");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "2":
                    try {
                        agenciaController.consultaContasCorrentes(ContaCorrenteView.consulta(sc))
                                .forEach(cc -> {
                                    ContaCorrenteView.view(cc);
                                });
                        ;
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "3":
                    try {
                        agenciaController.aberturaContaCorrente(ContaCorrenteView.consulta(sc),
                                ContaCorrenteView.monta(sc));

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "4":

                    try {
                        ContaCorrente cc = ContaCorrenteView.monta(sc);
                        agenciaController.fechamentoContaCorrente(cc.getAgencia(), cc.getCodigoContaCorrente());
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "5":
                    try {
                        ContaCorrente cc = ContaCorrenteView.monta(sc);
                        TransacaoView
                                .viewComprovante(agenciaController.saque(cc.getAgencia(), cc.getCodigoContaCorrente(),
                                        TransacaoView.montaValor(sc)));
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "6":
                    try {
                        ContaCorrente cc = ContaCorrenteView.monta(sc);
                        TransacaoView
                                .viewComprovante(
                                        agenciaController.deposito(cc.getAgencia(), cc.getCodigoContaCorrente(),
                                                TransacaoView.montaValor(sc)));
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
