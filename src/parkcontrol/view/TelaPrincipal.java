package parkcontrol.view;



import javax.swing.*;

import java.awt.*;

import parkcontrol.controller.GerenciadorEstacionamento;

import parkcontrol.model.Carro;

import parkcontrol.model.Moto;

import parkcontrol.model.Veiculo;



public class TelaPrincipal extends JFrame {

    private Color corFundo = Color.WHITE; 

    private Color corDestaqueRed = new Color(200, 0, 0); 

    private Color corTextoPreto = new Color(30, 30, 30);

    private Color corBotaoPreto = new Color(0, 0, 0);



    private JTextField txtPlaca = new JTextField();

    private JTextField txtModelo = new JTextField();

    private JTextField txtHora = new JTextField();

    private JComboBox<String> comboTipo = new JComboBox<>(new String[]{"Carro", "Moto"});

    private JButton btnEntrada = new JButton("Registrar Entrada");

    private JButton btnSaida = new JButton("Calcular Saída");

    private JTextArea areaListagem = new JTextArea();



    private GerenciadorEstacionamento gerenciador = new GerenciadorEstacionamento();



    public TelaPrincipal() {

        setTitle("ParkControl - Sistema de Gerenciamento");

        setSize(700, 450);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(null);

        getContentPane().setBackground(corFundo);

        setLocationRelativeTo(null);



        Font fonteLabels = new Font("Arial", Font.BOLD, 13);



        

        JLabel lblTipo = new JLabel("Tipo de Veículo:");

        lblTipo.setBounds(25, 30, 120, 25);

        lblTipo.setFont(fonteLabels);

        add(lblTipo);



        comboTipo.setBounds(140, 30, 150, 30);

        add(comboTipo);



        JLabel lblPlaca = new JLabel("Placa:");

        lblPlaca.setBounds(25, 80, 100, 25);

        lblPlaca.setFont(fonteLabels);

        add(lblPlaca);



        txtPlaca.setBounds(140, 80, 150, 30);

        add(txtPlaca);



        JLabel lblModelo = new JLabel("Modelo:");

        lblModelo.setBounds(25, 130, 100, 25);

        lblModelo.setFont(fonteLabels);

        add(lblModelo);



        txtModelo.setBounds(140, 130, 150, 30);

        add(txtModelo);



        JLabel lblHora = new JLabel("Hora de Entrada:");

        lblHora.setBounds(25, 180, 120, 25);

        lblHora.setFont(fonteLabels);

        add(lblHora);



        txtHora.setBounds(140, 180, 150, 30);

        add(txtHora);



       

        btnEntrada.setBounds(25, 250, 265, 45);

        btnEntrada.setBackground(corBotaoPreto);

        btnEntrada.setForeground(Color.WHITE);

        btnEntrada.setFont(new Font("Arial", Font.BOLD, 14));

        btnEntrada.setFocusPainted(false);

        btnEntrada.setBorder(BorderFactory.createLineBorder(corDestaqueRed, 1));

        add(btnEntrada);



   

        btnSaida.setBounds(25, 310, 265, 45);

        btnSaida.setBackground(corDestaqueRed);

        btnSaida.setForeground(Color.WHITE);

        btnSaida.setFont(new Font("Arial", Font.BOLD, 14));

        btnSaida.setFocusPainted(false);

        btnSaida.setBorder(BorderFactory.createLineBorder(corBotaoPreto, 1));

        add(btnSaida);



       

        JLabel lblPatio = new JLabel("PÁTIO ATUAL");

        lblPatio.setBounds(350, 30, 200, 25);

        lblPatio.setFont(new Font("Arial", Font.BOLD, 15));

        lblPatio.setForeground(corDestaqueRed);

        add(lblPatio);



        areaListagem.setFont(new Font("Monospaced", Font.PLAIN, 13));

        areaListagem.setForeground(corTextoPreto);

        

        JScrollPane scroll = new JScrollPane(areaListagem);

        scroll.setBounds(350, 60, 310, 315);

     

        scroll.setBorder(BorderFactory.createTitledBorder(

            BorderFactory.createLineBorder(corBotaoPreto, 1), "Veículos Estacionados"));

        add(scroll);



       

        btnEntrada.addActionListener(e -> {

            try {

                String placa = txtPlaca.getText();

                String modelo = txtModelo.getText();

                int hora = Integer.parseInt(txtHora.getText());

                String tipo = (String) comboTipo.getSelectedItem();



                if (tipo.equals("Carro")) {

                    gerenciador.adicionarVeiculo(new Carro(placa, modelo, hora));

                } else {

                    gerenciador.adicionarVeiculo(new Moto(placa, modelo, hora));

                }



                atualizarLista();

                JOptionPane.showMessageDialog(this, "Veículo registrado com sucesso!");

                

                txtPlaca.setText("");

                txtModelo.setText("");

                txtHora.setText("");

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(this, "Erro: Insira uma hora válida.");

            }

        });



        btnSaida.addActionListener(e -> {

            String placaBusca = JOptionPane.showInputDialog("Digite a placa:");

            if(placaBusca == null || placaBusca.isEmpty()) return;

            

            try {

                String inputHora = JOptionPane.showInputDialog("Hora de saída:");

                if(inputHora == null) return;

                int horaSaida = Integer.parseInt(inputHora);



                for (Veiculo v : gerenciador.listarTodos()) {

                    if (v.getPlaca().equalsIgnoreCase(placaBusca)) {

                        double total = v.calcularTarifa(horaSaida);

                        JOptionPane.showMessageDialog(this, "Pagamento Calculado\nTotal: R$ " + total);

                        gerenciador.removerVeiculo(placaBusca);

                        atualizarLista();

                        return;

                    }

                }

                JOptionPane.showMessageDialog(this, "Veículo não encontrado.");

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(this, "Erro: Valor de hora inválido.");

            }

        });

        

        atualizarLista();

    }



    private void atualizarLista() {

        StringBuilder texto = new StringBuilder();

        texto.append(String.format("%-10s | %-8s | %-5s\n", "PLACA", "TIPO", "HORA"));

        texto.append("------------------------------\n");

        for (Veiculo v : gerenciador.listarTodos()) {

            String tipo = (v instanceof Carro) ? "Carro" : "Moto";

            texto.append(String.format("%-10s | %-8s | %-2dh\n", v.getPlaca(), tipo, v.getHoraEntrada()));

        }

        areaListagem.setText(texto.toString());

    }

    public static void main(String[] args) {

        TelaPrincipal tela = new TelaPrincipal();

        tela.setVisible(true);

    }

}
