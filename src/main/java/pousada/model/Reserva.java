package pousada.model;

import java.util.ArrayList;
import java.util.List;

public class Reserva {
    private int codigo;
    private Hospede hospedeResponsavel;
    private Acomodacao acomodacao;
    private int qtdHospedes;
    private int qtdDias;
    private List<Servico> servicosContratados;

    public Reserva(int codigo, Hospede hospedeResponsavel, Acomodacao acomodacao, int qtdHospedes, int qtdDias) {
        if (qtdHospedes > acomodacao.getCapacidadeMaxima()) {
            throw new IllegalArgumentException("A quantidade de hóspedes excede a capacidade da acomodação."); 
        }
        this.codigo = codigo;
        this.hospedeResponsavel = hospedeResponsavel;
        this.acomodacao = acomodacao;
        this.qtdHospedes = qtdHospedes;
        this.qtdDias = qtdDias;
        this.servicosContratados = new ArrayList<>();
    }

    public void adicionarServico(Servico servico) {
        this.servicosContratados.add(servico);
    }

    public double calcularPrecoTotalReserva() {
        double total = acomodacao.calcularPrecoTotalDiarias(qtdDias);
        for (Servico s : servicosContratados) {
            total += s.calcularPrecoTotal();
        }
        return total;
    }

    public Hospede getHospedeResponsavel() {
        return hospedeResponsavel;
    }

    public void setHospedeResponsavel(Hospede hospedeResponsavel) {
        this.hospedeResponsavel = hospedeResponsavel;
    }

    public Acomodacao getAcomodacao() {
        return acomodacao;
    }

    public void setAcomodacao(Acomodacao acomodacao) {
        this.acomodacao = acomodacao;
    }

    public int getQtdHospedes() {
        return qtdHospedes;
    }

    public void setQtdHospedes(int qtdHospedes) {
        this.qtdHospedes = qtdHospedes;
    }

    public int getQtdDias() {
        return qtdDias;
    }

    public void setQtdDias(int qtdDias) {
        this.qtdDias = qtdDias;
    }

    public List<Servico> getServicosContratados() {
        return servicosContratados;
    }

    public void setServicosContratados(List<Servico> servicosContratados) {
        this.servicosContratados = servicosContratados;
    }
    
    public int getCodigo() { return codigo; }
}