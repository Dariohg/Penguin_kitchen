package Main;

import Controladores.ControladorCocinero;
import Controladores.ControladorMesero;
import Hilos.HiloMesero;
import Hilos.HiloCocinero;
import Monitores.MonitorMesas;
import Monitores.MonitorOrdenes;
import Monitores.MonitorComidas;

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
    private MonitorOrdenes monitorOrdenes;
    private MonitorComidas monitorComidas; // Nuevo monitor para comidas
    private int totalClientes;

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

        // Instanciar monitores
        monitorMesas = new MonitorMesas(mesas);
        monitorOrdenes = new MonitorOrdenes(); // El buffer es manejado internamente
        monitorComidas = new MonitorComidas(); // Nuevo monitor de comidas

        // Crear un ClienteManager con tasa de llegada 1 cliente por segundo
        ClienteManager clienteManager = new ClienteManager(1, monitorMesas);

        totalClientes = 0;
        // Generar clientes en intervalos usando Poisson
        FXGL.run(() -> {
            clienteManager.generarClientes(totalClientes);
            totalClientes++;
        }, Duration.millis(1000 + Math.random() * 2000)); // Generar un nuevo cliente cada 1 - 3 segundos

        // Crear y empezar el hilo del mesero
        Mesero m = new Mesero();
        FXGL.getGameWorld().addEntity(m.getMesero());
        ControladorMesero controladorMesero = new ControladorMesero(m);
        HiloMesero hiloMesero = new HiloMesero(controladorMesero, monitorMesas, monitorOrdenes, monitorComidas); // Pasa el monitorOrdenes
        new Thread(hiloMesero, "Mesero").start(); // Inicia el hilo del mesero

        // Crear y empezar el hilo del cocinero
        ControladorCocinero controladorCocinero = new ControladorCocinero(c);
        HiloCocinero hiloCocinero = new HiloCocinero(controladorCocinero, monitorOrdenes, monitorComidas); // Pasa los monitores
        new Thread(hiloCocinero, "Cocinero").start(); // Inicia el hilo del cocinero
    }

    public static void main(String[] args) {
        launch(args); // Lanza la aplicación FXGL
    }
}