package Hilos;

import Monitores.MonitorMesas;
import views.Mesa;
import views.Cliente;
import Controladores.*;

public class HiloCliente implements Runnable {

    private final ControladorCliente controladorCliente;
    private final Cliente cliente;
    private final MonitorMesas monitorMesas;

    public HiloCliente(ControladorCliente controladorCliente, Cliente cliente, MonitorMesas monitorMesas) {
        this.controladorCliente = controladorCliente;
        this.cliente = cliente;
        this.monitorMesas = monitorMesas;
    }

    @Override
    public void run() {
        // Se asigna una mesa al cliente
        Mesa mesaAsignada = monitorMesas.asignarMesa(cliente);
        controladorCliente.moverAMesa(mesaAsignada);  // El cliente se mueve hacia la mesa

        // Esperar que el mesero lo atienda
        try {
            System.out.println(Thread.currentThread().getName() + " está esperando ser atendido.");
            synchronized (cliente) {
                cliente.wait(); // El cliente espera a ser atendido
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // El mesero lo atiende y luego el cliente se va
        try {
            Thread.sleep(3000);  // El mesero atiende durante 3 segundos
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        monitorMesas.liberarMesa(mesaAsignada);  // El mesero libera la mesa cuando el cliente se va
        controladorCliente.EliminarEntidadCliente();  // El cliente se va después de ser atendido
    }
}
