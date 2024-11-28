package Main;

import Controladores.ControladorMesero;
import Hilos.HiloMesero;
import Monitores.MonitorMesas;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import javafx.util.Duration;
import views.*;
import Modelos.GestorMesas;
import Modelos.ClienteManager;
import com.almasb.fxgl.dsl.FXGL;

import java.util.List;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameScene;

public class Main extends GameApplication {

    private MonitorMesas monitorMesas;

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

        // Crear entidades de Cocinero, Recepcionista
        Cocinero c = new Cocinero();
        Recepcionista p = new Recepcionista();

        // Añadir a las entidades al mundo
        FXGL.getGameWorld().addEntity(c.getCocinero());
        FXGL.getGameWorld().addEntity(p.getRecepcionista());

        // Crear las mesas
        List<Mesa> mesas = GestorMesas.crearMesas();
        GestorMesas.addMesas();
        monitorMesas = new MonitorMesas(mesas);  // Crear el MonitorMesas aquí

        // Crear un ClienteManager con tasa de llegada 1 cliente por segundo
        ClienteManager clienteManager = new ClienteManager(1, monitorMesas);

        // Generar clientes en intervalos usando Poisson
        FXGL.run(() -> {
            clienteManager.generarClientes();
        }, Duration.millis(3000));  // Generar un nuevo cliente cada 3 segundos

        // Crear y empezar el hilo del mesero
        Mesero m = new Mesero();
        FXGL.getGameWorld().addEntity(m.getMesero());
        ControladorMesero controladorMesero = new ControladorMesero(m);
        HiloMesero hiloMesero = new HiloMesero(controladorMesero, monitorMesas);  // Pasa monitorMesas
        new Thread(hiloMesero, "Mesero").start();  // Inicia el hilo del mesero
    }

    public static void main(String[] args) {
        launch(args);  // Lanza la aplicación FXGL
    }
}
