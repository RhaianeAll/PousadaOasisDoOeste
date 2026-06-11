package pousada.model;

public class Refeicao extends Servico {
    private double precoRefeicao;

    public Refeicao(int codigo, String descricao, double precoRefeicao) {
        super(codigo, descricao);
        this.precoRefeicao = precoRefeicao;
    }

    @Override
    public double calcularPrecoTotal() {
        return precoRefeicao;
    }
}
