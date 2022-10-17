package main.java.br.com.rmibank.corebanking;

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
import main.java.br.com.rmibank.corebanking.server.Server;

public class CoreBanking {
    public static void main(String[] args) throws Exception {

        RmiBankSchema dataBase = new RmiBankSchema();

        IClienteRepository clienteRepository = new ClienteRepositoryLocalJavaClass(dataBase);
        ITransacaoRepository transacaoRepository = new TransacaoRepositoryLocalJavaClass(dataBase);

        IClienteService contaCorrenteService = new ClienteService(clienteRepository);
        ITransacaoService transacaoService = new TransacaoService(transacaoRepository);

        IAgenciaController agenciaController = new CoreBankingController(contaCorrenteService, transacaoService);
        IAtmController atmController = new CoreBankingController(contaCorrenteService, transacaoService);

        try {

            String serverAdress = "localhost";// "192.168.0.12";//args[0];
            int serverPort = Integer.parseInt("1099");// args[1];

            Server server = new Server(serverAdress, serverPort, agenciaController, atmController);
            server.run();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}