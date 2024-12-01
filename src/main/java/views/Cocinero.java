package views;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;

public class Cocinero {
    private final String nombre = "";
    private String estado;

    private final Entity cocinero;

    public Cocinero () {
        this.cocinero = FXGL.entityBuilder().at(-260,-500).scale(0.2,0.2).viewWithBBox("chef.png").build();
    }

    public String getNombre() {
        return nombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Entity getCocinero() {
        return cocinero;
    }
}