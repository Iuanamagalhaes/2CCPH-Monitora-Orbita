package br.com.monitoraorbita.model;

import java.util.Random;

// Realiza leituras simuladas em quilopascals
public class SensorPressao extends ComponenteEspacial implements Sensor {

    private double limiteMinimo;  // pressao minima segura
    private double limiteAlerta;  // pressao maxima segura
    private double valorAtual;    // ultima leitura registrada
    private Random random = new Random();

    public SensorPressao(String id, String nome, double limiteMinimo, double limiteAlerta) {
        super(id, nome);
        this.limiteMinimo = limiteMinimo;
        this.limiteAlerta = limiteAlerta;
        this.valorAtual   = 101.3; // pressao atmosferica
    }

    // Simula uma leitura
    @Override
    public double lerValor() {
        this.valorAtual = Math.round((50 + random.nextDouble() * 150) * 10.0) / 10.0;
        return this.valorAtual;
    }

    @Override
    public boolean verificarFuncionamento() {
        return !getStatus().equals("FALHA");
    }

    @Override
    public String getTipo() {
        return "PRESSAO";
    }

    @Override
    public double getLimiteAlerta() {
        return this.limiteAlerta;
    }

    // Alerta quando o valor sai do intervalo seguro (abaixo do minimo ou acima do maximo)
    @Override
    public boolean estaEmAlerta() {
        return this.valorAtual > limiteAlerta || this.valorAtual < limiteMinimo;
    }

    // Exibe as informacoes especificas deste sensor
    @Override
    public void exibirDetalhes() {
        System.out.println("| Tipo      : Sensor de Pressao");
        System.out.println("| Leitura   : " + valorAtual + " kPa");
        System.out.println("| Lim. Min  : " + limiteMinimo + " kPa");
        System.out.println("| Lim. Max  : " + limiteAlerta + " kPa");
        System.out.println("| Alerta    : " + (estaEmAlerta() ? "SIM" : "OK"));
    }
}