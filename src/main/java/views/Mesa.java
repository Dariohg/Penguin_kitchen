package views;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import javafx.geometry.Point2D;

public class Mesa {

    private final Entity mesa;
    private boolean ocupada;

    // Constructor que crea una mesa en una posición específica
    public Mesa(double x, double y) {
        this.mesa = FXGL.entityBuilder()
                .at(x, y)
                .viewWithBBox("mesero.png") // Asegúrate de tener una imagen de mesa (aunque no sea visualmente importante, se necesita para FXGL)
                .build();
        this.ocupada = false; // Inicialmente la mesa está libre
    }

    // Método para obtener la entidad de la mesa
    public Entity getMesa() {
        return mesa;
    }

    // Método para obtener la posición de la mesa
    public Point2D getPosition() {
        return mesa.getPosition();
    }

    // Método para saber si la mesa está ocupada
    public boolean estaOcupada() {
        return ocupada;
    }

    // Método para cambiar el estado de ocupación de la mesa
    public void ocupar() {
        this.ocupada = true;
    }

    // Método para liberar la mesa
    public void liberar() {
        this.ocupada = false;
    }
}

