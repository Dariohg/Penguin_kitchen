package Modelos;

import views.Mesa;
import com.almasb.fxgl.dsl.FXGL;

import java.util.ArrayList;
import java.util.List;

public class GestorMesas {

    // Constructor vacío
    public GestorMesas() {
    }

    // Método para crear las mesas en las posiciones específicas
    public static List<Mesa> crearMesas() {
        List<Mesa> mesas = new ArrayList<>();

        // Fila 1
        mesas.add(new Mesa(-320, -300)); // Mesa 1
        mesas.add(new Mesa(-120, -300)); // Mesa 2
        mesas.add(new Mesa(80, -300));  // Mesa 3

        // Fila 2
        mesas.add(new Mesa(-320, -150)); // Mesa 4
        mesas.add(new Mesa(-100, -150)); // Mesa 5

        // Fila 3
        mesas.add(new Mesa(-320, 35));   // Mesa 6

        return mesas; // Devuelve la lista de mesas
    }

    // Método para añadir las mesas al mundo de FXGL
    public static void addMesas() {
        // Obtener la lista de mesas creadas
        List<Mesa> mesas = crearMesas();

        // Añadir cada mesa al mundo de FXGL
        for (Mesa mesa : mesas) {
            // Agregar la entidad de la mesa al mundo
            FXGL.getGameWorld().addEntity(mesa.getMesa());
        }
    }
}
