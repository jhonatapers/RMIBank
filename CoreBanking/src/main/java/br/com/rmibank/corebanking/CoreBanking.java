package main.java.br.com.rmibank.corebanking;

import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;

import main.java.br.com.rmibank.corebanking.adapters.database.RmiBankSchema;
import main.java.br.com.rmibank.corebanking.adapters.database.repository.impl.ClienteRepositoryLocalJavaClass;
import main.java.br.com.rmibank.corebanking.adapters.database.repository.impl.TransacaoRepositoryLocalJavaClass;
import main.java.br.com.rmibank.corebanking.domain.controller.IAgenciaController;
import main.java.br.com.rmibank.corebanking.domain.controller.IAtmController;
import main.java.br.com.rmibank.corebanking.domain.controller.impl.CoreBankingController;
import main.java.br.com.rmibank.corebanking.domain.repository.IClienteRepository;
import main.java.br.com.rmibank.corebanking.domain.repository.ITransacaoRepository;
import main.java.br.com.rmibank.corebanking.domain.service.IClienteService;
import main.java.br.com.rmibank.corebanking.domain.service.ITransacaoService;
import main.java.br.com.rmibank.corebanking.domain.service.impl.ClienteService;
import main.java.br.com.rmibank.corebanking.domain.service.impl.TransacaoService;

public class CoreBanking {
    public static void main(String[] args) throws Exception {

        RmiBankSchema dataBase = new RmiBankSchema();

        IClienteRepository clienteRepository = new ClienteRepositoryLocalJavaClass(dataBase);
        ITransacaoRepository transacaoRepository = new TransacaoRepositoryLocalJavaClass(dataBase);

        IClienteService contaCorrenteService = new ClienteService(clienteRepository);
        ITransacaoService transacaoService = new TransacaoService(transacaoRepository);

        try {

            String serverAdress = "localhost";// "192.168.0.12";//args[0];
            String serverPort = "1099";// args[1];

            // Definicao do ip onde o servico ira funcionar
            System.setProperty("java.rmi.server.hostname", serverAdress);

            // Registro do servico em uma porta
            LocateRegistry.createRegistry(Integer.parseInt(serverPort));

            // Cria o objeto que implementa os metodos que serao servidos
            IAgenciaController agenciaController = new CoreBankingController(contaCorrenteService, transacaoService);
            // Coloca na porta registrada o servico da AgenciaController
            Naming.bind("AgenciaController", (Remote) agenciaController);

            // Cria o objeto que implementa os metodos que serao servidos
            IAtmController atmController = new CoreBankingController(contaCorrenteService, transacaoService);
            // Coloca na porta registrada o servico da AtmController
            Naming.bind("AtmController", (Remote) atmController);

            System.out.println("Conexao estabelecida.");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}