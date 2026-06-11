package pousada.model;

public class Passeio extends Servico {
    private double precoBase;
    private int qtdMaximaPessoas;

    public Passeio(int codigo, String descricao, double precoBase, int qtdMaximaPessoas) {
        super(codigo, descricao);
        this.precoBase = precoBase;
        this.qtdMaximaPessoas = qtdMaximaPessoas;
    }

    @Override
    public double calcularPrecoTotal() {
        return precoBase * qtdMaximaPessoas;
    }
}