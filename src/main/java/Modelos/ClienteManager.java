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

    public void generarClientes(int totalClientes) {
        // Si ya se han generado 100 clientes, no hacer nada
        if (totalClientes >= 100) {
            return;
        }

        // Obtener número de clientes a generar según Poisson
        int llegada = poisson.sample();

        // Ajustar si se excedería el límite de 100 clientes
        if (totalClientes + llegada > 100) {
            llegada = 100 - totalClientes;
        }

        // Generar clientes
        for (int i = 0; i < llegada; i++) {
            // Usar totalClientes como ID único
            Cliente cliente = new Cliente(totalClientes);
            ControladorCliente controladorCliente = new ControladorCliente(cliente);
            HiloCliente hiloCliente = new HiloCliente(controladorCliente, cliente, monitorMesas);
            
            // Comenzar el hilo del cliente
            Thread clienteThread = new Thread(hiloCliente, "Cliente-" + totalClientes);
            clienteThread.start();
        }

        System.out.println("Clientes generados en esta iteración: " + llegada + 
                           " | Total clientes: " + totalClientes);
    }
}
