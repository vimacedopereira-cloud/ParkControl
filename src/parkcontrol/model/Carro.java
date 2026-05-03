package parkcontrol.model;

public class Carro extends Veiculo {
   
    public Carro(String placa, String modelo, double horaEntrada) {
        super(placa, modelo, horaEntrada);
    }

   
    public double calcularTarifa(double horaSaida) { 
        double minutosDecorridos = horaSaida - getHoraEntrada();
        double horasTotal = minutosDecorridos / 60.0;
        return horasTotal * 10.0;
    }
}
