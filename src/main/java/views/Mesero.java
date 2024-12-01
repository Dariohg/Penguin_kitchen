package views;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import javafx.geometry.Point2D;

public class Mesero {
    private final Entity mesero;

    public Mesero() {
        this.mesero = FXGL.entityBuilder()
                .at(40, -160)   // Posición inicial del mesero
                .scale(0.2, 0.2) // Escala
                .viewWithBBox("mesero.png")  // Imagen del mesero
                .build();
        // No movemos el mesero al principio
    }

    public Entity getMesero() {
        return mesero;
    }

    // Método para mover al mesero hacia la mesa
    public void moverAMesa(Mesa mesa) {
        FXGL.animationBuilder()
                .duration(javafx.util.Duration.seconds(3)) // Duración del movimiento
                .translate(mesero) // Mover el mesero
                .to(mesa.getPosition()) // Moverlo a la mesa
                .buildAndPlay();
    }
}
