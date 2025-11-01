package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

// la bomba te quita vidas
public class Bomba extends ObjetoCayendo {
    public Bomba(Texture imagen, float x, float y) {
        super(imagen, x, y);
    }

    @Override
    public void aplicarEfecto(Tarro tarro) {
        tarro.dañar();
    }
}