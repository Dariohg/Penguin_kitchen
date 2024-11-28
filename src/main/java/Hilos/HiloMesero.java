package Hilos;

import views.Mesa;
import views.Cliente;
import Controladores.ControladorMesero;
import Monitores.MonitorMesas;

public class HiloMesero implements Runnable {

    private final ControladorMesero controladorMesero;
    private final MonitorMesas monitorMesas;

    public HiloMesero(ControladorMesero controladorMesero, MonitorMesas monitorMesas) {
        this.controladorMesero = controladorMesero;
        this.monitorMesas = monitorMesas;
    }

    @Override
    public void run() {
        while (true) {
            // Espera a que haya un cliente esperando
            Cliente cliente = monitorMesas.obtenerSiguienteCliente();  // Obtiene el siguiente cliente en la cola

            if (cliente != null) {
                Mesa mesa = cliente.getMesa();  // Obtén la mesa asignada

                if (mesa != null && mesa.estaOcupada()) {
                    // El mesero se mueve hacia la mesa donde el cliente está esperando
                    controladorMesero.moverAMesa(mesa);

                    // Aquí el mesero tiene que "atender" al cliente
                    // Espera que el cliente sea atendido
                    System.out.println("Atendiendo a: " + cliente);
                    controladorMesero.atenderCliente(cliente);

                    // Espera por un tiempo simulando que está atendiendo
                    try {
                        Thread.sleep(3000);  // El mesero atiende durante 3 segundos
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }

                    // Liberamos la mesa después de atender
                    monitorMesas.liberarMesa(mesa);  // El mesero libera la mesa
                }
            } else {
                // Si no hay clientes, el mesero espera un poco antes de intentarlo nuevamente
                try {
                    Thread.sleep(2000);  // Esperar antes de intentar nuevamente
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
