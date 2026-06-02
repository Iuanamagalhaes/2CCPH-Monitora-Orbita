package br.com.monitoraorbita.model;

public interface Sensor {

    // Constantes de nivel de alerta usadas pelos sensores
    String NIVEL_NORMAL  = "NORMAL";
    String NIVEL_ATENCAO = "ATENCAO";
    String NIVEL_ALERTA  = "ALERTA";
    String NIVEL_CRITICO = "CRITICO";

    // Le e retorna o valor atual do sensor (simulado com valores aleatorios)
    double lerValor();

    // Retorna verdadeiro se o sensor estiver operacional (nao estiver com status "FALHA")
    boolean verificarFuncionamento();

    // Retorna o tipo do sensor como texto (ex: "TEMPERATURA", "PRESSAO")
    String getTipo();

    // Retorna o limite maximo configurado para disparar alerta
    double getLimiteAlerta();

    // Retorna true se o valor atual ultrapassou o limite de alerta
    boolean estaEmAlerta();
}