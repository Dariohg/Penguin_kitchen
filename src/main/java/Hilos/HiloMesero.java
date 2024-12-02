package Hilos;

import views.Mesa;
import views.Cliente;
import Controladores.ControladorMesero;
import Modelos.Orden;
import Monitores.MonitorMesas;
import Monitores.MonitorOrdenes;
import Monitores.MonitorComidas;
import com.almasb.fxgl.entity.Entity;

public class HiloMesero implements Runnable {

    private final ControladorMesero controladorMesero;
    private final MonitorMesas monitorMesas;
    private final MonitorOrdenes monitorOrdenes;
    private final MonitorComidas monitorComidas;

    public HiloMesero(ControladorMesero controladorMesero, MonitorMesas monitorMesas, MonitorOrdenes monitorOrdenes, MonitorComidas monitorComidas) {
        this.controladorMesero = controladorMesero;
        this.monitorMesas = monitorMesas;
        this.monitorOrdenes = monitorOrdenes;
        this.monitorComidas = monitorComidas;
    }

    @Override
    public void run() {
        while (true) {
            // Esperar al siguiente cliente en la cola
            Cliente cliente = monitorMesas.obtenerSiguienteCliente();

            if (cliente != null) {
                Mesa mesa = cliente.getMesa();

                if (mesa != null && mesa.estaOcupada()) {
                    // Mover al mesero a la mesa del cliente
                    controladorMesero.moverAMesa(mesa);
                    System.out.println("Mesero atendiendo al cliente en la mesa: " + mesa);

                    // Crear una nueva orden y agregarla al buffer de órdenes
                    Orden nuevaOrden = new Orden(cliente.getId());
                    monitorOrdenes.agregarOrden(nuevaOrden);
                    System.out.println("Mesero añadió la orden " + nuevaOrden.getId());

                    // Esperar a que la comida esté lista en el monitor de comidas
                    Orden comidaLista = monitorComidas.obtenerSiguienteComida();

                    if (comidaLista != null && comidaLista.getId() == cliente.getId()) {
                        // Llevar la comida al cliente y spawnear el plato
                        System.out.println("Mesero lleva la comida de la orden " + comidaLista.getId() + " al cliente.");
                        Entity plato = controladorMesero.spawnPlatoEnMesa(mesa);

                        // Notificar al cliente que puede empezar a comer
                        synchronized (cliente) {
                            cliente.notify(); // Desbloquear al cliente
                        }

                        // Esperar un poco antes de eliminar el plato
                        // try {
                        //     Thread.sleep(2000); // Simular tiempo para entregar el plato
                        // } catch (InterruptedException e) {
                        //     Thread.currentThread().interrupt();
                        // }

                        // El cliente se encargará de eliminar el plato al irse
                        cliente.setPlato(plato);
                    }
                }
            } else {
                // Si no hay clientes, esperar antes de intentar de nuevo
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}