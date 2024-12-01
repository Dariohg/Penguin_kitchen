package Controladores;

import views.Cliente;
import views.Mesa;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import javafx.geometry.Point2D;

public class ControladorCliente {
    private final Cliente cliente;

    public ControladorCliente(Cliente cliente) {
        this.cliente = cliente;
        // Agrega la entidad del cliente al mundo de FXGL
        FXGL.getGameWorld().addEntity(cliente.getCliente());
    }

    // Método para mover al cliente hacia la mesa
    public void moverAMesa(Mesa mesa) {
        Point2D targetPosition = mesa.getPosition();  // Obtener la posición de la mesa
        FXGL.animationBuilder()
                .duration(javafx.util.Duration.seconds(3))  // Duración del movimiento
                .translate(cliente.getCliente())  // Entidad a mover
                .to(targetPosition)  // Posición de la mesa
                .buildAndPlay();  // Ejecutar la animación
    }

    // Método para notificar al cliente que está siendo atendido
    public void atenderCliente() {
        synchronized (cliente) {
            cliente.notify();  // Despertar al cliente para que continúe
        }
    }

    // Método para eliminar el plato del cliente
    public void eliminarPlato(Entity plato) {
        FXGL.runOnce(() -> {
            FXGL.getGameWorld().removeEntity(plato); // Eliminar la entidad del plato
            System.out.println("Plato eliminado.");
        }, javafx.util.Duration.ZERO);
    }

    // Eliminar al cliente de la entidad después de ser atendido
    public void EliminarEntidadCliente() {
        FXGL.runOnce(() -> {
            FXGL.getGameWorld().removeEntity(cliente.getCliente()); // Eliminar la entidad gráfica del cliente
            System.out.println("Cliente eliminado del sistema.");
        }, javafx.util.Duration.ZERO);
    }
}
