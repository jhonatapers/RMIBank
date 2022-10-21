package main.java.br.com.rmibank.corebanking;

import java.rmi.Naming;

import main.java.br.com.rmibank.corebanking.application.view.MenuViewAtm;
import main.java.br.com.rmibank.corebanking.domain.controller.IAtmController;
import main.java.br.com.rmibank.corebanking.domain.controller.IIdempotencyController;

public class Atm {
    public static void main(String[] args) throws Exception {

        IAtmController atmController = (IAtmController) Naming
                .lookup("rmi://localhost:1099/AtmController");
        
                
        IIdempotencyController idempotencyController = (IIdempotencyController) Naming
        .lookup("rmi://localhost:1099/IdempotencyController");

        MenuViewAtm menu = new MenuViewAtm(atmController, idempotencyController);

        menu.start();

    }
}
