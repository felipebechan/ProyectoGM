package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

// el powerup de la estrella te da vida y puntos
public class PowerUpEstrella extends ObjetoCayendo {
    public PowerUpEstrella(Texture imagen, float x, float y) {
        super(imagen, x, y);
    }

    @Override
    public void aplicarEfecto(Tarro tarro) {
        tarro.sumarPuntos(50);
        tarro.ganarVida();
    }
}