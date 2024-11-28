package Main;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import static com.almasb.fxgl.dsl.FXGL.*;

import com.almasb.fxgl.dsl.FXGL;
import views.Cliente;
import views.Mesero;
import views.Cocinero;
import views.Recepcionista;

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
        getGameScene().setBackgroundRepeat("fondo.png");
        Mesero m = new Mesero();
        Cocinero c = new Cocinero();
        Recepcionista p = new Recepcionista();
        Cliente e = new Cliente(-400,-400);
        Cliente f = new Cliente(-350,-300);


        FXGL.getGameWorld().addEntity(m.getMesero());
        FXGL.getGameWorld().addEntity(c.getCocinero());
        FXGL.getGameWorld().addEntity(p.getRecepcionista());
        FXGL.getGameWorld().addEntity(e.getCliente());
        FXGL.getGameWorld().addEntity(f.getCliente());
    }
    public static void main(String[] args) {
        launch(args);
    }
}
