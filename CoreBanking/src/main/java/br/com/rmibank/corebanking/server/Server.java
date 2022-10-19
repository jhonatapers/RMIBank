package main.java.br.com.rmibank.corebanking.server;

import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;

import main.java.br.com.rmibank.corebanking.domain.controller.IAgenciaController;
import main.java.br.com.rmibank.corebanking.domain.controller.IAtmController;
import main.java.br.com.rmibank.corebanking.domain.controller.IIdempotencyController;

public class Server extends Thread {

    private String address;

    private int port;

    private IIdempotencyController idempotencyController;

    private IAgenciaController agenciaController;

    private IAtmController atmController;

    public Server(String address, int port, IIdempotencyController idempotencyController,
            IAgenciaController agenciaController, IAtmController atmController) {
        this.address = address;
        this.port = port;

        this.idempotencyController = idempotencyController;
        this.agenciaController = agenciaController;
        this.atmController = atmController;
    }

    @Override
    public void run() {

        try {

            // Definicao do ip onde o servico ira funcionar
            System.setProperty("java.rmi.server.hostname", address);
            System.setProperty("java.rmi.server.useCodebaseOnly", "false");

            // Registro do servico em uma porta
            LocateRegistry.createRegistry(port);

            // Cria o objeto que implementa os metodos que serao servidos
            // Coloca na porta registrada o servico da AgenciaController
            Naming.bind("IdempotencyController", (Remote) idempotencyController);

            // Cria o objeto que implementa os metodos que serao servidos
            // Coloca na porta registrada o servico da AgenciaController
            Naming.bind("AgenciaController", (Remote) agenciaController);

            // Cria o objeto que implementa os metodos que serao servidos
            // Coloca na porta registrada o servico da AgenciaController
            Naming.bind("AtmController", (Remote) atmController);

            System.out.println("Conexao estabelecida.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
