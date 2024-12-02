package Controladores;

import views.Cliente;
import views.Mesero;
import views.Mesa;

import java.util.concurrent.CompletableFuture;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import javafx.application.Platform;

public class ControladorMesero {

    private final Mesero mesero;

    public ControladorMesero(Mesero mesero) {
        this.mesero = mesero;
    }

    // Método para mover al mesero hacia una mesa
    public void moverAMesa(Mesa mesa) {
        if (mesa != null && mesa.estaOcupada()) {
            mesero.moverAMesa(mesa);
        }
    }

    // Método para simular la acción de atender al cliente
    public void atenderCliente(Cliente cliente) {
        System.out.println("Atendiendo al cliente...");
    }

    public Entity spawnPlatoEnMesa(Mesa mesa) {
        // Usamos un CompletableFuture para manejar el retorno asíncrono
        CompletableFuture<Entity> futurePlato = new CompletableFuture<>();

        Platform.runLater(() -> {
            Entity plato = FXGL.entityBuilder()
                .at(mesa.getPosition().add(10, 10)) // Ajustar posición
                .viewWithBBox("comida.png")          // Asset del plato
                .scale(0.2, 0.2)                  // Escala del plato
                .buildAndAttach();

            System.out.println("Plato spawneado en la mesa: " + mesa.getPosition());
            futurePlato.complete(plato); // Completar el Future con el plato creado
        });

        try {
            // Esperamos el resultado del Future
            return futurePlato.get(); // Esto es bloqueante hasta que el plato esté creado
        } catch (Exception e) {
            throw new RuntimeException("Error al spawnear el plato", e);
        }
    }

    // Método para eliminar el plato
    public void eliminarPlato(Entity plato) {
        if (plato != null) {
            FXGL.getGameWorld().removeEntity(plato);
            System.out.println("Plato eliminado.");
        }
    }
}