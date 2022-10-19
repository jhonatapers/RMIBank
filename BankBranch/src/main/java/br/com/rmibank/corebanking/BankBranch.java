package main.java.br.com.rmibank.corebanking;

import java.rmi.Naming;

import main.java.br.com.rmibank.corebanking.application.view.MenuViewAgencia;
import main.java.br.com.rmibank.corebanking.domain.controller.IAgenciaController;
import main.java.br.com.rmibank.corebanking.domain.controller.IIdempotencyController;

public class BankBranch {
    public static void main(String[] args) throws Exception {

        IAgenciaController agenciaController = (IAgenciaController) Naming
                .lookup("rmi://localhost:1099/AgenciaController");

        IIdempotencyController idempotencyController = (IIdempotencyController) Naming
                .lookup("rmi://localhost:1099/IdempotencyController");

        MenuViewAgencia menu = new MenuViewAgencia(agenciaController, idempotencyController);

        menu.start();

    }
}