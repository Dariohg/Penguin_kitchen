package views;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import javafx.geometry.Point2D;

import java.util.Random;

public class Cliente {
    private final Entity cliente; // Entidad gráfica del cliente
    private Mesa mesaAsignada;    // Referencia a la mesa asignada
    private Entity plato;         // Referencia al plato servido al cliente
    private final int id;         // Identificador único del cliente

    public Cliente(int id) {
        String image = new Random().nextBoolean() ? "cliente.png" : "clienta.png";
        this.cliente = FXGL.entityBuilder()
                .at(20, 10)             // Posición inicial del cliente
                .viewWithBBox(image)    // Imagen del cliente
                .scale(0.2, 0.2)        // Escala de la imagen
                .build();

        this.id = id;
    }

    // Método para mover al cliente hacia la mesa asignada
    public void moveToTable(Mesa mesa) {
        this.mesaAsignada = mesa; // Asignar la mesa al cliente
        Point2D targetPosition = mesa.getPosition(); // Obtener la posición de la mesa
        FXGL.animationBuilder()
                .duration(javafx.util.Duration.seconds(1)) // Duración del movimiento
                .translate(cliente)                       // Mover la entidad gráfica
                .to(targetPosition)                       // Hacia la posición de la mesa
                .buildAndPlay();                          // Ejecutar la animación
    }

    // Método para asignar el plato al cliente
    public void setPlato(Entity plato) {
        this.plato = plato; // Referenciar el plato asignado al cliente
    }

    // Método para eliminar el plato del cliente
    public void eliminarPlato() {
        if (plato != null) {
            FXGL.getGameWorld().removeEntity(plato); // Eliminar el plato del mundo FXGL
            System.out.println("Plato eliminado para el cliente con ID: " + id);
            plato = null; // Liberar la referencia al plato
        }
    }

    // Getters y setters
    public Entity getCliente() {
        return cliente; // Obtener la entidad gráfica del cliente
    }

    public void setMesa(Mesa mesa) {
        this.mesaAsignada = mesa; // Asignar la mesa al cliente
    }

    public int getId() {
        return id; // Retornar el identificador único del cliente
    }

    public Mesa getMesa() {
        return mesaAsignada; // Retornar la mesa asignada al cliente
    }
}