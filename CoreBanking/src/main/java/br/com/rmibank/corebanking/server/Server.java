package main.java.br.com.rmibank.corebanking.server;

import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;

public abstract class Server extends Thread {

    private String address;

    private int port;

    private String bindName;

    private Object remoteObjectInterface;

    public Server(String address, int port, String bindName, Object remoteObjectInterface) {
        this.address = address;
        this.port = port;
        this.bindName = bindName;
        this.remoteObjectInterface = remoteObjectInterface;
    }

    @Override
    public void run() {

        try {

            // Definicao do ip onde o servico ira funcionar
            System.setProperty("java.rmi.server.hostname", address);

            // Registro do servico em uma porta
            LocateRegistry.createRegistry(port);

            // Cria o objeto que implementa os metodos que serao servidos
            // Coloca na porta registrada o servico da AgenciaController
            Naming.bind(bindName, (Remote) remoteObjectInterface);

            System.out.println("Conexao estabelecida.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
