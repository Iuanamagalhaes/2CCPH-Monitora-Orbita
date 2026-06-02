package br.com.monitoraorbita.model;

import java.util.Random;

// Realiza leituras
public class SensorTemperatura extends ComponenteEspacial implements Sensor {

    private double limiteAlerta;  // temperatura maxima segura em graus
    private double valorAtual;    // ultima leitura registrada
    private Random random = new Random();

    public SensorTemperatura(String id, String nome, double limiteAlerta) {
        super(id, nome);
        this.setLimiteAlerta(limiteAlerta);
        this.valorAtual = 20.0;
    }

    // Simula uma leitura entre -50 e 150 graus e armazena o resultado
    @Override
    public double lerValor() {
        this.valorAtual = Math.round((-50 + random.nextDouble() * 200) * 10.0) / 10.0;
        setTemperatura(this.valorAtual);
        return this.valorAtual;
    }

    // O sensor funciona enquanto nao estiver com status de FALHA
    @Override
    public boolean verificarFuncionamento() {
        return !getStatus().equals("FALHA");
    }

    @Override
    public String getTipo() {
        return "TEMPERATURA";
    }

    @Override
    public double getLimiteAlerta() {
        return this.limiteAlerta;
    }

    // Alerta quando o valor atual passa do limite
    @Override
    public boolean estaEmAlerta() {
        return this.valorAtual > this.limiteAlerta;
    }

    // Exibe as informações especificas deste sensor
    @Override
    public void exibirDetalhes() {
        System.out.println("| Tipo      : Sensor de Temperatura");
        System.out.println("| Leitura   : " + valorAtual + " C");
        System.out.println("| Limite    : " + limiteAlerta + " C");
        System.out.println("| Alerta    : " + (estaEmAlerta() ? "SIM" : "OK"));
    }

    public void setLimiteAlerta(double limiteAlerta) {
        if (limiteAlerta > 0) this.limiteAlerta = limiteAlerta;
        else System.err.println("Erro: limite de alerta deve ser positivo.");
    }
}