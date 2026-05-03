package parkcontrol.view;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
import java.util.Date;
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
    
   
    private JSpinner spinnerHora;
    
    private JComboBox<String> comboTipo = new JComboBox<>(new String[]{"Carro", "Moto"});
    private JButton btnEntrada = new JButton("Registrar Entrada");
    private JButton btnSaida = new JButton("Calcular Saída");
    private JButton btnEditar = new JButton("Editar Veículo"); 
    private JTextArea areaListagem = new JTextArea();

    private GerenciadorEstacionamento gerenciador = new GerenciadorEstacionamento();

    public TelaPrincipal() {
        setTitle("ParkControl - Sistema de Gerenciamento");
        setSize(700, 520); 
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

        JLabel lblHora = new JLabel("Horário:"); 
        lblHora.setBounds(25, 180, 120, 25);
        lblHora.setFont(fonteLabels);
        add(lblHora);

       
        SpinnerDateModel model = new SpinnerDateModel();
        spinnerHora = new JSpinner(model);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(spinnerHora, "HH:mm");
        spinnerHora.setEditor(editor);
        spinnerHora.setBounds(140, 180, 150, 30);
        add(spinnerHora);

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

        btnEditar.setBounds(25, 370, 265, 45);
        btnEditar.setBackground(Color.WHITE);
        btnEditar.setForeground(Color.BLACK);
        btnEditar.setFont(new Font("Arial", Font.BOLD, 14));
        btnEditar.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        add(btnEditar);

        JLabel lblPatio = new JLabel("PÁTIO ATUAL");
        lblPatio.setBounds(350, 30, 200, 25);
        lblPatio.setFont(new Font("Arial", Font.BOLD, 15));
        lblPatio.setForeground(corDestaqueRed);
        add(lblPatio);

        areaListagem.setFont(new Font("Monospaced", Font.PLAIN, 13));
        areaListagem.setForeground(corTextoPreto);
        
        JScrollPane scroll = new JScrollPane(areaListagem);
        scroll.setBounds(350, 60, 310, 355);
        scroll.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(corBotaoPreto, 1), "Veículos Estacionados"));
        add(scroll);

       
        btnEntrada.addActionListener(e -> {
            String placa = txtPlaca.getText();
            String modelo = txtModelo.getText();
            double horaEmMinutos = getHoraSpinnerEmMinutos();
            String tipo = (String) comboTipo.getSelectedItem();

            if (tipo.equals("Carro")) {
                gerenciador.adicionarVeiculo(new Carro(placa, modelo, horaEmMinutos));
            } else {
                gerenciador.adicionarVeiculo(new Moto(placa, modelo, horaEmMinutos));
            }

            atualizarLista();
            JOptionPane.showMessageDialog(this, "Veículo registrado!");
            limparCampos();
        });

        
        btnEditar.addActionListener(e -> {
            String placaBusca = JOptionPane.showInputDialog("Digite a placa do veículo para editar:");
            if (placaBusca == null) return;

            Veiculo encontrado = null;
            for (Veiculo v : gerenciador.listarTodos()) {
                if (v.getPlaca().equalsIgnoreCase(placaBusca)) {
                    encontrado = v;
                    break;
                }
            }

            if (encontrado != null) {
                txtPlaca.setText(encontrado.getPlaca());
                txtModelo.setText(encontrado.getModelo());
                
               
                double minutos = encontrado.getHoraEntrada();
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.HOUR_OF_DAY, (int)(minutos / 60));
                cal.set(Calendar.MINUTE, (int)(minutos % 60));
                spinnerHora.setValue(cal.getTime());

                comboTipo.setSelectedItem(encontrado instanceof Carro ? "Carro" : "Moto");
                gerenciador.removerVeiculo(encontrado.getPlaca());
                JOptionPane.showMessageDialog(this, "Dados carregados! Altere e clique em 'Registrar Entrada'.");
            } else {
                JOptionPane.showMessageDialog(this, "Veículo não encontrado.");
            }
        });

        
        btnSaida.addActionListener(e -> {
            String placaBusca = JOptionPane.showInputDialog("Digite a placa:");
            if(placaBusca == null || placaBusca.isEmpty()) return;
            
            
            SpinnerDateModel sdm = new SpinnerDateModel();
            JSpinner spSaida = new JSpinner(sdm);
            spSaida.setEditor(new JSpinner.DateEditor(spSaida, "HH:mm"));
            
            int option = JOptionPane.showOptionDialog(this, spSaida, "Selecione a Hora de Saída", 
                         JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

            if (option == JOptionPane.OK_OPTION) {
                Date dataSaida = (Date) spSaida.getValue();
                Calendar cal = Calendar.getInstance();
                cal.setTime(dataSaida);
                double horaSaidaMinutos = (cal.get(Calendar.HOUR_OF_DAY) * 60) + cal.get(Calendar.MINUTE);

                for (Veiculo v : gerenciador.listarTodos()) {
                    if (v.getPlaca().equalsIgnoreCase(placaBusca)) {
                        double total = v.calcularTarifa(horaSaidaMinutos);
                        JOptionPane.showMessageDialog(this, "Pagamento Calculado\nTotal: R$ " + String.format("%.2f", total));
                        gerenciador.removerVeiculo(placaBusca);
                        atualizarLista();
                        return;
                    }
                }
                JOptionPane.showMessageDialog(this, "Veículo não encontrado.");
            }
        });
        
        atualizarLista();
    }

   
    private double getHoraSpinnerEmMinutos() {
        Date dataSelecionada = (Date) spinnerHora.getValue();
        Calendar cal = Calendar.getInstance();
        cal.setTime(dataSelecionada);
        return (cal.get(Calendar.HOUR_OF_DAY) * 60.0) + cal.get(Calendar.MINUTE);
    }

    private String formatarMinutosParaHora(double totalMinutos) {
        int h = (int) totalMinutos / 60;
        int m = (int) totalMinutos % 60;
        return String.format("%02d:%02d", h, m);
    }

    private void limparCampos() {
        txtPlaca.setText("");
        txtModelo.setText("");
        
        spinnerHora.setValue(new Date());
    }

    private void atualizarLista() {
        StringBuilder texto = new StringBuilder();
        texto.append(String.format("%-10s | %-8s | %-5s\n", "PLACA", "TIPO", "ENTRADA"));
        texto.append("------------------------------\n");
        for (Veiculo v : gerenciador.listarTodos()) {
            String tipo = (v instanceof Carro) ? "Carro" : "Moto";
            String horaFormatada = formatarMinutosParaHora(v.getHoraEntrada());
            texto.append(String.format("%-10s | %-8s | %-5s\n", v.getPlaca(), tipo, horaFormatada));
        }
        areaListagem.setText(texto.toString());
    }

    public static void main(String[] args) {
        TelaPrincipal tela = new TelaPrincipal();
        tela.setVisible(true);
    }
}
