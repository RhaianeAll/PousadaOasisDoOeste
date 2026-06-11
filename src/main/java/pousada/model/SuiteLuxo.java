package pousada.model;

public class SuiteLuxo extends Acomodacao {
    private double adicionalFrigobar;

    public SuiteLuxo(int codigo, int capacidadeMaxima, double precoBaseDiaria, double adicionalFrigobar) {
        super(codigo, capacidadeMaxima, precoBaseDiaria);
        this.adicionalFrigobar = adicionalFrigobar;
    }

    @Override
    public double calcularPrecoTotalDiarias(int dias) {
        return (getPrecoBaseDiaria() * dias) + adicionalFrigobar;
    }
}