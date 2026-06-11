package pousada.model;

public class Chale extends Acomodacao {
    private double taxaAquecimento;
    private double taxaLimpeza;

    public Chale(int codigo, int capacidadeMaxima, double precoBaseDiaria, double taxaAquecimento, double taxaLimpeza) {
        super(codigo, capacidadeMaxima, precoBaseDiaria);
        this.taxaAquecimento = taxaAquecimento;
        this.taxaLimpeza = taxaLimpeza;
    }

    @Override
    public double calcularPrecoTotalDiarias(int dias) {
        return (getPrecoBaseDiaria() * dias) + (taxaAquecimento * dias) + taxaLimpeza;
    }
}