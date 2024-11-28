package Monitores;

import views.Mesa;
import views.Cliente;
import java.util.List;
import java.util.Queue;
import java.util.LinkedList;

public class MonitorMesas {

    private final List<Mesa> mesas;
    private final Queue<Cliente> clientesEsperando = new LinkedList<>();  // Cola de clientes esperando

    public MonitorMesas(List<Mesa> mesas) {
        this.mesas = mesas;
    }

    // Método sincronizado para asignar una mesa al cliente
    public synchronized Mesa asignarMesa(Cliente cliente) {
        while (true) {
            for (Mesa mesa : mesas) {
                if (!mesa.estaOcupada()) {
                    mesa.ocupar();
                    mesa.asignarCliente(cliente);
                    clientesEsperando.add(cliente);  // Añadir a la cola de clientes esperando
                    System.out.println(Thread.currentThread().getName() + " se le asignó una mesa: " + mesa.getMesa().getPosition());
                    notifyAll();  // Notificar al mesero
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
        clientesEsperando.remove(); // Eliminar el cliente de la cola cuando termine
        System.out.println(Thread.currentThread().getName() + " liberó una mesa: " + mesa.getMesa().getPosition());
        notifyAll();  // Notificar a los clientes en espera
    }

    // Método para obtener el siguiente cliente que debe ser atendido
    public synchronized Cliente obtenerSiguienteCliente() {
        return clientesEsperando.poll(); // Obtener el siguiente cliente en la cola
    }
}
