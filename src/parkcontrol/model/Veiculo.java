package parkcontrol.model;

public abstract class Veiculo {
    private String placa;
    private String modelo;
    private int horaEntrada;

    public Veiculo(String placa, String modelo, int horaEntrada) {
        this.placa = placa;
        this.modelo = modelo;
        this.horaEntrada = horaEntrada;
    }

    public abstract double calcularTarifa(int horaSaida);

    public String getPlaca() { return placa; }
    public String getModelo() { return modelo; }
    public int getHoraEntrada() { return horaEntrada; }
}