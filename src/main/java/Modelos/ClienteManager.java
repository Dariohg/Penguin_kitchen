package Modelos;

import views.Cliente;
import Controladores.ControladorCliente;
import Hilos.HiloCliente;
import Monitores.MonitorMesas;

public class ClienteManager {

    private final PoissonDistribution poisson;
    private final MonitorMesas monitorMesas;

    public ClienteManager(double lambda, MonitorMesas monitorMesas) {
        this.poisson = new PoissonDistribution(lambda);
        this.monitorMesas = monitorMesas;
    }

    public void generarClientes() {
        // Usamos Poisson para determinar el número de clientes que llegan
        int llegada = poisson.sample();
        for (int i = 0; i < llegada; i++) {
            // Crear cliente
            Cliente cliente = new Cliente(i);
            ControladorCliente controladorCliente = new ControladorCliente(cliente);
            HiloCliente hiloCliente = new HiloCliente(controladorCliente, cliente, monitorMesas);
            new Thread(hiloCliente, "Cliente-" + i).start(); // Crear y lanzar hilo de cliente
        }
    }
}
