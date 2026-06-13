package pousada.view;

import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import pousada.model.Acomodacao;
import pousada.model.Hospede;
import pousada.model.Pousada;
import pousada.model.Reserva;
import pousada.model.Servico;

public class SistemaPousadaUI extends JFrame {

    // --- Paleta de Cores ---
    private final Color COR_FUNDO_MENU = new Color(180, 212, 255); // Azul Pastel
    private final Color COR_FUNDO_TELA = Color.WHITE;
    private final Color COR_TEXTO_ESCURO = new Color(50, 50, 50);
    private final Color COR_BOTAO_ACAO = new Color(100, 149, 237); // Azul mais forte para botões
    private final Color COR_TEXTO_BOTAO = Color.WHITE;

    // --- Fontes ---
    private final Font FONTE_TITULO = new Font("Segoe UI", Font.BOLD, 24);
    private final Font FONTE_SUBTITULO = new Font("Segoe UI", Font.BOLD, 16);
    private final Font FONTE_PADRAO = new Font("Segoe UI", Font.PLAIN, 14);

    // --- Gerenciador de Telas (Páginas) ---
    private JPanel painelPrincipalCards;
    private CardLayout gerenciadorCards;
    
    private Pousada pousada;

    public SistemaPousadaUI() {
        pousada = new Pousada();
        
        pousada.carregarDadosIniciais();
        pousada.carregarHospedesEReservas();
        configurarJanelaBase();
        inicializarComponentes();

    }

