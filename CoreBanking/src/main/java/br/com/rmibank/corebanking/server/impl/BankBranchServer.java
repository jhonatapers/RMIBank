package main.java.br.com.rmibank.corebanking.server.impl;

import main.java.br.com.rmibank.corebanking.domain.controller.IAgenciaController;
import main.java.br.com.rmibank.corebanking.server.Server;

public class BankBranchServer extends Server {

    public BankBranchServer(String address, int port, IAgenciaController agenciaController) {
        super(address, port, "AgenciaController", agenciaController);
    }

}
