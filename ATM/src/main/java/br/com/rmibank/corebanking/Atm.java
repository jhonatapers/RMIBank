package main.java.br.com.rmibank.corebanking;

import java.rmi.Naming;

import main.java.br.com.rmibank.corebanking.application.view.MenuViewAtm;
import main.java.br.com.rmibank.corebanking.domain.controller.IAtmController;

public class Atm {
    public static void main(String[] args) throws Exception {
        IAtmController atmController = (IAtmController) Naming.lookup("rmi://10.32.143.125:1099/AtmController");

        MenuViewAtm menu = new MenuViewAtm(atmController);

        menu.start();
    }
}
