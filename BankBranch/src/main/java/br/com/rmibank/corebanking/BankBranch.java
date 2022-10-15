package main.java.br.com.rmibank.corebanking;

import java.math.BigDecimal;
import java.rmi.Naming;

import main.java.br.com.rmibank.corebanking.domain.controller.IAgenciaController;
import main.java.br.com.rmibank.corebanking.domain.entity.Cliente;
import main.java.br.com.rmibank.corebanking.domain.entity.aggregate.ContaCorrente;

public class BankBranch {
    public static void main(String[] args) throws Exception {

        IAgenciaController agenciaController = (IAgenciaController) Naming
                .lookup("rmi://localhost:1099/AgenciaController");

        try {
            agenciaController.consultaContasCorrentes(2574855035L);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            agenciaController.cadastroCliente(new Cliente(2574855035L, "Jhonata Peres"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            agenciaController.aberturaContaCorrente(2574855035L, new ContaCorrente(3143145, 1, new BigDecimal(0.0)));

            agenciaController.aberturaContaCorrente(2574855035L, new ContaCorrente(3143146, 1, new BigDecimal(0.0)));

            agenciaController.aberturaContaCorrente(2574855035L, new ContaCorrente(3143147, 1, new BigDecimal(0.0)));

            agenciaController.aberturaContaCorrente(2574855035L, new ContaCorrente(3143148, 1, new BigDecimal(0.0)));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        agenciaController.consultaContasCorrentes(2574855035L).forEach(conta -> {
            System.out.println(conta.getCodigoContaCorrente() + "-" + conta.getAgencia());
        });

    }
}