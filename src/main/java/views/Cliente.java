package views;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import javafx.geometry.Point2D;

import java.util.Random;

public class Cliente {
    private final Entity cliente;
    private Mesa mesaAsignada;  // Referencia a la mesa asignada

    public Cliente() {
        String image = new Random().nextBoolean() ? "cliente.png" : "clienta.png";
        this.cliente = FXGL.entityBuilder()
                .at(20, 10)
                .viewWithBBox(image)
                .scale(0.2, 0.2)
                .build();
    }

    // Método para mover al cliente hacia la mesa
    public void moveToTable(Mesa mesa) {
        this.mesaAsignada = mesa; // Asignamos la mesa al cliente
        Point2D targetPosition = mesa.getPosition();
        FXGL.animationBuilder()
                .duration(javafx.util.Duration.seconds(1)) // Duración del movimiento
                .translate(cliente) // Entidad a mover
                .to(targetPosition) // Posición objetivo
                .build();
    }

    // Método para obtener la entidad de cliente
    public Entity getCliente() {
        return cliente;
    }

    // Método para obtener la mesa asignada al cliente
    public Mesa getMesa() {
        return mesaAsignada;
    }
}
