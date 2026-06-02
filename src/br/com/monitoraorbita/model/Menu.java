package br.com.monitoraorbita.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {

    Scanner scanner = new Scanner(System.in);
    private boolean sair = false;

    private SensorTemperatura sensorTemp;
    private SensorPressao     sensorPressao;
    private SensorRadiacao    sensorRadiacao;
    private PropulsaoQuimica  propQuimica;
    private PropulsaoEletrica propEletrica;
    private DadosMissao       dadosMissao;

    // Inicializa todos os componentes com valores padrão ao criar o menu
    public Menu() {
        this.sensorTemp     = new SensorTemperatura("ST-001", "Sensor Termico",    80.0);
        this.sensorPressao  = new SensorPressao    ("SP-001", "Sensor de Pressao", 70.0, 150.0);
        this.sensorRadiacao = new SensorRadiacao   ("SR-001", "Sensor Geiger",     1.5);
        this.propQuimica    = new PropulsaoQuimica ("PQ-001", "Motor Quimico Alpha",
                500000.0, 1000.0, "Hidrogenio Liquido");
        this.propEletrica   = new PropulsaoEletrica("PE-001", "Motor Ionico Beta",
                50000.0, "Xenonio");
    }

    public void exibirMenu() {
        System.out.println("+______________________________________________________+");
        System.out.println("|  Bem-vindo(a) ao Sistema de Monitoramento Espacial   |");
        System.out.println("+______________________________________________________+");
        System.out.print("| Nome da missão: ");
        String nomeMissao = scanner.nextLine();
        System.out.print("| Número de tripulantes: ");
        int tripulantes = 1;
        try {
            tripulantes = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.err.println("Valor inválido. Definido como 1.");
        }

        System.out.print("| Trajetória: ");
        String trajetoria = scanner.nextLine();

        System.out.print("| Defina um código de acesso secreto: ");
        String codigo = scanner.nextLine();

        // Cria a missão com os dados informados
        dadosMissao = new DadosMissao(nomeMissao, tripulantes, trajetoria, codigo);

        // Liga os três sensores ao iniciar
        sensorTemp.ligar();
        sensorPressao.ligar();
        sensorRadiacao.ligar();

        // Faz uma leitura inicial para mostrar o estado dos sensores na abertura
        System.out.println("+______________________________________________________+");
        System.out.println("| Leitura inicial dos sensores:");
        List<Sensor> sensores = new ArrayList<>();
        sensores.add(sensorTemp);
        sensores.add(sensorPressao);
        sensores.add(sensorRadiacao);

        for (Sensor s : sensores) {
            double val = s.lerValor();
            System.out.printf("| %-12s : %.2f | Alerta: %s%n",
                    s.getTipo(), val, s.estaEmAlerta() ? "SIM" : "OK");
        }
        System.out.println("+______________________________________________________+");

        while (!sair) {
            System.out.println("+______________________________________________________+");
            System.out.println("| Escolha uma opção:                                   |");
            System.out.println("| 1 - Verificar sensores                               |");
            System.out.println("| 2 - Controlar propulsão                              |");
            System.out.println("| 3 - Gerenciar dados da missão                        |");
            System.out.println("| 4 - Simular alertas                                  |");
            System.out.println("| 5 - Exibir status completo                           |");
            System.out.println("| 0 - Sair                                             |");
            System.out.println("+______________________________________________________+");
            System.out.print("Escolha: ");

            int opcao;
            try {
                opcao = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.err.println("Opção inválida!");
                continue;
            }

            switch (opcao) {
                case 1:
                    menuSensores();
                    break;

                case 2:
                    menuPropulsao();
                    break;

                case 3:
                    menuDadosMissao();
                    break;

                case 4:
                    simularAlertas();
                    break;

                case 5:
                    exibirStatusCompleto();
                    break;

                case 0:
                    System.out.println("[SISTEMA] Encerrando monitoramento. Boa viagem!");
                    sair = true;
                    break;

                default:
                    System.err.println("Opção inválida!");
                    break;
            }
        }
    }

    private void menuSensores() {
        System.out.println("+______________________________________________________+");
        System.out.println("| 1 - Ler todos os sensores                            |");
        System.out.println("| 2 - Detalhes: Sensor de Temperatura                  |");
        System.out.println("| 3 - Detalhes: Sensor de Pressão                      |");
        System.out.println("| 4 - Detalhes: Sensor de Radiação                     |");
        System.out.println("| 5 - Verificar alertas                                |");
        System.out.println("+______________________________________________________+");
        System.out.print("Opção: ");

        int op;
        try {
            op = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.err.println("Opção inválida!");
            return;
        }

        switch (op) {
            case 1:
                lerTodosSensores();
                break;

            case 2:
                sensorTemp.lerValor();
                sensorTemp.exibirInfo();
                break;

            case 3:
                sensorPressao.lerValor();
                sensorPressao.exibirInfo();
                break;

            case 4:
                sensorRadiacao.lerValor();
                sensorRadiacao.exibirInfo();
                break;

            case 5:
                verificarAlertasSensores();
                break;

            default:
                System.err.println("Opção inválida!");
                break;
        }
    }

    // Lê os três sensores e exibe os valores seguidos de verificação de alertas
    private void lerTodosSensores() {
        System.out.println("[LEITURA] Coletando dados dos sensores...");
        System.out.printf("| Temperatura : %.1f C%n",     sensorTemp.lerValor());
        System.out.printf("| Pressão     : %.1f kPa%n",   sensorPressao.lerValor());
        System.out.printf("| Radiação    : %.2f mSv/h%n", sensorRadiacao.lerValor());
        verificarAlertasSensores();
    }

    // Verifica cada sensor e imprime alertas para os que estão fora do limite
    private void verificarAlertasSensores() {
        System.out.println("[ALERTAS] Verificando sensores...");
        boolean tudoOk = true;

        if (sensorTemp.estaEmAlerta()) {
            System.out.println("[ALERTA] Temperatura acima do limite! " +
                    sensorTemp.getTemperatura() + " C");
            tudoOk = false;
        }
        if (sensorPressao.estaEmAlerta()) {
            System.out.println("[ALERTA] Pressão fora do intervalo seguro!");
            tudoOk = false;
        }
        if (sensorRadiacao.estaEmAlerta()) {
            System.out.println("[CRÍTICO] Radiação acima do limite!");
            tudoOk = false;
        }
        if (tudoOk) {
            System.out.println("[OK] Todos os sensores dentro dos parâmetros normais.");
        }
    }

    private void menuPropulsao() {
        System.out.println("+______________________________________________________+");
        System.out.println("| 1 - Ligar Motor Químico                              |");
        System.out.println("| 2 - Desligar Motor Químico                           |");
        System.out.println("| 3 - Acelerar - Motor Químico                         |");
        System.out.println("| 4 - Ligar Motor Elétrico                             |");
        System.out.println("| 5 - Desligar Motor Elétrico                          |");
        System.out.println("| 6 - Acelerar - Motor Elétrico                        |");
        System.out.println("| 7 - Ver status dos motores                           |");
        System.out.println("+______________________________________________________+");
        System.out.print("Opção: ");

        int op;
        try {
            op = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.err.println("Opção inválida!");
            return;
        }

        switch (op) {
            case 1:
                propQuimica.ligarMotor();
                break;

            case 2:
                propQuimica.desligarMotor();
                break;

            case 3:
                System.out.print("| Potência (0-100): ");
                try {
                    double pot = Double.parseDouble(scanner.nextLine().trim().replace(",", "."));
                    propQuimica.acelerar(pot);
                } catch (NumberFormatException e) {
                    System.err.println("Valor inválido!");
                }
                break;

            case 4:
                propEletrica.ligarMotor();
                break;

            case 5:
                propEletrica.desligarMotor();
                break;

            case 6:
                System.out.print("| Potência (0-100): ");
                try {
                    double pot = Double.parseDouble(scanner.nextLine().trim().replace(",", "."));
                    propEletrica.acelerar(pot);
                } catch (NumberFormatException e) {
                    System.err.println("Valor inválido!");
                }
                break;

            case 7:
                propQuimica.exibirStatus();
                propEletrica.exibirStatus();
                break;

            default:
                System.err.println("Opção inválida!");
                break;
        }
    }

    private void menuDadosMissao() {
        System.out.println("+______________________________________________________+");
        System.out.println("| 1 - Exibir resumo da missão                          |");
        System.out.println("| 2 - Ver coordenadas (requer senha)                   |");
        System.out.println("| 3 - Atualizar nível de combustível                   |");
        System.out.println("| 4 - Atualizar trajetória                             |");
        System.out.println("| 5 - Atualizar coordenadas (requer senha)             |");
        System.out.println("+______________________________________________________+");
        System.out.print("Opção: ");

        int op;
        try {
            op = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.err.println("Opção inválida!");
            return;
        }

        switch (op) {
            case 1:
                dadosMissao.exibirResumo();
                break;

            case 2:
                System.out.print("| Código de acesso: ");
                System.out.println(dadosMissao.getCoordenadas(scanner.nextLine()));
                break;

            case 3:
                System.out.print("| Novo nível de combustível (0-100): ");
                try {
                    double nivel = Double.parseDouble(scanner.nextLine().trim().replace(",", "."));
                    dadosMissao.setNivelCombustivel(nivel);
                } catch (NumberFormatException e) {
                    System.err.println("Valor inválido!");
                }
                break;

            case 4:
                System.out.print("| Nova trajetória: ");
                dadosMissao.setTrajetoria(scanner.nextLine());
                break;

            case 5:
                System.out.print("| Código de acesso: ");
                String cod = scanner.nextLine();
                try {
                    System.out.print("| Coordenada X: ");
                    double x = Double.parseDouble(scanner.nextLine().trim().replace(",", "."));
                    System.out.print("| Coordenada Y: ");
                    double y = Double.parseDouble(scanner.nextLine().trim().replace(",", "."));
                    System.out.print("| Coordenada Z: ");
                    double z = Double.parseDouble(scanner.nextLine().trim().replace(",", "."));
                    dadosMissao.setCoordenadas(cod, x, y, z);
                } catch (NumberFormatException e) {
                    System.err.println("Coordenadas inválidas!");
                }
                break;

            default:
                System.err.println("Opção inválida!");
                break;
        }
    }

    // Faz 5 leituras aleatórias consecutivas e classifica o nível de alerta de cada uma
    private void simularAlertas() {
        System.out.println("[SIMULAÇÃO] Gerando 5 leituras aleatórias...");
        for (int i = 1; i <= 5; i++) {
            System.out.println("\n--- Leitura #" + i + " ---");
            sensorTemp.lerValor();
            sensorPressao.lerValor();
            sensorRadiacao.lerValor();

            int alertas = 0;
            if (sensorTemp.estaEmAlerta())     alertas++;
            if (sensorPressao.estaEmAlerta())  alertas++;
            if (sensorRadiacao.estaEmAlerta()) alertas++;

            if (alertas == 0) {
                System.out.println("[NORMAL] Todos os parâmetros dentro do esperado.");
            } else if (alertas == 1) {
                System.out.println("[ATENÇÃO] 1 sensor em alerta.");
            } else if (alertas == 2) {
                System.out.println("[ALERTA] 2 sensores em alerta! Verificação necessária.");
            } else {
                System.out.println("[CRÍTICO] Todos os sensores em alerta! Acionar emergência!");
            }
        }
    }

    // Exibe o status de todos os componentes de uma vez
    private void exibirStatusCompleto() {
        System.out.println("+______________________________________________________+");
        System.out.println("|              STATUS COMPLETO DO SISTEMA              |");
        System.out.println("+______________________________________________________+");
        dadosMissao.exibirResumo();
        System.out.println("\n----- SENSORES -----");
        sensorTemp.exibirInfo();
        sensorPressao.exibirInfo();
        sensorRadiacao.exibirInfo();
        System.out.println("\n----- PROPULSÃO -----");
        propQuimica.exibirStatus();
        propEletrica.exibirStatus();
    }
}