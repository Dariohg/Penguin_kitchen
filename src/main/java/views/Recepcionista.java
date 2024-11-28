package views;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;

public class Recepcionista {
    private final Entity recepcionista;

    public Recepcionista () {
        this.recepcionista = FXGL.entityBuilder().at(-160,40).scale(0.2,0.2).viewWithBBox("RECEPCIONISTA.PNG").build();
    }

    public Entity getRecepcionista() {
        return recepcionista;
    }
}
