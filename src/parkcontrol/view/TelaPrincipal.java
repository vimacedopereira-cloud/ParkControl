package parkcontrol.view;
import javax.swing.*;
import parkcontrol.controller.GerenciadorEstacionamento;
import parkcontrol.model.Carro;
import parkcontrol.model.Moto;
import parkcontrol.model.Veiculo;

public class TelaPrincipal extends JFrame {

    private JTextField txtPlaca = new JTextField();
    private JTextField txtModelo = new JTextField();
    private JTextField txtHora = new JTextField();
    private JComboBox<String> comboTipo = new JComboBox<>(new String[]{"Carro", "Moto"});
    private JButton btnEntrada = new JButton("Registrar Entrada");
    private JButton btnSaida = new JButton("Calcular Saída");

    private GerenciadorEstacionamento gerenciador = new GerenciadorEstacionamento();

    public TelaPrincipal() {
        setTitle("Estacionamento ParkControl");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null); 
        setLocationRelativeTo(null); 
        JLabel lblTipo = new JLabel("Tipo:");
        lblTipo.setBounds(20, 20, 100, 25);
        add(lblTipo);
        comboTipo.setBounds(130, 20, 150, 25);
        add(comboTipo);

        JLabel lblPlaca = new JLabel("Placa:");
        lblPlaca.setBounds(20, 60, 100, 25);
        add(lblPlaca);
        txtPlaca.setBounds(130, 60, 150, 25);
        add(txtPlaca);

        JLabel lblModelo = new JLabel("Modelo:");
        lblModelo.setBounds(20, 100, 100, 25);
        add(lblModelo);
        txtModelo.setBounds(130, 100, 150, 25);
        add(txtModelo);

        JLabel lblHora = new JLabel("Hora Entrada:");
        lblHora.setBounds(20, 140, 100, 25);
        add(lblHora);
        txtHora.setBounds(130, 140, 150, 25);
        add(txtHora);

        btnEntrada.setBounds(20, 200, 150, 30);
        add(btnEntrada);

        btnSaida.setBounds(180, 200, 150, 30);
        add(btnSaida);
        btnEntrada.addActionListener(e -> {
            String placa = txtPlaca.getText();
            String modelo = txtModelo.getText();
            int hora = Integer.parseInt(txtHora.getText());
            String tipo = (String) comboTipo.getSelectedItem();

            if (tipo.equals("Carro")) {
                gerenciador.adicionarVeiculo(new Carro(placa, modelo, hora));
            } else {
                gerenciador.adicionarVeiculo(new Moto(placa, modelo, hora));
            }

            JOptionPane.showMessageDialog(this, "Entrada registrada!");
            
            txtPlaca.setText("");
            txtModelo.setText("");
            txtHora.setText("");
        });

        btnSaida.addActionListener(e -> {
            String placaBusca = JOptionPane.showInputDialog("Qual a placa do veículo?");
            int horaSaida = Integer.parseInt(JOptionPane.showInputDialog("Hora de saída?"));

            for (Veiculo v : gerenciador.listarTodos()) {
                if (v.getPlaca().equalsIgnoreCase(placaBusca)) {
                    double total = v.calcularTarifa(horaSaida);
                    JOptionPane.showMessageDialog(this, "Valor a pagar: R$ " + total);
                    gerenciador.removerVeiculo(placaBusca);
                    return;
                }
            }
            JOptionPane.showMessageDialog(this, "Veículo não encontrado!");
        });
    }
    public static void main(String[] args) {
        TelaPrincipal tela = new TelaPrincipal();
        tela.setVisible(true);
    }
}