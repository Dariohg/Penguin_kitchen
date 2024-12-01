package Monitores;

import Modelos.Orden;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class MonitorOrdenes {
    private final Queue<Orden> bufferOrdenes;

    // Constructor que acepta una cola externa
    public MonitorOrdenes() {
        this.bufferOrdenes = new LinkedBlockingQueue<>();
    }

    public synchronized void agregarOrden(Orden orden) {
        bufferOrdenes.add(orden);
        notifyAll(); // Notificar que hay una nueva orden
    }

    public synchronized Orden obtenerSiguienteOrden() {
        while (bufferOrdenes.isEmpty()) {
            try {
                wait(); // Esperar hasta que haya órdenes disponibles
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return bufferOrdenes.poll();
    }
}