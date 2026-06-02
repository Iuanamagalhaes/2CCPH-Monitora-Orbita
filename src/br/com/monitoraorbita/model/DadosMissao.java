package br.com.monitoraorbita.model;

public class DadosMissao {

    // Dados gerais da missao
    private String nomeMissao;
    private int    numeroDeTripulantes;
    private String trajetoria;
    private double nivelCombustivel;  // porcentagem de 0 a 100
    private String statusMissao;      // "ATIVA", "PAUSADA" ou "CONCLUIDA"

    // Dados sensiveis
    private double coordenadaX;
    private double coordenadaY;
    private double coordenadaZ;
    private String codigoAcesso;

    public DadosMissao(String nomeMissao, int numeroDeTripulantes,
                       String trajetoria, String codigoAcesso) {
        this.setNomeMissao(nomeMissao);
        this.setNumeroDeTripulantes(numeroDeTripulantes);
        this.codigoAcesso    = codigoAcesso;
        this.nivelCombustivel = 100.0;
        this.statusMissao    = "ATIVA";
        this.trajetoria = trajetoria;
        System.out.println("Missao '" + this.nomeMissao + "' registrada no sistema!");
    }

    // Exibe um resumo com dados públicos
    public void exibirResumo() {
        System.out.println("+______________________________________________________+");
        System.out.println("|           DADOS DA MISSAO: " + nomeMissao);
        System.out.println("+______________________________________________________+");
        System.out.println("| Status       : " + statusMissao);
        System.out.println("| Tripulantes  : " + numeroDeTripulantes);
        System.out.println("| Trajetoria   : " + trajetoria);
        System.out.printf ("| Combustivel  : %.1f%%%n", nivelCombustivel);
        System.out.println("| Coordenadas  : [PROTEGIDAS - requer codigo de acesso]");
        System.out.println("+______________________________________________________+");
    }

    //Getters públicos
    public String getNomeMissao()       { return this.nomeMissao; }
    public int    getNumeroDeTripulantes() { return this.numeroDeTripulantes; }
    public String getTrajetoria()       { return this.trajetoria; }
    public double getNivelCombustivel() { return this.nivelCombustivel; }
    public String getStatusMissao()     { return this.statusMissao; }

    // Retorna as coordenadas apenas se o codigo de acesso estiver correto
    public String getCoordenadas(String senhaInformada) {
        if (!senhaInformada.equals(codigoAcesso)) {
            return "[ACESSO NEGADO] Codigo incorreto.";
        }
        return "X: " + coordenadaX + " | Y: " + coordenadaY + " | Z: " + coordenadaZ;
    }

    // Setters
    private void setNomeMissao(String nome) {
        if (nome != null && !nome.isBlank()) this.nomeMissao = nome;
        else System.err.println("Erro: nome da missao invalido.");
    }

    private void setNumeroDeTripulantes(int n) {
        if (n > 0) this.numeroDeTripulantes = n;
        else {
            System.err.println("Erro: numero de tripulantes invalido. Definido como 1.");
            this.numeroDeTripulantes = 1;
        }
    }

    public void setTrajetoria(String trajetoria) {
        if (trajetoria != null && !trajetoria.isBlank()) {
            this.trajetoria = trajetoria;
            System.out.println("[MISSAO] Trajetoria atualizada: " + trajetoria);
        } else {
            System.err.println("Erro: trajetoria invalida.");
        }
    }

    // Combustivel deve estar entre 0 e 100 e dispara alerta automatico quando cai abaixo de 20%
    public void setNivelCombustivel(double nivel) {
        if (nivel < 0) {
            System.err.println("Erro: nivel de combustivel nao pode ser negativo.");
            return;
        }
        if (nivel > 100) {
            System.err.println("Erro: nivel nao pode ultrapassar 100%.");
            return;
        }
        this.nivelCombustivel = nivel;
        if (nivel < 20) {
            System.out.println("[ALERTA CRITICO] Combustivel abaixo de 20%! Nivel: " + nivel + "%");
        }
    }

    // Atualiza as coordenadas somente se o codigo de acesso estiver correto
    public void setCoordenadas(String senhaInformada, double x, double y, double z) {
        if (!senhaInformada.equals(codigoAcesso)) {
            System.err.println("[ACESSO NEGADO] Impossivel atualizar coordenadas.");
            return;
        }
        this.coordenadaX = x;
        this.coordenadaY = y;
        this.coordenadaZ = z;
        System.out.println("[MISSAO] Coordenadas atualizadas com sucesso.");
    }

    public void setStatusMissao(String status) {
        if (status.equals("ATIVA") || status.equals("PAUSADA") || status.equals("CONCLUIDA")) {
            this.statusMissao = status;
        } else {
            System.err.println("Erro: status invalido. Use: ATIVA, PAUSADA ou CONCLUIDA.");
        }
    }
}