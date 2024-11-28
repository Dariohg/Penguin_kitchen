package Monitores;

import views.Mesa;
import java.util.List;

public class MonitorMesas {

    private final List<Mesa> mesas; // Lista de mesas

    public MonitorMesas(List<Mesa> mesas) {
        this.mesas = mesas;
    }

    // Método sincronizado para asignar una mesa al cliente
    public synchronized Mesa asignarMesa() {
        while (true) {
            for (Mesa mesa : mesas) {
                if (!mesa.estaOcupada()) {
                    mesa.ocupar();
                    System.out.println(Thread.currentThread().getName() + " se le asignó una mesa: " + mesa.getMesa().getPosition());
                    return mesa;
                }
            }

            try {
                System.out.println(Thread.currentThread().getName() + " esperando una mesa...");
                wait();  // Esperar hasta que haya mesas disponibles
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        }
    }

    // Método sincronizado para liberar la mesa cuando el cliente termina
    public synchronized void liberarMesa(Mesa mesa) {
        mesa.liberar();  // Liberar la mesa
        System.out.println(Thread.currentThread().getName() + " liberó una mesa: " + mesa.getMesa().getPosition());
        notifyAll();  // Notificar a los clientes en espera
    }
}
