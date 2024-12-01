package Hilos;

import Controladores.ControladorCocinero;
import Monitores.MonitorOrdenes;
import Monitores.MonitorComidas;
import Modelos.Orden;

public class HiloCocinero implements Runnable {

    private final ControladorCocinero controladorCocinero;
    private final MonitorOrdenes monitorOrdenes;
    private final MonitorComidas monitorComidas;

    public HiloCocinero(ControladorCocinero controladorCocinero, MonitorOrdenes monitorOrdenes, MonitorComidas monitorComidas) {
        this.controladorCocinero = controladorCocinero;
        this.monitorOrdenes = monitorOrdenes;
        this.monitorComidas = monitorComidas;
    }

    @Override
    public void run() {
        while (true) {
            // Obtener la siguiente orden del buffer
            Orden orden = monitorOrdenes.obtenerSiguienteOrden(); // Espera bloqueante hasta que haya órdenes

            if (orden != null) {
                // Cocinar la orden
                System.out.println("Cocinero preparando orden: " + orden.getId());
                controladorCocinero.cocinarOrden(orden);

                // Simular tiempo de cocción
                try {
                    Thread.sleep(5000); // Simulación de cocinar durante 5 segundos
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return; // Salir si el hilo es interrumpido
                }

                // Cambiar estado de la orden a "LISTO"
                orden.setEstado("LISTO");

                // Añadir la orden al buffer de comidas
                monitorComidas.agregarComida(orden);
                controladorCocinero.notificarOrdenLista(orden);
                System.out.println("Orden lista: " + orden.getId());
            } else {
                // Si no hay órdenes, reposar un poco antes de intentarlo nuevamente
                try {
                    Thread.sleep(2000); // Esperar antes de revisar nuevamente
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return; // Salir si el hilo es interrumpido
                }
            }
        }
    }
}
