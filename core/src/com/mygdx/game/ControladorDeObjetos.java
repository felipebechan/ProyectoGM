package com.mygdx.game;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

// controla todos los objetos que caen en el juego
// usa el patron strategy para cambiar la forma de generar objetos
public class ControladorDeObjetos {
    private Array<ObjetoCayendo> objetos;
    private long lastDropTime;
    private Music rainMusic;
    
    // patron strategy - diferentes formas de generar objetos
    private EstrategiaGeneracion estrategiaActual;
    private EstrategiaGeneracion estrategiaNormal;
    private EstrategiaGeneracion estrategiaIzquierda;
    private EstrategiaGeneracion estrategiaDerecha;
    
    public ControladorDeObjetos(Music music) {
        this.rainMusic = music;
        this.objetos = new Array<>();
        
        // inicializamos las 3 estrategias
        this.estrategiaNormal = new EstrategiaGeneracionNormal();
        this.estrategiaIzquierda = new EstrategiaGeneracionIzquierda();
        this.estrategiaDerecha = new EstrategiaGeneracionDerecha();
        this.estrategiaActual = estrategiaNormal; // empezamos con la normal
    }
    
    // cambia la estrategia segun el estado del juego
    public void setEstrategia(GameScreen.EstadoJuego estado) {
        switch (estado) {
            case NORMAL:
                estrategiaActual = estrategiaNormal;
                break;
            case GRAVEDAD_IZQUIERDA:
                estrategiaActual = estrategiaIzquierda;
                break;
            case GRAVEDAD_DERECHA:
                estrategiaActual = estrategiaDerecha;
                break;
        }
    }
    
    public void iniciar() {
        crearObjetoCayendo();
        rainMusic.setLooping(true);
        rainMusic.play();
    }
    
    // reorienta todos los objetos existentes y cambia la estrategia
    public void reorientarTodosLosObjetos(GameScreen.EstadoJuego nuevoEstado) {
        for (ObjetoCayendo objeto : objetos) {
            objeto.reorientar(nuevoEstado);
        }
        setEstrategia(nuevoEstado);
    }
    
    // crea un nuevo objeto usando la estrategia actual
    private void crearObjetoCayendo() {
        GestorDeRecursos recursos = GestorDeRecursos.getInstancia();
        ObjetoCayendo objeto = estrategiaActual.generarObjeto(recursos);
        objetos.add(objeto);
        lastDropTime = TimeUtils.nanoTime();
    }
    
    // actualiza todos los objetos y detecta colisiones
    public ObjetoCayendo actualizar(float delta, Tarro tarro, GameScreen.EstadoJuego estado) {
        // generar nuevos objetos cada cierto tiempo
        if (TimeUtils.nanoTime() - lastDropTime > 500000000 && estado != GameScreen.EstadoJuego.AVISO_GIRO) {
            crearObjetoCayendo();
        }
        
        // actualizar cada objeto
        for (int i = objetos.size - 1; i >= 0; i--) {
            ObjetoCayendo objeto = objetos.get(i);
            objeto.actualizar(delta); // usa el template method
            
            // eliminar si se sale de la pantalla
            if (objeto.getArea().y + 64 < 0 || objeto.getArea().x + 64 < 0 || objeto.getArea().x > 800) {
                objetos.removeIndex(i);
            } 
            // detectar colision con el tarro
            else if (objeto.getArea().overlaps(tarro.getArea())) {
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
    public void destruir() { rainMusic.dispose(); }
}