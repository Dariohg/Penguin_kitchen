package views;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import java.util.Random;

public class Cliente {
 private final Entity cliente;

    public Cliente(double x, double y) {
        String image = new Random().nextBoolean() ? "cliente.png" : "clienta.png";
        this.cliente = FXGL.entityBuilder()
                .at(x, y)
                .viewWithBBox(image)
                .scale(0.2, 0.2)
                .build();
    }

    public Entity getCliente() {
        return cliente;
    }
}
