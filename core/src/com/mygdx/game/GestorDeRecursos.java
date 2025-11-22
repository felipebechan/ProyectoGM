package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.GdxRuntimeException;

// patron singleton para manejar todos los recursos del juego
// asi no cargamos las texturas y sonidos varias veces
public class GestorDeRecursos {
    private static GestorDeRecursos instancia; // la unica instancia que va a existir
    
    // texturas del juego
    private Texture fondo;
    private Texture cestaImg;
    private Texture manzanaImg;
    private Texture sandiaImg;
    private Texture bombaImg;
    private Texture estrellaImg;
    private Texture giroImg;
    private Texture tamanioImg;
    private Texture velocidadImg;
    
    // sonidos y musica
    private Sound hurtSound;
    private Sound dropSound;
    private Sound sonidoAlerta;
    private Sound sonidoGiro;
    private Music rainMusic;
    
    // constructor privado para que nadie pueda crear instancias desde afuera
    private GestorDeRecursos() {
        cargarRecursos();
    }
    
    // metodo para obtener la unica instancia del gestor
    public static GestorDeRecursos getInstancia() {
        if (instancia == null) {
            instancia = new GestorDeRecursos();
        }
        return instancia;
    }
    
    // carga todos los recursos del juego
    private void cargarRecursos() {
        try {
            fondo = new Texture(Gdx.files.internal("fondo.png"));
            cestaImg = new Texture(Gdx.files.internal("cesta.png"));
            manzanaImg = new Texture(Gdx.files.internal("manzana.png"));
            sandiaImg = new Texture(Gdx.files.internal("sandia.png"));
            bombaImg = new Texture(Gdx.files.internal("bomba.png"));
            estrellaImg = new Texture(Gdx.files.internal("estrella.png"));
            giroImg = new Texture(Gdx.files.internal("powerup_giro.png"));
            tamanioImg = new Texture(Gdx.files.internal("powerup_tamanio.png"));
            velocidadImg = new Texture(Gdx.files.internal("powerup_velocidad.png"));
            
            hurtSound = Gdx.audio.newSound(Gdx.files.internal("hurt.ogg"));
            dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
            sonidoAlerta = Gdx.audio.newSound(Gdx.files.internal("alerta.wav"));
            sonidoGiro = Gdx.audio.newSound(Gdx.files.internal("giro.wav"));
            rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
            
        } catch (GdxRuntimeException e) {
            Gdx.app.error("GestorDeRecursos", "falto un archivo en la carpeta assets!!", e);
            Gdx.app.exit();
        }
    }
    
    // getters para acceder a los recursos
    public Texture getFondo() { return fondo; }
    public Texture getCestaImg() { return cestaImg; }
    public Texture getManzanaImg() { return manzanaImg; }
    public Texture getSandiaImg() { return sandiaImg; }
    public Texture getBombaImg() { return bombaImg; }
    public Texture getEstrellaImg() { return estrellaImg; }
    public Texture getGiroImg() { return giroImg; }
    public Texture getTamanioImg() { return tamanioImg; }
    public Texture getVelocidadImg() { return velocidadImg; }
    
    public Sound getHurtSound() { return hurtSound; }
    public Sound getDropSound() { return dropSound; }
    public Sound getSonidoAlerta() { return sonidoAlerta; }
    public Sound getSonidoGiro() { return sonidoGiro; }
    public Music getRainMusic() { return rainMusic; }
    
    // libera todos los recursos de memoria
    public void dispose() {
        if (fondo != null) fondo.dispose();
        if (cestaImg != null) cestaImg.dispose();
        if (manzanaImg != null) manzanaImg.dispose();
        if (sandiaImg != null) sandiaImg.dispose();
        if (bombaImg != null) bombaImg.dispose();
        if (estrellaImg != null) estrellaImg.dispose();
        if (giroImg != null) giroImg.dispose();
        if (tamanioImg != null) tamanioImg.dispose();
        if (velocidadImg != null) velocidadImg.dispose();
        
        if (hurtSound != null) hurtSound.dispose();
        if (dropSound != null) dropSound.dispose();
        if (sonidoAlerta != null) sonidoAlerta.dispose();
        if (sonidoGiro != null) sonidoGiro.dispose();
        if (rainMusic != null) rainMusic.dispose();
        
        instancia = null;
    }
}