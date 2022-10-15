package main.java.br.com.rmibank.corebanking.application.view;

import java.util.Scanner;

import main.java.br.com.rmibank.corebanking.domain.entity.Cliente;

public class ClienteView {

    public static Cliente monta(Scanner sc) {

        System.out.println("\r\nInforme CPF:");
        long cpf = Long.parseLong(sc.nextLine());

        System.out.println("\r\nInforme NOME:");
        String nome = sc.nextLine();

        return new Cliente(cpf, nome);

    }

    public static void view(Cliente cliente) {

        System.out.println(" ------------------ ");
        System.out.println(" NOME: " + cliente.getNome());
        System.out.println(" CPF : " + String.format("%011d", cliente.getCpf()));
        System.out.println(" ------------------ ");
    }

}
