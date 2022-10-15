package main.java.br.com.rmibank.corebanking.server.impl;

import main.java.br.com.rmibank.corebanking.domain.controller.IAtmController;
import main.java.br.com.rmibank.corebanking.server.Server;

public class AtmServer extends Server {

    public AtmServer(String address, int port, IAtmController atmController) {
        super(address, port, "AtmController", atmController);
    }

}
