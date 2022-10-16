package main.java.br.com.rmibank.corebanking;

import java.rmi.Naming;

import main.java.br.com.rmibank.corebanking.application.view.MenuView;
import main.java.br.com.rmibank.corebanking.domain.controller.IAtmController;

public class Atm {
    public static void main(String[] args) throws Exception {
        IAtmController atmController = (IAtmController) Naming.lookup("rmi://localhost:1099/AtmController");

        MenuView menu = new MenuView(atmController);

        menu.start();
    }
}
