package pousada.model;

public class Acomodacao {
    private String codAcomodacao;
    private int capacidadeMax;
    private double precoBaseDiaria;

    public Acomodacao(String codAcomodacao, int capacidadeMax, double precoBaseDiaria) {
        this.codAcomodacao = codAcomodacao;
        this.capacidadeMax = capacidadeMax;
        this.precoBaseDiaria = precoBaseDiaria;
    }

    public String getCodAcomodacao() {
        return codAcomodacao;
    }

    public void setCodAcomodacao(String codAcomodacao) {
        this.codAcomodacao = codAcomodacao;
    }

    public int getCapacidadeMax() {
        return capacidadeMax;
    }

    public void setCapacidadeMax(int capacidadeMax) {
        this.capacidadeMax = capacidadeMax;
    }

    public double getPrecoBaseDiaria() {
        return precoBaseDiaria;
    }

    public void setPrecoBaseDiaria(double precoBaseDiaria) {
        this.precoBaseDiaria = precoBaseDiaria;
    }
    
    
    
}
