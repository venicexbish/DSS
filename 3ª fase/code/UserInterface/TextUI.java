package UserInterface;

import SimuladorLN.*;
import SimuladorLN.SSConta.Conta;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class TextUI {
    // O model tem a 'lógica de negócio'.
    private ISimuladorLN model;

    // Menus da aplicação
    private Scanner scan;
    private int nJog;
    private Boolean admin;


    public TextUI() throws IOException {
        this.model = new SimuladorLNFacade();
        this.scan = new Scanner(System.in);
    }

    public void run() throws IOException {
        Menu.Logo();

        System.out.println("\n\033[1;35m Bem vindo ao FÓRMULA UM! \033[0m\n");
        this.inicio();
        for (int i = 0; i < nJog; i++) {
            if (i == 0)
                admin = true;
            else
                admin = false;
            this.menuInicial();
        }
        // this.menuSimular();

        System.out.println("Saindo!...");
    }

    public void inicio() {
        System.out.println("Insira o número de jogadores:");
        String n = scan.nextLine();
        nJog = Integer.parseInt(n);
    }

    /**
     * Estado - Menu Principal
     */
    public void menuInicial() {
        Menu menuInicial = new Menu(new String[] {
                "Fazer login",
                "Criar conta",
                "Guest", });
        menuInicial.setHandler(1, () -> {
            System.out.print("Insira username: ");
            String usern = scan.nextLine();
            System.out.print("Insira password: ");
            String pass = scan.nextLine();
            boolean loginValido = this.model.getContaFacade().verificarCredenciais(usern, pass);

            if (loginValido) {
                menuJogador();
            } else
                System.out.println("Login inválido!");
        });

        menuInicial.setHandler(2, () -> {
            System.out.print("Insira username: ");

            String usern = scan.nextLine();
            System.out.print("Insira password: ");
            String pass = scan.nextLine();
            System.out.print("Insira a versão (Base/Premium): ");
            String versao = scan.nextLine();
            Boolean vs;
            // AQUI criar objeto conta

            switch (versao) {
                case "True":
                    vs = true;
                case "False":
                    vs = false;
                default:
                    vs = false;
            }

            Random random = new Random();
            Integer id = random.nextInt();

            // menuJogador();
            Conta c = new Conta();
            c.setIdConta(id.toString());
            c.setUsername(usern);
            c.setPassword(pass);
            c.setVersao(vs);

            // this.model.getContaFacade().Conta(id,usern,pass,versao); //fazer put(id) na
            // ContaDAO
            this.model.getContaFacade().putConta(c);
            System.out.print("\nConta criada com sucesso.\n\n");

        });
        menuInicial.setHandler(3, () -> menuJogador());

    }

    public void menuJogador() {
        Menu menuJog = new Menu(new String[] {
                "Configurar Campeonato",
                "Ver Ranking Global" });
        menuJog.setHandler(1, () -> menuConfigCamp());
        menuJog.setHandler(2, () -> showRankingG());
    }

    public void menuConfigCamp() {
        if (admin) {
            Menu menuAdmin = new Menu(new String[] {
                    "Escolher Campeonato",
                    "Escolher Carro",
                    "Escolher Piloto", });
            menuAdmin.setHandler(1, () -> showCamp());
            menuAdmin.setHandler(2, () -> showCarro());
            menuAdmin.setHandler(3, () -> showPiloto());

        } else {
            Menu menuJ = new Menu(new String[] {
                    "Escolher Carro",
                    "Escolher Piloto", });
            menuJ.setHandler(1, () -> showCarro());
            menuJ.setHandler(2, () -> showPiloto());
        }
    }

    public void showRankingG() {

    }

    public void showCamp() {
        // mostrar lista de campeonatos disponiveis
        this.model.getCampeonatoFacade().listarCampeonatos();
        System.out.println("Insira o numero do campeonato desejado: \n");
        String id = scan.nextLine();

        // para podermos selecionar ENTRE os varios campeonatos

    }

    public void showCarro() {
        // mostrar lista de carros disponiveis

    }

    public void showPiloto() {

    }
}
