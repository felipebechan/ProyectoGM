package com.mygdx.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

// apunte a la clase correcta.
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("Recolecta Gotas - Base");
		config.setWindowedMode(500, 500);
		config.useVsync(true);
		
		new Lwjgl3Application(new ProyectoJuegoLluvia(), config);
	}
}