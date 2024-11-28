package com.tuempresa.restaurante.config;

import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.FXGL;

public class AppConfig {

    public static void configurarJuego(GameSettings settings) {
        settings.setWidth(800);
        settings.setHeight(600);
        settings.setTitle("Cataurant");
        settings.setVersion("1.0");
        settings.setFullScreenAllowed(true);
        settings.setMenuEnabled(true);

        // Configura la escena inicial
        FXGL.getGameController().setScene("gameMenu"); // Establece la escena inicial
    }
}
