package br.com.monitoraorbita.model;

public class PropulsaoQuimica extends SistemaPropulsao {

    // Atributos
    private double nivelCombustivel;  // litros disponiveis
    private String tipoCombustivel;
    private double consumoPorUnidade; // litros consumidos por % de potencia aplicada

    public PropulsaoQuimica(String id, String nome, double empuxoMaximo,
                            double nivelCombustivel, String tipoCombustivel) {
        super(id, nome, empuxoMaximo);
        this.nivelCombustivel  = nivelCombustivel;
        this.tipoCombustivel   = tipoCombustivel;
        this.consumoPorUnidade = 0.5; // 0.5 litros por % de potencia
    }

    // Consome combustivel proporcional a potência aplicada
    @Override
    public void acelerar(double porcentagem) {
        if (!isLigado()) {
            System.err.println("Erro: motor desligado! Ligue antes de acelerar.");
            return;
        }
        if (nivelCombustivel <= 0) {
            System.err.println("Erro: sem combustivel! Impossivel acelerar.");
            return;
        }

        setPotenciaAtual(porcentagem);

        double consumo = porcentagem * consumoPorUnidade;
        nivelCombustivel = Math.max(0, nivelCombustivel - consumo);

        System.out.println("[QUIMICA] Aceleracao ativada!");
        System.out.printf("[QUIMICA] Potencia: %.1f%% | Empuxo: %.0f N%n",
                porcentagem, calcularEmpuxo());
        System.out.printf("[QUIMICA] Combustivel consumido: %.1fL | Restante: %.1fL%n",
                consumo, nivelCombustivel);

        if (nivelCombustivel < 100) {
            System.out.println("[AVISO] Combustivel baixo: " +
                    String.format("%.1f", nivelCombustivel) + "L restantes.");
        }
    }

    // Exibe informações exclusivas
    @Override
    public void exibirDetalhesEspecificos() {
        System.out.println("| Tipo Prop.  : Quimica");
        System.out.println("| Combustivel : " + tipoCombustivel);
        System.out.printf ("| Nivel Comb. : %.1f L%n", nivelCombustivel);
        System.out.printf ("| Consumo/%%   : %.1f L%n", consumoPorUnidade);
    }

    public double getNivelCombustivel() { return this.nivelCombustivel; }
    public String getTipoCombustivel()  { return this.tipoCombustivel; }
}