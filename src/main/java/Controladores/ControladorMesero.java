package Controladores;

import views.Cliente;
import views.Mesero;
import views.Mesa;

public class ControladorMesero {

    private final Mesero mesero;

    public ControladorMesero(Mesero mesero) {
        this.mesero = mesero;
    }

    // Método para mover al mesero hacia una mesa
    public void moverAMesa(Mesa mesa) {
        // Asegúrate de que la mesa no sea nula antes de mover
        if (mesa != null && mesa.estaOcupada()) {
            mesero.moverAMesa(mesa);
        }
    }

    // Método para simular la acción de atender al cliente
    public void atenderCliente(Cliente cliente) {
        System.out.println("Atendiendo al cliente...");
        // Aquí podrías agregar lógica adicional para mostrar que el mesero está atendiendo
    }
}
