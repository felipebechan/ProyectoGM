package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

// este powerup agranda la cesta
public class PowerUpTamanio extends ObjetoCayendo {
    public PowerUpTamanio(Texture imagen, float x, float y) {
        super(imagen, x, y);
    }

    @Override
    public void aplicarEfecto(Tarro tarro) {
        // le decimos al tarro que active su powerup de tamaño
        tarro.activarPowerUpTamanio();
    }
}