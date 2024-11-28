package Hilos;

import Monitores.MonitorMesas;
import views.Mesa;
import views.Cliente;
import Controladores.*;  // Corregido nombre de la clase ControladorCliente

public class HiloCliente implements Runnable {  // Implementando Runnable para el hilo

    private final ControladorCliente controladorCliente;  // Cambié el nombre de la variable a controladorCliente
    private final Cliente cliente;
    private final MonitorMesas monitorMesas;

    public HiloCliente(ControladorCliente controladorCliente, Cliente cliente, MonitorMesas monitorMesas) {
        this.controladorCliente = controladorCliente;
        this.cliente = cliente;

        this.monitorMesas = monitorMesas;
    }

    @Override
    public void run() {
       Mesa mesaAsignada = monitorMesas.asignarMesa();
        controladorCliente.moverAMesa(mesaAsignada);

        try {
            Thread.sleep(5000);
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
        monitorMesas.liberarMesa(mesaAsignada);
        controladorCliente.EliminarEntidadCliente();
    }
}
