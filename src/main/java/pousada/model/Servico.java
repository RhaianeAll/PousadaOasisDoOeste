package pousada.model;

public abstract class Servico implements Faturavel {
    private int codigo;
    private String descricao;

    public Servico(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public int getCodigo() { return codigo; }
    public String getDescricao() { return descricao; }
    
    public void exibirDescricao() {
        System.out.println("Serviço [" + codigo + "]: " + descricao);
    }
}