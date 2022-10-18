package main.java.br.com.rmibank.corebanking;

import java.rmi.Naming;

import main.java.br.com.rmibank.corebanking.application.view.MenuViewAgencia;
import main.java.br.com.rmibank.corebanking.domain.controller.IAgenciaController;

public class BankBranch {
    public static void main(String[] args) throws Exception {

        IAgenciaController agenciaController = (IAgenciaController) Naming
                .lookup("rmi://localhost:1099/AgenciaController");

        MenuViewAgencia menu = new MenuViewAgencia(agenciaController);

        menu.start();

    }
}