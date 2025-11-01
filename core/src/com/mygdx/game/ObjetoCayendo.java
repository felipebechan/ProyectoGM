package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public abstract class ObjetoCayendo implements Dibujable {
    protected Rectangle area;
    protected Texture imagen;
    
    protected float velocidadY; 
    protected float velocidadX = 0;
    protected float rotacion = 0f;

    public ObjetoCayendo(Texture imagen, float x, float y) {
        this.imagen = imagen;
        this.area = new Rectangle(x, y, 50, 50); 
        this.velocidadY = MathUtils.random(200, 400);
    }

    public void mover(float delta) {
        area.y -= velocidadY * delta;
        area.x += velocidadX * delta;
    }
    
    public void reorientar(GameScreen.EstadoJuego nuevoEstado) {
        if (nuevoEstado == GameScreen.EstadoJuego.GRAVEDAD_IZQUIERDA) {
            this.velocidadY = 0;
            this.velocidadX = -MathUtils.random(200, 400);
            // si la gravedad va a la izq, el sprite apunta a la derecha
            this.rotacion = -90;
        } else if (nuevoEstado == GameScreen.EstadoJuego.GRAVEDAD_DERECHA) {
            this.velocidadY = 0;
            this.velocidadX = MathUtils.random(200, 400);
            //si la gravedad va a la der, el sprite apunta a la izquierda
            this.rotacion = 90;
        }
    }
    
    @Override
    public void render(SpriteBatch batch) {
        //  calculamos la posicion para que la imagen (64x64)
        // se dibuje centrada sobre la hitbox (50x50)
        float drawX = area.x - (64 - area.width) / 2;
        float drawY = area.y - (64 - area.height) / 2;
        
        // el origen de la rotacion es siempre el centro de la IMAGEN 
        float originX = 32f;
        float originY = 32f;

        batch.draw(imagen, drawX, drawY, originX, originY, 64, 64, 1, 1, rotacion, 0, 0, imagen.getWidth(), imagen.getHeight(), false, false);
    }

    public Rectangle getArea() {
        return area;
    }

    public abstract void aplicarEfecto(Tarro tarro);
}