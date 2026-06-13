package pousada.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Pousada {
    private List<Hospede> hospedes;
    private List<Acomodacao> acomodacoes;
    private List<Servico> servicos;
    private List<Reserva> reservas;

    public Pousada() {
        this.hospedes = new ArrayList<>();
        this.acomodacoes = new ArrayList<>();
        this.servicos = new ArrayList<>();
        this.reservas = new ArrayList<>();
    }

    //Métodos de Adição e Busca

    public void cadastrarHospede(Hospede h) {
        hospedes.add(h);
    }

    public Hospede buscarHospedePorCpf(String cpf) {
        for (Hospede h : hospedes) {
            if (h.getCpf().equals(cpf)) {
                return h;
            }
        }
        return null;
    }

    public void cadastrarReserva(Reserva r) {
        reservas.add(r);
    }

    public Reserva buscarReservaPorCodigo(int codigo) {
        for (Reserva r : reservas) {
            if (r.getCodigo() == codigo) {
                return r;
            }
        }
        return null;
    }

    public List<Hospede> getHospedes() { return hospedes; }
    public List<Acomodacao> getAcomodacoes() { return acomodacoes; }
    public List<Servico> getServicos() { return servicos; }
    public List<Reserva> getReservas() { return reservas; }

    //Leitura e Salvamento de Arquivos

    public void carregarDadosIniciais() {
        File arquivo = new File("acomodacoes_servicos.txt");
        
        if (!arquivo.exists()) {
            System.out.println("ATENÇÃO: O arquivo acomodacoes_servicos.txt não foi encontrado na raiz do projeto!");
            return; 
        }

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;
                
                // Divide a linha em pedaços usando o ponto e vírgula
                String[] dados = linha.split(";");
                String categoria = dados[0];
                String tipo = dados[1];

                if (categoria.equals("A")) {
                    int codigo = Integer.parseInt(dados[2]);
                    int capacidade = Integer.parseInt(dados[3]);
                    double precoBase = Double.parseDouble(dados[4]);

                    if (tipo.equals("QuartoPadrao")) {
                        acomodacoes.add(new QuartoPadrao(codigo, capacidade, precoBase));
                        
                    } else if (tipo.equals("Chale")) {
                        double taxaAquec = Double.parseDouble(dados[5]);
                        double taxaLimp = Double.parseDouble(dados[6]);
                        acomodacoes.add(new Chale(codigo, capacidade, precoBase, taxaAquec, taxaLimp));
                        
                    } else if (tipo.equals("SuiteLuxo")) {
                        double adicionalFrigobar = Double.parseDouble(dados[5]);
                        acomodacoes.add(new SuiteLuxo(codigo, capacidade, precoBase, adicionalFrigobar));
                    }
                } 
                else if (categoria.equals("S")) {
                    int codigo = Integer.parseInt(dados[2]);
                    String descricao = dados[3];
                    double valorPrincipal = Double.parseDouble(dados[4]);

                    if (tipo.equals("Refeicao")) {
                        servicos.add(new Refeicao(codigo, descricao, valorPrincipal));
                        
                    } else if (tipo.equals("Passeio")) {
                        int qtdMax = Integer.parseInt(dados[5]);
                        servicos.add(new Passeio(codigo, descricao, valorPrincipal, qtdMax));
                        
                    } else if (tipo.equals("Massagem")) {
                        servicos.add(new Massagem(codigo, descricao, valorPrincipal));
                    }
                }
            }
            System.out.println("Dados iniciais carregados com sucesso a partir do arquivo!");
            
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de dados iniciais: " + e.getMessage());
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.err.println("Erro de formatação no arquivo .txt. Verifique se os campos estão corretos: " + e.getMessage());
        }
    }

    //Carregar hóspedes e reservas 
    public void carregarHospedesEReservas() {
        File arquivo = new File("hospedes_reservas.txt");
        if (!arquivo.exists()) {
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar os dados salvos: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            
            System.err.println("Dados inconsistentes no arquivo: " + e.getMessage());
        }
    }

    public void salvarDados() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("hospedes_reservas.txt"))) {
            
            bw.write("--- HOSPEDES ---");
            bw.newLine();
            for (Hospede h : hospedes) {
                bw.write(h.getNome() + ";" + h.getIdade() + ";" + h.getCpf());
                bw.newLine();
            }

            bw.write("--- RESERVAS ---");
            bw.newLine();
            for (Reserva r : reservas) {
                bw.write(r.getCodigo() + ";" + r.getQtdHospedes() + ";" + r.getQtdDias()); 
                bw.newLine();
            }
            
        } catch (IOException e) {
            System.err.println("Erro ao salvar os arquivos: " + e.getMessage());
        }
    }
}