package parkcontrol.model;

public abstract class Veiculo {
    private String placa;
    private String modelo;
    private double horaEntrada; 

    public Veiculo(String placa, String modelo, double horaEntrada) {
        this.placa = placa;
        this.modelo = modelo;
        this.horaEntrada = horaEntrada;
    }

    public abstract double calcularTarifa(double horaSaida);

    public String getPlaca() { return placa; }
    public String getModelo() { return modelo; }
    public double getHoraEntrada() { return horaEntrada; }
}
