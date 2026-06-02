package br.com.monitoraorbita.model;

import java.util.Random;

public class SensorRadiacao extends ComponenteEspacial implements Sensor {

    private double limiteAlerta;  // radiacao maxima segura
    private double valorAtual;    // ultima leitura registrada
    private Random random = new Random();

    public SensorRadiacao(String id, String nome, double limiteAlerta) {
        super(id, nome);
        this.setLimiteAlerta(limiteAlerta);
        this.valorAtual = 0.0;
    }

    // Simula uma leitura
    @Override
    public double lerValor() {
        this.valorAtual = Math.round(random.nextDouble() * 5.0 * 100.0) / 100.0;
        return this.valorAtual;
    }

    @Override
    public boolean verificarFuncionamento() {
        return !getStatus().equals("FALHA");
    }

    @Override
    public String getTipo() {
        return "RADIACAO";
    }

    @Override
    public double getLimiteAlerta() {
        return this.limiteAlerta;
    }

    @Override
    public boolean estaEmAlerta() {
        return this.valorAtual > this.limiteAlerta;
    }

    // Classifica o nivel de perigo usando as constantes definidas na interface Sensor
    public String getNivelPerigo() {
        if (valorAtual < 0.5) return Sensor.NIVEL_NORMAL;
        if (valorAtual < 1.0) return Sensor.NIVEL_ATENCAO;
        if (valorAtual < 2.0) return Sensor.NIVEL_ALERTA;
        return Sensor.NIVEL_CRITICO;
    }

    // Exibe as informacoes especificas deste sensor
    @Override
    public void exibirDetalhes() {
        System.out.println("| Tipo      : Sensor de Radiacao");
        System.out.println("| Leitura   : " + valorAtual + " mSv/h");
        System.out.println("| Limite    : " + limiteAlerta + " mSv/h");
        System.out.println("| Nivel     : " + getNivelPerigo());
        System.out.println("| Alerta    : " + (estaEmAlerta() ? "SIM" : "OK"));
    }

    public void setLimiteAlerta(double limiteAlerta) {
        if (limiteAlerta > 0) this.limiteAlerta = limiteAlerta;
        else System.err.println("Erro: limite de alerta deve ser positivo.");
    }
}