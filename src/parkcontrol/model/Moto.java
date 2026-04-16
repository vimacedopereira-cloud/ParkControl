package parkcontrol.model;

public class Moto extends Veiculo {
    public Moto(String placa, String modelo, int horaEntrada) {
        super(placa, modelo, horaEntrada);
    }
    public double calcularTarifa(int horaSaida) {
        int tempo = horaSaida - getHoraEntrada();
        return tempo * 5.0; 
    }
}