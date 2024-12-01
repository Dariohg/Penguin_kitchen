package Monitores;

import Modelos.Orden;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class MonitorComidas {
    private final Queue<Orden> bufferComidas;

    public MonitorComidas() {
        this.bufferComidas = new LinkedBlockingQueue<>();
    }

    public synchronized void agregarComida(Orden orden) {
        bufferComidas.add(orden);
        notifyAll(); // Notificar que hay una nueva comida lista
    }

    public synchronized Orden obtenerSiguienteComida() {
        while (bufferComidas.isEmpty()) {
            try {
                wait(); // Esperar hasta que haya comidas disponibles
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return bufferComidas.poll(); // Retorna la comida lista
    }
}