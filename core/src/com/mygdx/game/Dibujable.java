package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

// pa q todas las cosas q se dibujan tengan el metodo render
public interface Dibujable {
    void render(SpriteBatch batch);
}