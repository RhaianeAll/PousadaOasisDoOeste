package pousada.model;

public abstract class Acomodacao {
    private int codigo;
    private int capacidadeMaxima;
    private double precoBaseDiaria;

    public Acomodacao(int codigo, int capacidadeMaxima, double precoBaseDiaria) {
        if (precoBaseDiaria < 0) {
            throw new IllegalArgumentException("O preço base não pode ser negativo.");
        }
        this.codigo = codigo;
        this.capacidadeMaxima = capacidadeMaxima;
        this.precoBaseDiaria = precoBaseDiaria;
    }

    public int getCodigo() {
        return codigo;
    }

    public int getCapacidadeMaxima() {
        return capacidadeMaxima;
    }

    public double getPrecoBaseDiaria() {
        return precoBaseDiaria;
    }

    public void exibirDadosBasicos() {
        System.out.println("Código: " + codigo + " | Capacidade: " + capacidadeMaxima + " | Preço Base: R$" + precoBaseDiaria);
    }

    public abstract double calcularPrecoTotalDiarias(int dias);

}