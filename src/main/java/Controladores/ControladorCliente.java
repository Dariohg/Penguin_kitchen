package Controladores;

import views.Cliente;
import views.Mesa;
import com.almasb.fxgl.dsl.FXGL;
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

    // Eliminar al cliente de la entidad después de ser atendido
    public void EliminarEntidadCliente() {
        // Usamos FXGL para eliminar la entidad del cliente del mundo
        FXGL.runOnce(() -> FXGL.getGameWorld().removeEntity(cliente.getCliente()), javafx.util.Duration.ZERO);
    }
}
