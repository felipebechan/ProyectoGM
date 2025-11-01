package com.mygdx.game;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class ControladorDeObjetos {
    private Array<ObjetoCayendo> objetos;
    private long lastDropTime;
    private Music rainMusic; 
    private Texture manzanaImg, sandiaImg, bombaImg, estrellaImg, giroImg, tamanioImg, velocidadImg;
    private Sound dropSound;


    public ControladorDeObjetos(Music music, Texture manzana, Texture sandia, Texture bomba, Texture estrella, Texture giro, Texture tamanio, Texture velocidad, Sound sound) {
        this.rainMusic = music;
        this.manzanaImg = manzana;
        this.sandiaImg = sandia;
        this.bombaImg = bomba;
        this.estrellaImg = estrella;
        this.giroImg = giro;
        this.tamanioImg = tamanio;
        this.velocidadImg = velocidad;
        this.dropSound = sound;
        this.objetos = new Array<>();
    }


    public void iniciar() {
        crearObjetoCayendo(GameScreen.EstadoJuego.NORMAL);
        rainMusic.setLooping(true);
        rainMusic.play();
    }

    public void reorientarTodosLosObjetos(GameScreen.EstadoJuego nuevoEstado) {
        for (ObjetoCayendo objeto : objetos) {
            objeto.reorientar(nuevoEstado);
        }
    }

    private void crearObjetoCayendo(GameScreen.EstadoJuego estado) {
		ObjetoCayendo objeto;
		int tipo = MathUtils.random(1, 100);

        switch (estado) {
			case NORMAL:
				float x = MathUtils.random(0, 800 - 64);
				if (tipo <= 35) objeto = new Fruta(manzanaImg, x, 480, dropSound);
				else if (tipo <= 70) objeto = new Fruta(sandiaImg, x, 480, dropSound);
				else if (tipo <= 85) objeto = new Bomba(bombaImg, x, 480);
				else if (tipo <= 90) objeto = new PowerUpEstrella(estrellaImg, x, 480);
				else if (tipo <= 93) objeto = new PowerUpGiro(giroImg, x, 480);
                else if (tipo <= 96) objeto = new PowerUpTamanio(tamanioImg, x, 480);
				else objeto = new PowerUpVelocidad(velocidadImg, x, 480);
				break;
			case GRAVEDAD_IZQUIERDA:
			case GRAVEDAD_DERECHA:
				float y_lado = MathUtils.random(0, 480 - 64);
				float x_lado = (estado == GameScreen.EstadoJuego.GRAVEDAD_IZQUIERDA) ? 800 : -64;
				if (tipo <= 45) objeto = new Fruta(manzanaImg, x_lado, y_lado, dropSound);
				else if (tipo <= 90) objeto = new Fruta(sandiaImg, x_lado, y_lado, dropSound);
				else objeto = new Bomba(bombaImg, x_lado, y_lado);
				objeto.reorientar(estado);
				break;
			default:
				return;
		}
        
        objetos.add(objeto);
        lastDropTime = TimeUtils.nanoTime();
    }

    public ObjetoCayendo actualizar(float delta, Tarro tarro, GameScreen.EstadoJuego estado) {
        if (TimeUtils.nanoTime() - lastDropTime > 500000000 && estado != GameScreen.EstadoJuego.AVISO_GIRO) {
            crearObjetoCayendo(estado);
        }

        for (int i = objetos.size - 1; i >= 0; i--) {
            ObjetoCayendo objeto = objetos.get(i);
            objeto.mover(delta);

            if (objeto.getArea().y + 64 < 0 || objeto.getArea().x + 64 < 0 || objeto.getArea().x > 800) {
                objetos.removeIndex(i);
            } else if (objeto.getArea().overlaps(tarro.getArea())) {
                objeto.aplicarEfecto(tarro);
                objetos.removeIndex(i);
                return objeto; 
            }
        }
        return null;
    }

    public void render(SpriteBatch batch) {
        for (ObjetoCayendo objeto : objetos) {
            objeto.render(batch);
        }
    }
    
    public void pausar() { rainMusic.pause(); }
    public void continuar() { rainMusic.play(); }
    public void destruir() { rainMusic.dispose(); dropSound.dispose(); }
}