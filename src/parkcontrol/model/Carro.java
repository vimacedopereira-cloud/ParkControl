package parkcontrol.model;

public class Carro extends Veiculo {
    public Carro(String placa, String modelo, int horaEntrada) {
        super(placa, modelo, horaEntrada);
    }
    public double calcularTarifa(int horaSaida) {
        int tempo = horaSaida - getHoraEntrada();
        return tempo * 10.0; 
    }
}
