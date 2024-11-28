package views;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;

public class Cocinero {

    private final Entity cocinero;

    public Cocinero () {
        this.cocinero = FXGL.entityBuilder().at(60,-290).scale(0.2,0.2).viewWithBBox("CHEF.PNG").build();
    }

    public Entity getCocinero() {
        return cocinero;
    }
}