    private void configurarJanelaBase() {
        setTitle("Pousada Oásis do Oeste - Sistema de Gestão");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void inicializarComponentes() {
        // 1. Criar o Menu Lateral (Sidebar)
        JPanel menuLateral = new JPanel();
        menuLateral.setLayout(new BoxLayout(menuLateral, BoxLayout.Y_AXIS));
        menuLateral.setBackground(COR_FUNDO_MENU);
        menuLateral.setPreferredSize(new Dimension(200, getHeight()));
        menuLateral.setBorder(new EmptyBorder(20, 10, 20, 10));

        // Título do Menu
        JLabel lblLogo = new JLabel("<html><center>Pousada<br>Oásis do Oeste</center></html>");
        lblLogo.setFont(FONTE_SUBTITULO);
        lblLogo.setForeground(COR_TEXTO_ESCURO);
        lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuLateral.add(lblLogo);
        menuLateral.add(Box.createRigidArea(new Dimension(0, 40)));

        // Botões do Menu
        JButton btnHospedes = criarBotaoMenu("Hóspedes", "telaHospedes");
        JButton btnAcomodacoes = criarBotaoMenu("Acomodações", "telaAcomodacoes");
        JButton btnServicos = criarBotaoMenu("Serviços", "telaServicos");
        JButton btnReservas = criarBotaoMenu("Reservas", "telaReservas");

        menuLateral.add(btnHospedes);
        menuLateral.add(Box.createRigidArea(new Dimension(0, 10)));
        menuLateral.add(btnAcomodacoes);
        menuLateral.add(Box.createRigidArea(new Dimension(0, 10)));
        menuLateral.add(btnServicos);
        menuLateral.add(Box.createRigidArea(new Dimension(0, 10)));
        menuLateral.add(btnReservas);

        menuLateral.add(Box.createVerticalGlue()); 

        JButton btnSalvarSistema = new JButton("Salvar Sistema");
        btnSalvarSistema.setMaximumSize(new Dimension(180, 40));
        btnSalvarSistema.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSalvarSistema.setBackground(new Color(60, 179, 113));
        btnSalvarSistema.setForeground(Color.WHITE);
        btnSalvarSistema.setOpaque(true);
        btnSalvarSistema.setContentAreaFilled(true);
        btnSalvarSistema.setBorderPainted(false);
        btnSalvarSistema.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSalvarSistema.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        btnSalvarSistema.addActionListener(e -> {
            pousada.salvarDados();
            JOptionPane.showMessageDialog(this, "Dados salvos com sucesso no arquivo hospedes_reservas.txt!", "Salvar", JOptionPane.INFORMATION_MESSAGE);
        });

        JButton btnSair = new JButton("Sair do Sistema");
        btnSair.setMaximumSize(new Dimension(180, 40));
        btnSair.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSair.setBackground(new Color(220, 20, 60));
        btnSair.setForeground(Color.WHITE);
        btnSair.setOpaque(true);
        btnSair.setContentAreaFilled(true);
        btnSair.setBorderPainted(false);
        btnSair.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSair.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnSair.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Deseja salvar os dados antes de sair?", "Sair", JOptionPane.YES_NO_CANCEL_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                pousada.salvarDados();
                System.exit(0);
            } else if (confirm == JOptionPane.NO_OPTION) {
                System.exit(0);
            }
        });

        menuLateral.add(btnSalvarSistema);
        menuLateral.add(Box.createRigidArea(new Dimension(0, 10)));
        menuLateral.add(btnSair);

        // 2. Criar a Área Principal (onde as telas mudam)
        gerenciadorCards = new CardLayout();
        painelPrincipalCards = new JPanel(gerenciadorCards);
        painelPrincipalCards.setBackground(COR_FUNDO_TELA);

        // Adicionando as telas ao gerenciador de cards
        painelPrincipalCards.add(criarTelaHospedes(), "telaHospedes");
        painelPrincipalCards.add(criarTelaAcomodacoes(), "telaAcomodacoes");
        painelPrincipalCards.add(criarTelaServicos(), "telaServicos");
        painelPrincipalCards.add(criarTelaReservas(), "telaReservas");

        // 3. Adicionar tudo à Janela
        add(menuLateral, BorderLayout.WEST);
        add(painelPrincipalCards, BorderLayout.CENTER);
    }

    //Métodos Auxiliares de Criação de UI
    private void estilizarTabela(JTable tabela) {
        tabela.setFont(FONTE_PADRAO);
        tabela.setRowHeight(30); // Linhas mais altas para dar "respiro"
        tabela.setSelectionBackground(COR_BOTAO_ACAO); // Cor da linha quando selecionada
        tabela.setSelectionForeground(COR_TEXTO_BOTAO);
        tabela.setShowVerticalLines(false); // Remove as linhas verticais da grade
        tabela.setGridColor(new Color(230, 230, 230)); // Linhas horizontais bem suaves

        // Customização do Cabeçalho
        tabela.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tabela.getTableHeader().setBackground(COR_FUNDO_MENU);
        tabela.getTableHeader().setForeground(COR_TEXTO_ESCURO);
        tabela.getTableHeader().setOpaque(false);
    }
    
    private JButton criarBotaoMenu(String texto, String nomeCard) {
        JButton botao = new JButton(texto);
        botao.setMaximumSize(new Dimension(180, 40));
        botao.setFont(FONTE_PADRAO);
        botao.setBackground(COR_FUNDO_TELA); // Fundo branco no menu azul
        botao.setForeground(COR_TEXTO_ESCURO);
        botao.setFocusPainted(false);
        botao.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Ação para trocar a tela quando clicar
        botao.addActionListener(e -> gerenciadorCards.show(painelPrincipalCards, nomeCard));

        return botao;
    }

    private JButton criarBotaoAcao(String texto) {
        JButton botao = new JButton(texto);
        botao.setFont(FONTE_SUBTITULO);
        
        // Cores
        botao.setBackground(COR_BOTAO_ACAO); 
        botao.setForeground(COR_TEXTO_BOTAO);
        
        // Os comandos mágicos que forçam o Java a pintar o fundo ignorando o Windows/Mac
        botao.setOpaque(true); 
        botao.setContentAreaFilled(true);
        botao.setBorderPainted(false);
        
        // Estilo
        botao.setFocusPainted(false);
        botao.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        return botao;
    }

    //Construção das Telas Internas
    private JPanel criarTelaAcomodacoes() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBackground(COR_FUNDO_TELA);
        painel.setBorder(new EmptyBorder(30, 40, 30, 40));

        //CABEÇALHO
        JLabel lblTitulo = new JLabel("Gestão de Acomodações");
        lblTitulo.setFont(FONTE_TITULO);
        lblTitulo.setForeground(COR_TEXTO_ESCURO);
        painel.add(lblTitulo, BorderLayout.NORTH);

        //PAINEL DE BUSCA
        JPanel painelBusca = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelBusca.setBackground(COR_FUNDO_TELA);
        
        painelBusca.add(new JLabel("Buscar por Código:"));
        JTextField txtCodigoBusca = new JTextField(10);
        painelBusca.add(txtCodigoBusca);
        
        JButton btnBuscar = criarBotaoAcao("Buscar Acomodação");
        painelBusca.add(btnBuscar);
        
        //TABELA
        String[] colunas = {"Código", "Tipo", "Capacidade", "Preço Base (R$)"};
        DefaultTableModel modeloAcomodacoes = new DefaultTableModel(colunas, 0);
        JTable tabelaAcomodacoes = new JTable(modeloAcomodacoes);
        estilizarTabela(tabelaAcomodacoes);
        
        JScrollPane scrollPane = new JScrollPane(tabelaAcomodacoes);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        scrollPane.getViewport().setBackground(COR_FUNDO_TELA);
        
        //Agrupando busca e tabela no centro
        JPanel painelCentro = new JPanel(new BorderLayout());
        painelCentro.setBackground(COR_FUNDO_TELA);
        painelCentro.add(painelBusca, BorderLayout.NORTH);
        painelCentro.add(scrollPane, BorderLayout.CENTER);
        
        painel.add(painelCentro, BorderLayout.CENTER);

        //BOTÃO DE ATUALIZAR TABELA
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBotoes.setBackground(COR_FUNDO_TELA);
        JButton btnAtualizar = criarBotaoAcao("Atualizar Lista");
        painelBotoes.add(btnAtualizar);
        painel.add(painelBotoes, BorderLayout.SOUTH);

        //AÇÕES DOS BOTÕES

        //Exibir dados de uma acomodação
        btnBuscar.addActionListener(e -> {
            try {
                int codigo = Integer.parseInt(txtCodigoBusca.getText().trim());
                Acomodacao encontrada = null;
                
                // Busca na lista da pousada
                for (Acomodacao a : pousada.getAcomodacoes()) {
                    if (a.getCodigo() == codigo) {
                        encontrada = a;
                        break;
                    }
                }
                
                if (encontrada != null) {
                    // O método getClass().getSimpleName() pega o nome da classe (Ex: "Chale", "QuartoPadrao")
                    String tipo = encontrada.getClass().getSimpleName();
                    String info = "Código: " + encontrada.getCodigo() + 
                                  "\nTipo: " + tipo +
                                  "\nCapacidade: " + encontrada.getCapacidadeMaxima() + " pessoas" +
                                  "\nPreço Base: R$ " + encontrada.getPrecoBaseDiaria();
                    
                    JOptionPane.showMessageDialog(this, info, "Detalhes da Acomodação", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Acomodação não encontrada com este código.", "Aviso", JOptionPane.WARNING_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Por favor, digite um código numérico válido.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        //Exibir dados de todas as acomodações
        btnAtualizar.addActionListener(e -> {
            modeloAcomodacoes.setRowCount(0);
            for (Acomodacao a : pousada.getAcomodacoes()) {
                String tipo = a.getClass().getSimpleName(); 
                modeloAcomodacoes.addRow(new Object[]{
                    a.getCodigo(), 
                    tipo, 
                    a.getCapacidadeMaxima(), 
                    String.format("%.2f", a.getPrecoBaseDiaria())
                });
            }
        });

        return painel;
    }

    private JPanel criarTelaServicos() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBackground(COR_FUNDO_TELA);
        painel.setBorder(new EmptyBorder(30, 40, 30, 40));

        //CABEÇALHO
        JLabel lblTitulo = new JLabel("Gestão de Serviços");
        lblTitulo.setFont(FONTE_TITULO);
        lblTitulo.setForeground(COR_TEXTO_ESCURO);
        painel.add(lblTitulo, BorderLayout.NORTH);

        //PAINEL DE BUSCA
        JPanel painelBusca = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelBusca.setBackground(COR_FUNDO_TELA);
        
        painelBusca.add(new JLabel("Buscar por Código:"));
        JTextField txtCodigoBusca = new JTextField(10);
        painelBusca.add(txtCodigoBusca);
        
        JButton btnBuscar = criarBotaoAcao("Buscar Serviço");
        painelBusca.add(btnBuscar);
        
        //TABELA
        String[] colunas = {"Código", "Tipo", "Descrição", "Preço/Taxa (R$)"};
        DefaultTableModel modeloServicos = new DefaultTableModel(colunas, 0);
        JTable tabelaServicos = new JTable(modeloServicos);
        estilizarTabela(tabelaServicos);
        
        JScrollPane scrollPane = new JScrollPane(tabelaServicos);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        scrollPane.getViewport().setBackground(COR_FUNDO_TELA);
        
        //Agrupando busca e tabela no centro
        JPanel painelCentro = new JPanel(new BorderLayout());
        painelCentro.setBackground(COR_FUNDO_TELA);
        painelCentro.add(painelBusca, BorderLayout.NORTH);
        painelCentro.add(scrollPane, BorderLayout.CENTER);
        
        painel.add(painelCentro, BorderLayout.CENTER);

        //BOTÃO DE ATUALIZAR TABELA
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBotoes.setBackground(COR_FUNDO_TELA);
        JButton btnAtualizar = criarBotaoAcao("Atualizar Lista");
        painelBotoes.add(btnAtualizar);
        painel.add(painelBotoes, BorderLayout.SOUTH);

        //AÇÕES DOS BOTÕES (A LÓGICA DE FATO)
        //Exibir dados de um serviço
        btnBuscar.addActionListener(e -> {
            try {
                int codigo = Integer.parseInt(txtCodigoBusca.getText().trim());
                Servico encontrado = null;
                
                // Busca na lista da pousada
                for (Servico s : pousada.getServicos()) {
                    if (s.getCodigo() == codigo) {
                        encontrado = s;
                        break;
                    }
                }
                
                if (encontrado != null) {
                    String tipo = encontrado.getClass().getSimpleName();
                    String info = "Código: " + encontrado.getCodigo() + 
                                  "\nTipo: " + tipo +
                                  "\nDescrição: " + encontrado.getDescricao() + 
                                  "\nPreço Calculado: R$ " + String.format("%.2f", encontrado.calcularPrecoTotal());
                    
                    JOptionPane.showMessageDialog(this, info, "Detalhes do Serviço", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Serviço não encontrado com este código.", "Aviso", JOptionPane.WARNING_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Por favor, digite um código numérico válido.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        //Exibir dados de todos os serviços
        btnAtualizar.addActionListener(e -> {
            modeloServicos.setRowCount(0);
            for (Servico s : pousada.getServicos()) {
                String tipo = s.getClass().getSimpleName(); 
                modeloServicos.addRow(new Object[]{
                    s.getCodigo(), 
                    tipo, 
                    s.getDescricao(), 
                    String.format("%.2f", s.calcularPrecoTotal())
                });
            }
        });

        return painel;
    }

    private JPanel criarTelaReservas() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBackground(COR_FUNDO_TELA);
        painel.setBorder(new EmptyBorder(30, 40, 30, 40));

        //CABEÇALHO
        JLabel lblTitulo = new JLabel("Gestão de Reservas");
        lblTitulo.setFont(FONTE_TITULO);
        lblTitulo.setForeground(COR_TEXTO_ESCURO);
        painel.add(lblTitulo, BorderLayout.NORTH);

        //PAINEL FORMULÁRIO SUPERIOR
        JPanel painelSuperior = new JPanel(new BorderLayout());
        painelSuperior.setBackground(COR_FUNDO_TELA);
        
        JPanel painelFormulario = new JPanel(new GridBagLayout());
        painelFormulario.setBackground(COR_FUNDO_TELA);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Cód. Reserva
        gbc.gridx = 0; gbc.gridy = 0;
        painelFormulario.add(new JLabel("Cód. Reserva:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0;
        JTextField txtCodigoReserva = new JTextField(10);
        painelFormulario.add(txtCodigoReserva, gbc);

        // CPF do Hóspede
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        painelFormulario.add(new JLabel("CPF Hóspede Responsável:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        JFormattedTextField txtCpfHospede = new JFormattedTextField();
        try {
            javax.swing.text.MaskFormatter mascaraCpf = new javax.swing.text.MaskFormatter("###.###.###-##");
            mascaraCpf.setPlaceholderCharacter('_');
            mascaraCpf.install(txtCpfHospede);
        } catch (java.text.ParseException e) { e.printStackTrace(); }
        painelFormulario.add(txtCpfHospede, gbc);

        // Cód. Acomodação
        gbc.gridx = 0; gbc.gridy = 2;
        painelFormulario.add(new JLabel("Cód. Acomodação:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        JTextField txtCodigoAco = new JTextField(10);
        painelFormulario.add(txtCodigoAco, gbc);

        // Quantidade de Hóspedes
        gbc.gridx = 0; gbc.gridy = 3;
        painelFormulario.add(new JLabel("Qtd. Hóspedes:"), gbc);
        gbc.gridx = 1; gbc.gridy = 3;
        JTextField txtQtdHospedes = new JTextField(5);
        painelFormulario.add(txtQtdHospedes, gbc);

        // Quantidade de Dias
        gbc.gridx = 0; gbc.gridy = 4;
        painelFormulario.add(new JLabel("Qtd. Dias:"), gbc);
        gbc.gridx = 1; gbc.gridy = 4;
        JTextField txtQtdDias = new JTextField(5);
        painelFormulario.add(txtQtdDias, gbc);

        painelSuperior.add(painelFormulario, BorderLayout.CENTER);

        //BOTÕES DE AÇÃO DO FORMULÁRIO
        JPanel painelBotoesForm = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBotoesForm.setBackground(COR_FUNDO_TELA);
        
        JButton btnSalvar = criarBotaoAcao("Salvar Reserva");
        JButton btnBuscar = criarBotaoAcao("Buscar Cód.");
        JButton btnAddServico = criarBotaoAcao("Add Serviço");
        JButton btnExtrato = criarBotaoAcao("Ver Extrato");

        painelBotoesForm.add(btnBuscar);
        painelBotoesForm.add(btnAddServico);
        painelBotoesForm.add(btnExtrato);
        painelBotoesForm.add(btnSalvar);
        painelSuperior.add(painelBotoesForm, BorderLayout.SOUTH);

        painel.add(painelSuperior, BorderLayout.NORTH);

        //TABELA DE RESERVAS
        String[] colunas = {"Cód. Reserva", "Hóspede", "Acomodação", "Qtd. Dias", "Total (R$)"};
        DefaultTableModel modeloReservas = new DefaultTableModel(colunas, 0);
        JTable tabelaReservas = new JTable(modeloReservas);
        estilizarTabela(tabelaReservas);

        JScrollPane scrollPane = new JScrollPane(tabelaReservas);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        scrollPane.getViewport().setBackground(COR_FUNDO_TELA);
        painel.add(scrollPane, BorderLayout.CENTER);

        //BOTÃO DE ATUALIZAR LISTA NO RODAPÉ
        JPanel painelRodape = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelRodape.setBackground(COR_FUNDO_TELA);
        JButton btnAtualizar = criarBotaoAcao("Atualizar Lista");
        painelRodape.add(btnAtualizar);
        painel.add(painelRodape, BorderLayout.SOUTH);

        // =========================================================================
        //                         LÓGICA E EVENTOS DO MENU
        // =========================================================================

        //Cadastrar Reserva
        btnSalvar.addActionListener(e -> {
            try {
                int codigo = Integer.parseInt(txtCodigoReserva.getText().trim());
                String cpf = txtCpfHospede.getText();
                int codAco = Integer.parseInt(txtCodigoAco.getText().trim());
                int qtdHosp = Integer.parseInt(txtQtdHospedes.getText().trim());
                int qtdDias = Integer.parseInt(txtQtdDias.getText().trim());

                //Buscar hóspede cadastrado pelo CPF
                Hospede hospede = pousada.buscarHospedePorCpf(cpf);
                if (hospede == null) {
                    JOptionPane.showMessageDialog(this, "Hóspede não cadastrado com este CPF.", "Erro de Vínculo", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                //Buscar acomodação cadastrada pelo Código
                Acomodacao acomodacao = null;
                for (Acomodacao a : pousada.getAcomodacoes()) {
                    if (a.getCodigo() == codAco) {
                        acomodacao = a;
                        break;
                    }
                }
                if (acomodacao == null) {
                    JOptionPane.showMessageDialog(this, "Acomodação não encontrada com este código.", "Erro de Vínculo", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                //Instancia o objeto. Disparará IllegalArgumentException se qtdHosp > capacidade
                Reserva novaReserva = new Reserva(codigo, hospede, acomodacao, qtdHosp, qtdDias);
                pousada.cadastrarReserva(novaReserva);

                JOptionPane.showMessageDialog(this, "Reserva vinculada com sucesso!");

                //Limpeza dos campos
                txtCodigoReserva.setText("");
                txtCpfHospede.setValue(null);
                txtCodigoAco.setText("");
                txtQtdHospedes.setText("");
                txtQtdDias.setText("");

                atualizarTabelaReservas(modeloReservas);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Preencha os campos numéricos de forma correta.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                // Captura o erro obrigatório caso exceda a capacidade máxima da acomodação
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro de Consistência", JOptionPane.WARNING_MESSAGE);
            }
        });

        //Exibir dados de uma reserva
        btnBuscar.addActionListener(e -> {
            String codStr = JOptionPane.showInputDialog(this, "Digite o código da reserva para exibir os dados:");
            if (codStr != null && !codStr.trim().isEmpty()) {
                try {
                    int cod = Integer.parseInt(codStr.trim());
                    Reserva r = pousada.buscarReservaPorCodigo(cod);
                    if (r != null) {
                        String info = "Cód. Reserva: " + r.getCodigo() +
                                      "\nHóspede Responsável: " + r.getHospedeResponsavel().getNome() +
                                      "\nAcomodação Contratada: " + r.getAcomodacao().getClass().getSimpleName() + " (Nº " + r.getAcomodacao().getCodigo() + ")" +
                                      "\nQuantidade de Pessoas: " + r.getQtdHospedes() +
                                      "\nTotal de Diárias: " + r.getQtdDias() + " dias" +
                                      "\nServiços Extras Atrelados: " + r.getServicosContratados().size();
                        JOptionPane.showMessageDialog(this, info, "Dados da Reserva", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Nenhuma reserva localizada com este código.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Código inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //Adicionar serviço a uma reserva
        btnAddServico.addActionListener(e -> {
            String codResStr = JOptionPane.showInputDialog(this, "Digite o código da reserva:");
            if (codResStr == null || codResStr.trim().isEmpty()) return;

            String codSerStr = JOptionPane.showInputDialog(this, "Digite o código do serviço que deseja adicionar:");
            if (codSerStr == null || codSerStr.trim().isEmpty()) return;

            try {
                int codRes = Integer.parseInt(codResStr.trim());
                int codSer = Integer.parseInt(codSerStr.trim());

                Reserva r = pousada.buscarReservaPorCodigo(codRes);
                if (r == null) {
                    JOptionPane.showMessageDialog(this, "Reserva informada não existe.", "Erro", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Servico servico = null;
                for (Servico s : pousada.getServicos()) {
                    if (s.getCodigo() == codSer) {
                        servico = s;
                        break;
                    }
                }
                if (servico == null) {
                    JOptionPane.showMessageDialog(this, "Serviço informado não existe.", "Erro", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                //Adiciona o serviço na lista da reserva correspondente
                r.adicionarServico(servico);
                JOptionPane.showMessageDialog(this, "Serviço '" + servico.getDescricao() + "' adicionado à Reserva " + codRes + " com sucesso!");
                atualizarTabelaReservas(modeloReservas);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Os códigos inseridos devem ser numéricos.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        //Exibir extrato de uma reserva
        btnExtrato.addActionListener(e -> {
            String codStr = JOptionPane.showInputDialog(this, "Digite o código da reserva para o Extrato:");
            if (codStr != null && !codStr.trim().isEmpty()) {
                try {
                    int cod = Integer.parseInt(codStr.trim());
                    Reserva r = pousada.buscarReservaPorCodigo(cod);
                    if (r != null) {
                        StringBuilder extrato = new StringBuilder();
                        extrato.append("=========================================\n");
                        extrato.append("          EXTRATO DA RESERVA: ").append(r.getCodigo()).append("\n");
                        extrato.append("=========================================\n");
                        extrato.append("Responsável: ").append(r.getHospedeResponsavel().getNome()).append("\n");
                        extrato.append("Acomodação: ").append(r.getAcomodacao().getClass().getSimpleName())
                               .append(" (Código: ").append(r.getAcomodacao().getCodigo()).append(")\n");
                        extrato.append("Período: ").append(r.getQtdDias()).append(" dias utilizados\n");
                        
                        double precoDiarias = r.getAcomodacao().calcularPrecoTotalDiarias(r.getQtdDias());
                        extrato.append(String.format("Preço Total Diárias: R$ %.2f\n\n", precoDiarias));
                        
                        extrato.append("--- LISTA DE SERVIÇOS EXTRAS ---\n");
                        if (r.getServicosContratados().isEmpty()) {
                            extrato.append("Nenhum serviço consumido até o momento.\n");
                        } else {
                            for (Servico s : r.getServicosContratados()) {
                                extrato.append(String.format("- %s (%s): R$ %.2f\n", 
                                    s.getDescricao(), s.getClass().getSimpleName(), s.calcularPrecoTotal()));
                            }
                        }
                        extrato.append("-----------------------------------------\n");
                        extrato.append(String.format("VALOR TOTAL ACUMULADO: R$ %.2f\n", r.calcularPrecoTotalReserva()));
                        extrato.append("=========================================");

                        JOptionPane.showMessageDialog(this, extrato.toString(), "Extrato Consolidado", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Reserva não encontrada.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Código numérico inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //Exibir dados de todas as reservas
        btnAtualizar.addActionListener(e -> atualizarTabelaReservas(modeloReservas));

        return painel;
    }

    // Método auxiliar para preencher a listagem de reservas
    private void atualizarTabelaReservas(DefaultTableModel modelo) {
        modelo.setRowCount(0);
        for (Reserva r : pousada.getReservas()) {
            modelo.addRow(new Object[]{
                r.getCodigo(),
                r.getHospedeResponsavel().getNome(),
                r.getAcomodacao().getClass().getSimpleName(),
                r.getQtdDias(),
                String.format("%.2f", r.calcularPrecoTotalReserva())
            });
        }
    }

    private JPanel criarTelaHospedes() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBackground(COR_FUNDO_TELA);
        painel.setBorder(new EmptyBorder(30, 40, 30, 40));

        //CABEÇALHO
        JLabel lblTitulo = new JLabel("Gestão de Hóspedes");
        lblTitulo.setFont(FONTE_TITULO);
        lblTitulo.setForeground(COR_TEXTO_ESCURO);
        painel.add(lblTitulo, BorderLayout.NORTH);

        //FORMULÁRIO
        JPanel painelSuperior = new JPanel(new BorderLayout());
        painelSuperior.setBackground(COR_FUNDO_TELA);
        
        JPanel painelFormulario = new JPanel(new GridBagLayout());
        painelFormulario.setBackground(COR_FUNDO_TELA);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        //Campos do Formulário
        gbc.gridx = 0; gbc.gridy = 0;
        painelFormulario.add(new JLabel("Nome Completo:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0;
        JTextField txtNome = new JTextField(20);
        painelFormulario.add(txtNome, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        painelFormulario.add(new JLabel("CPF:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        JFormattedTextField txtCpf = new JFormattedTextField();
        try {
            javax.swing.text.MaskFormatter mascaraCpf = new javax.swing.text.MaskFormatter("###.###.###-##");
            mascaraCpf.setPlaceholderCharacter('_');
            mascaraCpf.install(txtCpf);
        } catch (java.text.ParseException e) { e.printStackTrace(); }
        painelFormulario.add(txtCpf, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        painelFormulario.add(new JLabel("Idade:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        JTextField txtIdade = new JTextField(5);
        painelFormulario.add(txtIdade, gbc);

        painelSuperior.add(painelFormulario, BorderLayout.CENTER);

        //BOTÕES DO FORMULÁRIO
        JPanel painelBotoesForm = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBotoesForm.setBackground(COR_FUNDO_TELA);
        JButton btnSalvar = criarBotaoAcao("Salvar Hóspede");
        JButton btnBuscar = criarBotaoAcao("Buscar por CPF");
        
        painelBotoesForm.add(btnBuscar);
        painelBotoesForm.add(btnSalvar);
        painelSuperior.add(painelBotoesForm, BorderLayout.SOUTH);

        painel.add(painelSuperior, BorderLayout.NORTH);

        //TABELA
        String[] colunas = {"Nome", "CPF", "Idade"};
        DefaultTableModel modeloHospedes = new DefaultTableModel(colunas, 0);
        JTable tabelaHospedes = new JTable(modeloHospedes);
        estilizarTabela(tabelaHospedes);
        
        JScrollPane scrollPane = new JScrollPane(tabelaHospedes);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        scrollPane.getViewport().setBackground(COR_FUNDO_TELA);
        painel.add(scrollPane, BorderLayout.CENTER);

        // --- AÇÕES DOS BOTÕES (A LÓGICA DE FATO) ---

        //Cadastrar Hóspede
        btnSalvar.addActionListener(e -> {
            try {
                String nome = txtNome.getText();
                String cpf = txtCpf.getText();
                int idade = Integer.parseInt(txtIdade.getText().trim());

                // Usa as exceções criadas para evitar idades negativas
                Hospede novoHospede = new Hospede(nome, idade, cpf); 
                pousada.cadastrarHospede(novoHospede);

                JOptionPane.showMessageDialog(this, "Hóspede cadastrado com sucesso!");
                
                // Limpa os campos
                txtNome.setText("");
                txtCpf.setValue(null);
                txtIdade.setText("");
                
                // Atualiza a tabela na tela
                atualizarTabelaHospedes(modeloHospedes);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Idade inválida. Digite um número.", "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro de Validação", JOptionPane.WARNING_MESSAGE);
            }
        });

        //Exibir dados de um hóspede
        btnBuscar.addActionListener(e -> {
            String cpfBusca = JOptionPane.showInputDialog(this, "Digite o CPF do hóspede para busca:");
            if (cpfBusca != null && !cpfBusca.trim().isEmpty()) {
                Hospede h = pousada.buscarHospedePorCpf(cpfBusca);
                if (h != null) {
                    JOptionPane.showMessageDialog(this, "Dados Encontrados:\n\nNome: " + h.getNome() + "\nIdade: " + h.getIdade() + "\nCPF: " + h.getCpf(), "Resultado da Busca", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Hóspede não encontrado com este CPF.", "Aviso", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        return painel;
    }

    // Método auxiliar para preencher a tabela
    private void atualizarTabelaHospedes(DefaultTableModel modelo) {
        modelo.setRowCount(0); // Limpa as linhas antigas
        for (Hospede h : pousada.getHospedes()) {
            modelo.addRow(new Object[]{h.getNome(), h.getCpf(), h.getIdade()});
        }
    }

    // Tela genérica de placeholder para as outras abas
    private JPanel criarTelaEmBranco(String tituloStr) {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(COR_FUNDO_TELA);
        painel.setBorder(new EmptyBorder(30, 40, 30, 40));

        JLabel titulo = new JLabel(tituloStr);
        titulo.setFont(FONTE_TITULO);
        titulo.setForeground(COR_TEXTO_ESCURO);
        painel.add(titulo, BorderLayout.NORTH);
        
        JLabel construcao = new JLabel("Formulário e Tabelas aqui...", SwingConstants.CENTER);
        construcao.setFont(FONTE_PADRAO);
        painel.add(construcao, BorderLayout.CENTER);

        return painel;
    }

    //Método Principal (Start)
    public static void main(String[] args) {
        // Tenta aplicar o visual mais bonito nativo do sistema operacional (Windows/Mac)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Não foi possível carregar o tema do sistema.");
        }

        SwingUtilities.invokeLater(() -> {
            SistemaPousadaUI app = new SistemaPousadaUI();
            app.setVisible(true);
        });
    }
}