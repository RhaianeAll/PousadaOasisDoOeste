package pousada.model;

public class Hospede {
    private String nome;
    private int idade;
    private String cpf;

    public Hospede(String nome, int idade, String cpf) {
        setIdade(idade);
        this.nome = nome;
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public int getIdade() {
        return idade;
    }
    
    private void setIdade(int idade) {
        if (idade < 0) {
            throw new IllegalArgumentException("A idade do hóspede não pode ser negativa.");
        }
        this.idade = idade;
    }
    
    @Override
    public String toString() {
        return "Nome: " + nome + " | CPF: " + cpf + " | Idade: " + idade;
    }
}