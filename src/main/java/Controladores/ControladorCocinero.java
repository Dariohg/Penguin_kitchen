package Controladores;

import views.Cocinero;
import Modelos.Orden;

public class ControladorCocinero {

    private final Cocinero cocinero;

    public ControladorCocinero(Cocinero cocinero) {
        this.cocinero = cocinero;
    }

    // Método para cocinar una orden
    public void cocinarOrden(Orden orden) {
        System.out.println("Cocinero " + cocinero.getNombre() + " está cocinando la orden " + orden.getId());
        cocinero.setEstado("Cocinando: " + orden.getId());
    }

    // Método para notificar que la orden está lista
    public void notificarOrdenLista(Orden orden) {
        System.out.println("Cocinero " + cocinero.getNombre() + " completó la orden " + orden.getId());
        cocinero.setEstado("Orden lista: " + orden.getId());
    }
}
