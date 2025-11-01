package com.mygdx.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

// esta es una fruta wena xD
public class Fruta extends ObjetoCayendo {
    private Sound sonidoRecoleccion;

    public Fruta(Texture imagen, float x, float y, Sound sonido) {
        super(imagen, x, y);
        this.sonidoRecoleccion = sonido;
    }

    @Override
    public void aplicarEfecto(Tarro tarro) {
        tarro.sumarPuntos(10);
        sonidoRecoleccion.play();
    }
}