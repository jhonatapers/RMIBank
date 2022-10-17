package main.java.br.com.rmibank.corebanking;

import java.rmi.Naming;

import main.java.br.com.rmibank.corebanking.application.view.MenuViewAgencia;
import main.java.br.com.rmibank.corebanking.domain.controller.IAgenciaController;

public class BankBranch {
    public static void main(String[] args) throws Exception {

        System.setProperty("java.rmi.server.useCodebaseOnly", "false");

        IAgenciaController agenciaController = (IAgenciaController) Naming
                .lookup("rmi://10.32.143.125:1099/AgenciaController");

        MenuViewAgencia menu = new MenuViewAgencia(agenciaController);
        menu.start();

    }
}