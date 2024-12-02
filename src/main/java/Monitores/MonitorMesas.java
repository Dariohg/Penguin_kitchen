package Monitores;

import views.Mesa;
import views.Cliente;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class MonitorMesas {

    private final List<Mesa> mesas;
    private final Queue<Cliente> clientesEsperando = new LinkedBlockingQueue<>(); // Cola de clientes esperando

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
                    cliente.setMesa(mesa);
                    synchronized (clientesEsperando) {
                        clientesEsperando.add(cliente); // Añadir a la cola de clientes esperando
                        clientesEsperando.notifyAll(); // Notificar al mesero
                    }
                    System.out.println(Thread.currentThread().getName() + " se le asignó una mesa: " + mesa.getMesa().getPosition());
                    return mesa;
                }
            }

            try {
                System.out.println(Thread.currentThread().getName() + " esperando una mesa...");
                wait(); // Esperar hasta que haya mesas disponibles
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        }
    }

    public synchronized void liberarMesa(Mesa mesa) {
        mesa.liberar(); // Liberar la mesa
        synchronized (clientesEsperando) {
            // Solo eliminar el primer cliente si coincide con la mesa liberada
            if (!clientesEsperando.isEmpty() && clientesEsperando.peek().getMesa() == mesa) {
                clientesEsperando.poll(); // Eliminar el cliente de la cola
            }
            clientesEsperando.notifyAll(); // Notificar a los clientes en espera
        }
        System.out.println(Thread.currentThread().getName()  + " liberó una mesa: " + mesa.getMesa().getPosition());
        notifyAll(); // Notificar a los clientes en espera
    }

    public Cliente obtenerSiguienteCliente() {
        synchronized (clientesEsperando) {
            while (clientesEsperando.isEmpty()) {
                try {
                    clientesEsperando.wait(); // Esperar hasta que haya clientes en la cola
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return null;
                }
            }
            
            // Imprimir la cola de clientes para depuración
            System.out.println("Clientes esperando: " + clientesEsperando);
            
            // Retornar el primer cliente de la cola
            return clientesEsperando.peek();
        }
    }
}