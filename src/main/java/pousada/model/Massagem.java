package pousada.model;

public class Massagem extends Servico {
    private double precoSessao;

    public Massagem(int codigo, String descricao, double precoSessao) {
        super(codigo, descricao);
        this.precoSessao = precoSessao;
    }

    @Override
    public double calcularPrecoTotal() {
        return precoSessao;
    }
}