package main.java.br.com.rmibank.corebanking.application.view;

import java.math.BigDecimal;
import java.util.Scanner;

import main.java.br.com.rmibank.corebanking.domain.controller.IAgenciaController;
import main.java.br.com.rmibank.corebanking.domain.controller.IIdempotencyController;
import main.java.br.com.rmibank.corebanking.domain.entity.Cliente;
import main.java.br.com.rmibank.corebanking.domain.entity.aggregate.ContaCorrente;

public class MenuViewAgencia extends Thread {

    private Scanner sc = new Scanner(System.in);

    private IAgenciaController agenciaController;

    private IIdempotencyController idempotencyController;

    public MenuViewAgencia(IAgenciaController agenciaController, IIdempotencyController idempotencyController) {
        this.agenciaController = agenciaController;
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
                    System.out.println(" ---------------ACOES AGENCIA-------------- ");
                    System.out.println(" ------------------------------------------ ");
                    System.out.println("| 1 - cadastrarCliente                      |");
                    System.out.println("| 2 - consultarContasCorrentes              |");
                    System.out.println("| 3 - aberturaContaCorrente                 |");
                    System.out.println("| 4 - fechamentoContaCorrente               |");
                    System.out.println("| 5 - saque                                 |");
                    System.out.println("| 6 - deposito                              |");
                    System.out.println("| 7 - saldo                                 |");
                    System.out.println("| 8 - extrato                               |");
                    System.out.println(" ------------------------------------------ ");
                    System.out.println(" ------------------------------------------ \r\n\r\n\r\n");
                    break;
                case "clear":
                    clearScreen();
                    break;
                case "1":
                    try {
                        System.out.println("--------CADASTRO DE CLIENTE---------");

                        int idempotency = idempotencyController.newIdempotency();
                        agenciaController.cadastroCliente(idempotency,
                                ClienteView.monta(idempotency, sc));

                        System.out.println("------FIM CADASTRO DE CLIENTE-------\n\r\n\r");

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "2":
                    try {

                        System.out.println("--------CONSULTA DE CONTA CORRENTE---------");

                        agenciaController.consultaContasCorrentes(ClienteView.cpf(sc))
                                .forEach(cc -> {
                                    ContaCorrenteView.view(cc);
                                });

                        System.out.println("--------CONSULTA DE CONTA CORRENTE---------\n\r\n\r");

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "3":
                    try {

                        System.out.println("--------ABERTURA CONTA CORRENTE---------");

                        int idempotency = idempotencyController.newIdempotency();

                        agenciaController.aberturaContaCorrente(idempotency,
                                ClienteView.cpf(sc),
                                ContaCorrenteView.monta(idempotency, sc));

                        System.out.println("--------CONTA CORRENTE ABERTA---------\n\r\n\r");

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "4":

                    try {
                        System.out.println("--------FECHAMENTO CONTA CORRENTE---------");

                        int idempotency = idempotencyController.newIdempotency();

                        ContaCorrente cc = ContaCorrenteView.monta(idempotency, sc);
                        agenciaController.fechamentoContaCorrente(idempotency,
                                cc.getAgencia(), cc.getCodigoContaCorrente());

                        System.out.println("--------CONTA CORRENTE ENCERRADA---------\n\r\n\r");

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "5":
                    try {

                        System.out.println("--------SAQUE---------");

                        int idempotency = idempotencyController.newIdempotency();

                        ContaCorrente cc = ContaCorrenteView.monta(idempotency, sc);
                        TransacaoView
                                .view(agenciaController.saque(idempotency, cc.getAgencia(),
                                        cc.getCodigoContaCorrente(),
                                        TransacaoView.montaValor(sc)));

                        System.out.println("--------SAQUE---------\r\n\n\r");

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "6":
                    try {

                        System.out.println("--------DEPOSITO---------");

                        int idempotency = idempotencyController.newIdempotency();

                        ContaCorrente cc = ContaCorrenteView.monta(idempotency, sc);
                        TransacaoView
                                .view(
                                        agenciaController.deposito(idempotencyController.newIdempotency(),
                                                cc.getAgencia(), cc.getCodigoContaCorrente(),
                                                TransacaoView.montaValor(sc)));

                        System.out.println("--------DEPOSITO---------\r\n\r\n");

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "7":
                    try {

                        System.out.println("--------SALDO---------");

                        int idempotency = idempotencyController.newIdempotency();

                        ContaCorrente cc = ContaCorrenteView.monta(idempotency, sc);
                        ContaCorrenteView.view(agenciaController.saldo(cc.getAgencia(), cc.getCodigoContaCorrente()));

                        System.out.println("--------SALDO---------\r\n\r\n");

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "8":
                    try {

                        System.out.println("--------EXTRATO---------");

                        int idempotency = idempotencyController.newIdempotency();

                        ContaCorrente cc = ContaCorrenteView.monta(idempotency, sc);
                        agenciaController.extrato(cc.getAgencia(), cc.getCodigoContaCorrente())
                                .forEach(transacao -> {
                                    TransacaoView.view(transacao);
                                });

                        System.out.println("--------EXTRATO---------\r\n\r\n");

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "script":
                    try {

                        System.out.println("--------SCRIPT DE ERROS---------");

                        int idempotency = 0;

                        try {

                            System.out.println("1- IDEMPOTENCY NAO GERADO PELO SERVIDOR");
                            idempotency = 999999;

                            agenciaController.aberturaContaCorrente(idempotency, 2574855035L,
                                    new ContaCorrente(idempotency, 123L, 1, new BigDecimal(0.0d)));

                            System.out.println("---------------------------------");

                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }

                        System.out.println("2- REUTILIAR MESMO IDEMPOTENCY cadastro de cliente");
                        idempotency = idempotencyController.newIdempotency();

                        try {
                            agenciaController.cadastroCliente(idempotency, new Cliente(idempotency, 2574855035L, "J"));
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }

                        try {
                            agenciaController.cadastroCliente(idempotency, new Cliente(idempotency, 2574855035L, "JP"));
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }

                        System.out.println("3- REUTILIAR MESMO IDEMPOTENCY abertura de conta");
                        idempotency = idempotencyController.newIdempotency();

                        try {
                            agenciaController.aberturaContaCorrente(idempotency, 2574855035L,
                                    new ContaCorrente(idempotency, 123L, 1, new BigDecimal(0.0d)));
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }

                        try {
                            agenciaController.aberturaContaCorrente(idempotency, 25748550368L,
                                    new ContaCorrente(idempotency, 123L, 1, new BigDecimal(0.0d)));

                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }

                        System.out.println("4- REUTILIAR MESMO IDEMPOTENCY deposito / saque");
                        idempotency = idempotencyController.newIdempotency();

                        try {
                            agenciaController.deposito(idempotency, 1, 123L, new BigDecimal(1000.00));
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }

                        try {
                            agenciaController.saque(idempotency, 1, 123L, new BigDecimal(1000.00));
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }

                        System.out.println("---------------------------------");

                        System.out.println("--------SCRIPT DE ERROS---------\r\n\r\n");

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
