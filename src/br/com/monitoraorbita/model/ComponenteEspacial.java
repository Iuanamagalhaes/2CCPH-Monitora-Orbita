package br.com.monitoraorbita.model;

public abstract class ComponenteEspacial {

    // Atributos
    private String id;
    private String nome;
    private String status;       // "LIGADO", "DESLIGADO" ou "FALHA"
    private double temperatura;  // temperatura atual em graus

    // Construtor chamado pelas subclasses
    public ComponenteEspacial(String id, String nome) {
        this.setId(id);
        this.setNome(nome);
        this.status = "DESLIGADO";
        this.temperatura = 20.0;
        System.out.println(this.nome + " foi registrado no sistema!");
    }

    // Liga o componente alterando seu status
    public void ligar() {
        this.status = "LIGADO";
        System.out.println("[SISTEMA] " + nome + " ligado.");
    }

    // Desliga o componente alterando seu status
    public void desligar() {
        this.status = "DESLIGADO";
        System.out.println("[SISTEMA] " + nome + " desligado.");
    }

    public void exibirInfo() {
        System.out.println("+______________________________________________________+");
        System.out.println("| ID        : " + id);
        System.out.println("| Nome      : " + nome);
        System.out.println("| Status    : " + status);
        System.out.println("| Temp.     : " + temperatura + " C");
        exibirDetalhes();
        System.out.println("+______________________________________________________+");
    }

    public abstract void exibirDetalhes();

    // Getters
    public String getId()           { return this.id; }
    public String getNome()         { return this.nome; }
    public String getStatus()       { return this.status; }
    public double getTemperatura()  { return this.temperatura; }

    // Setters com validacao
    private void setId(String id) {
        if (id != null && !id.isBlank()) this.id = id;
        else System.err.println("Erro: ID invalido.");
    }

    private void setNome(String nome) {
        if (nome != null && !nome.isBlank()) this.nome = nome;
        else System.err.println("Erro: nome invalido.");
    }

    public void setStatus(String status) {
        if (status != null && !status.isBlank()) this.status = status;
        else System.err.println("Erro: status invalido.");
    }

    // Temperatura nao pode ser menor que o zero absoluto
    public void setTemperatura(double temperatura) {
        if (temperatura >= -273.15) this.temperatura = temperatura;
        else System.err.println("Erro: temperatura abaixo do zero absoluto.");
    }
}