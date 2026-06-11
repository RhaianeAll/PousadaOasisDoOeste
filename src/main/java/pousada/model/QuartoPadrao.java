package pousada.model;

public class QuartoPadrao extends Acomodacao {
    public QuartoPadrao(int codigo, int capacidadeMaxima, double precoBaseDiaria) {
        super(codigo, capacidadeMaxima, precoBaseDiaria);
    }

    @Override
    public double calcularPrecoTotalDiarias(int dias) {
        return getPrecoBaseDiaria() * dias;
    }
}