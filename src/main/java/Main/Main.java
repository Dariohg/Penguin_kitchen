package Main;

import Controladores.ControladorCliente;
import Hilos.HiloCliente;
import Monitores.MonitorMesas;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import static com.almasb.fxgl.dsl.FXGL.*;

import com.almasb.fxgl.dsl.FXGL;
import views.*;
import Modelos.GestorMesas;
import com.almasb.fxgl.entity.Entity;
import java.util.List;

public class Main extends GameApplication {
    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(600);
        gameSettings.setHeight(800); // Altura de la ventana
        gameSettings.setTitle("Penguin_Kitchen"); // Título de la ventana
        gameSettings.setVersion("1.0"); // Versión
    }

    @Override
    protected void initGame() {
        // Cargar fondo
        getGameScene().setBackgroundRepeat("fondo.png");

        // Crear entidades de Mesero, Cocinero, Recepcionista
        Mesero m = new Mesero();
        Cocinero c = new Cocinero();
        Recepcionista p = new Recepcionista();

        // Añadir a las entidades al mundo
        FXGL.getGameWorld().addEntity(m.getMesero());
        FXGL.getGameWorld().addEntity(c.getCocinero());
        FXGL.getGameWorld().addEntity(p.getRecepcionista());

        // Crear las mesas
        List<Mesa> mesas = GestorMesas.crearMesas();
        MonitorMesas monitorMesas = new MonitorMesas(mesas);

        // Crear clientes y asignar hilos para cada cliente
        for (int i = 0; i < 6; i++) {


            // Crear cliente visual
            Cliente cliente = new Cliente();


            // Crear controladorCliente para el cliente
            ControladorCliente controladorCliente = new ControladorCliente(cliente);


            // Crear y ejecutar hilo de cliente
            HiloCliente hiloCliente = new HiloCliente(controladorCliente, cliente, monitorMesas); // Asignar mesa
            new Thread(hiloCliente, "Cliente-" + i).start();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
