package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

// este powerup te hace mas rapido
public class PowerUpVelocidad extends ObjetoCayendo {
    public PowerUpVelocidad(Texture imagen, float x, float y) {
        super(imagen, x, y);
    }

    @Override
    public void aplicarEfecto(Tarro tarro) {
        // le decimos al tarro que active su powerup de velocidad
        tarro.activarPowerUpVelocidad();
    }
}