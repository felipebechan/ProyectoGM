package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

// este powerup no hace nada directo, el gamescreen lo cacha y cambia el juego
public class PowerUpGiro extends ObjetoCayendo {
    public PowerUpGiro(Texture imagen, float x, float y) {
        super(imagen, x, y);
    }

    @Override
    public void aplicarEfecto(Tarro tarro) {
        // no hace nada aqui, la magia la hace gamescreen
    }
}