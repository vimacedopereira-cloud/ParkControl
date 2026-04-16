package parkcontrol.controller;

import java.util.ArrayList;
import java.util.List;

import parkcontrol.model.Veiculo;

public class GerenciadorEstacionamento {

    private List<Veiculo> patio;

    public GerenciadorEstacionamento() {
        this.patio = new ArrayList<>();
    }

    public void adicionarVeiculo(Veiculo v) {
        patio.add(v);
    }

    public List<Veiculo> listarTodos() {
        return patio;
    }
    
    public void removerVeiculo(String placa) {
        patio.removeIf(v -> v.getPlaca().equalsIgnoreCase(placa));
    }
}