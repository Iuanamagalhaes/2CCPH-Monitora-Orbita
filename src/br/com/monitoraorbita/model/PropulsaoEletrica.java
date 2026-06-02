package br.com.monitoraorbita.model;

public class PropulsaoEletrica extends SistemaPropulsao {

    // Atributos exclusivos
    private double cargaBateria;    // porcentagem de 0 a 100
    private double eficiencia;      // fator de eficiencia
    private String tipoIon;         // gas ionizado usado
    private int    horasDeOperacao; // contador total de horas de uso

    public PropulsaoEletrica(String id, String nome, double empuxoMaximo, String tipoIon) {
        super(id, nome, empuxoMaximo);
        this.cargaBateria    = 100.0;
        this.eficiencia      = 0.92;  // 92% de eficiência energetica
        this.tipoIon         = tipoIon;
        this.horasDeOperacao = 0;
    }

    @Override
    public void acelerar(double porcentagem) {
        if (!isLigado()) {
            System.err.println("Erro: motor desligado! Ligue antes de acelerar.");
            return;
        }
        if (cargaBateria <= 0) {
            System.err.println("Erro: bateria descarregada! Impossivel acelerar.");
            return;
        }

        setPotenciaAtual(porcentagem);

        double consumo     = Math.round((porcentagem * 0.1 / eficiencia) * 100.0) / 100.0;
        cargaBateria       = Math.max(0, cargaBateria - consumo);
        double empuxoEfetivo = calcularEmpuxo() * eficiencia;
        horasDeOperacao++;

        System.out.println("[ELETRICA] Aceleracao ionica ativada!");
        System.out.printf("[ELETRICA] Potencia: %.1f%% | Empuxo efetivo: %.0f N%n",
                porcentagem, empuxoEfetivo);
        System.out.printf("[ELETRICA] Bateria consumida: %.1f%% | Restante: %.1f%%%n",
                consumo, cargaBateria);

        if (cargaBateria < 20) {
            System.out.println("[AVISO] Bateria critica: " +
                    String.format("%.1f", cargaBateria) + "%");
        }
    }

    // Recarrega a bateria ate o maximo de 100%
    public void recarregarBateria(double quantidade) {
        if (quantidade <= 0) {
            System.err.println("Erro: quantidade de recarga deve ser positiva.");
            return;
        }
        cargaBateria = Math.min(100, cargaBateria + quantidade);
        System.out.printf("[ELETRICA] Bateria recarregada. Nivel atual: %.1f%%%n", cargaBateria);
    }

    // Exibe informações
    @Override
    public void exibirDetalhesEspecificos() {
        System.out.println("| Tipo Prop.  : Eletrica (Ionica)");
        System.out.println("| Ion         : " + tipoIon);
        System.out.printf ("| Bateria     : %.1f%%%n", cargaBateria);
        System.out.printf ("| Eficiencia  : %.0f%%%n", eficiencia * 100);
        System.out.println("| Horas de uso: " + horasDeOperacao + "h");
    }

    public double getCargaBateria()  { return this.cargaBateria; }
    public int getHorasDeOperacao()  { return this.horasDeOperacao; }
}