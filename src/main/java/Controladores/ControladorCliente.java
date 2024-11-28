package Controladores;

import com.almasb.fxgl.dsl.FXGL;
import javafx.geometry.Point2D;
import views.Cliente;
import views.Mesa;

public class ControladorCliente {
    private final Cliente cliente;
    public ControladorCliente(Cliente cliente) {
        this.cliente = cliente;
        FXGL.getGameWorld().addEntity(cliente.getCliente());
    }

    // Método para mover al cliente hacia la mesa
    public void moverAMesa( Mesa mesa) {
        Point2D targetPosition = mesa.getPosition();
        FXGL.animationBuilder()
                .duration(javafx.util.Duration.seconds(3)) // Duración del movimiento
                .translate(cliente.getCliente()) // Entidad a mover
                .to(targetPosition) // Posición objetivo
                .buildAndPlay();
    }

    public void EliminarEntidadCliente() {
        FXGL.runOnce(() -> FXGL.getGameWorld().removeEntity(cliente.getCliente()),javafx.util.Duration.ZERO);
    }
}