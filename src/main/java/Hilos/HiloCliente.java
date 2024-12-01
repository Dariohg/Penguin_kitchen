package Hilos;

import Monitores.MonitorMesas;
import views.Mesa;
import views.Cliente;
import Controladores.ControladorCliente;
import com.almasb.fxgl.entity.Entity;

import java.util.Random;

public class HiloCliente implements Runnable {

    private final ControladorCliente controladorCliente;
    private final Cliente cliente;
    private final MonitorMesas monitorMesas;
    private Entity plato; // Referencia al plato asociado al cliente

    public HiloCliente(ControladorCliente controladorCliente, Cliente cliente, MonitorMesas monitorMesas) {
        this.controladorCliente = controladorCliente;
        this.cliente = cliente;
        this.monitorMesas = monitorMesas;
    }

    // Método para asignar el plato al cliente
    public void setPlato(Entity plato) {
        this.plato = plato;
    }

    @Override
    public void run() {
        // Asignar una mesa al cliente
        Mesa mesaAsignada = monitorMesas.asignarMesa(cliente);
        controladorCliente.moverAMesa(mesaAsignada);

        // Esperar a que el mesero lo atienda y le entregue la comida
        try {
            System.out.println(Thread.currentThread().getName() + " está esperando la comida.");
            synchronized (cliente) {
                cliente.wait(); // Bloquear al cliente hasta que sea notificado por el mesero
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Consumir la comida (tiempo aleatorio entre 2 y 4 segundos)
        Random random = new Random();
        int tiempoComer = 2000 + random.nextInt(2000); // Generar tiempo entre 2000ms y 4000ms
        System.out.println(Thread.currentThread().getName() + " está comiendo durante " + tiempoComer + "ms.");
        try {
            Thread.sleep(tiempoComer); // Simular el tiempo de consumo
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Liberar la mesa y eliminar el plato
        monitorMesas.liberarMesa(mesaAsignada);
        if (plato != null) {
            controladorCliente.eliminarPlato(plato); // Eliminar el plato del cliente
        }

        // Eliminar al cliente del sistema
        controladorCliente.EliminarEntidadCliente();
        System.out.println(Thread.currentThread().getName() + " terminó de comer y se fue.");
    }
}
