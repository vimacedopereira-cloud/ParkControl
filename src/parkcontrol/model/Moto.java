package parkcontrol.model;

public class Moto extends Veiculo {
    
    
    public Moto(String placa, String modelo, double horaEntrada) {
        super(placa, modelo, horaEntrada);
    }

    public double calcularTarifa(double horaSaidaEmMinutos) {
        
        double minutosDecorridos = horaSaidaEmMinutos - getHoraEntrada();   
        double horasTotal = minutosDecorridos / 60.0;
        return horasTotal * 5.0; 
    }
}
