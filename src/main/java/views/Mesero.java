package views;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;

public class Mesero {
private final Entity mesero;

public Mesero () {
    this.mesero = FXGL.entityBuilder().at(20,10).scale(0.2,0.2).viewWithBBox("MESERO.PNG").build();
}

public Entity getMesero() {
    return mesero;
}

}
