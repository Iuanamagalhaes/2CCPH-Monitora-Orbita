package br.com.monitoraorbita.model;

public abstract class SistemaPropulsao {

    // Atributos comuns
    private String  id;
    private String  nome;
    private boolean ligado;
    private double  potenciaAtual;  // porcentagem de 0 a 100
    private double  empuxoMaximo;   // forca maxima em Newtons

    // Construtor chamado pelas subclasses
    public SistemaPropulsao(String id, String nome, double empuxoMaximo) {
        this.setId(id);
        this.setNome(nome);
        this.setEmpuxoMaximo(empuxoMaximo);
        this.ligado       = false;
        this.potenciaAtual = 0.0;
        System.out.println("Motor '" + this.nome + "' registrado no sistema!");
    }

    // Liga o motor
    public void ligarMotor() {
        this.ligado = true;
        System.out.println("[PROPULSAO] Motor '" + nome + "' ligado.");
    }

    // Desliga o motor e zera a potencia atual
    public void desligarMotor() {
        this.ligado       = false;
        this.potenciaAtual = 0.0;
        System.out.println("[PROPULSAO] Motor '" + nome + "' desligado. Potencia zerada.");
    }

    // Calcula o empuxo atual com base na potencia e no empuxo maximo
    public double calcularEmpuxo() {
        return (potenciaAtual / 100.0) * empuxoMaximo;
    }

    // Exibe o status geral do motor
    public void exibirStatus() {
        System.out.println("+______________________________________________________+");
        System.out.println("| ID          : " + id);
        System.out.println("| Motor       : " + nome);
        System.out.println("| Estado      : " + (ligado ? "LIGADO" : "DESLIGADO"));
        System.out.printf ("| Potencia    : %.1f%%%n", potenciaAtual);
        System.out.printf ("| Empuxo      : %.0f N%n", calcularEmpuxo());
        exibirDetalhesEspecificos();
        System.out.println("+______________________________________________________+");
    }

    // Metodos abstratos
    public abstract void acelerar(double porcentagem);
    public abstract void exibirDetalhesEspecificos();

    // Getters
    public String  getId()            { return this.id; }
    public String  getNome()          { return this.nome; }
    public boolean isLigado()         { return this.ligado; }
    public double  getPotenciaAtual() { return this.potenciaAtual; }
    public double  getEmpuxoMaximo()  { return this.empuxoMaximo; }

    // Setter
    protected void setPotenciaAtual(double potencia) {
        if (potencia < 0) {
            System.err.println("Erro: potencia nao pode ser negativa.");
            return;
        }
        if (potencia > 100) {
            System.err.println("Erro: potencia nao pode ultrapassar 100%.");
            return;
        }
        this.potenciaAtual = potencia;
    }

    private void setId(String id) {
        if (id != null && !id.isBlank()) this.id = id;
        else System.err.println("Erro: ID invalido.");
    }

    private void setNome(String nome) {
        if (nome != null && !nome.isBlank()) this.nome = nome;
        else System.err.println("Erro: nome invalido.");
    }

    private void setEmpuxoMaximo(double empuxo) {
        if (empuxo > 0) this.empuxoMaximo = empuxo;
        else System.err.println("Erro: empuxo maximo deve ser positivo.");
    }
}