package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen {
    final ProyectoJuegoLluvia game;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private BitmapFont font;
    private Tarro tarro;
    private ControladorDeObjetos controlador;
    private Texture fondo;

    public enum EstadoJuego {
        NORMAL,
        AVISO_GIRO,
        GRAVEDAD_IZQUIERDA,
        GRAVEDAD_DERECHA
    }
    private EstadoJuego estadoActual = EstadoJuego.NORMAL;
    private float tiempoPowerUp = 0f;
    private float tiempoAviso = 0f;
    private final float DURACION_POWERUP = 10f; 
    private final float DURACION_AVISO = 2f;
    private Sound sonidoAlerta, sonidoGiro;

    public GameScreen(final ProyectoJuegoLluvia game) {
        this.game = game;
        this.batch = game.getBatch();
        this.font = game.getFont();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        // usamos el singleton para obtener todos los recursos
        GestorDeRecursos recursos = GestorDeRecursos.getInstancia();
        fondo = recursos.getFondo();
        sonidoAlerta = recursos.getSonidoAlerta();
        sonidoGiro = recursos.getSonidoGiro();

        tarro = new Tarro(recursos.getCestaImg(), recursos.getHurtSound());
        controlador = new ControladorDeObjetos(recursos.getRainMusic());

        tarro.crear();
        controlador.iniciar();
    }

    @Override
    public void render(float delta) {
        // actualizamos los timers de los powerups del tarro
        tarro.actualizar(delta);

        if (estadoActual == EstadoJuego.AVISO_GIRO) {
            tiempoAviso -= delta;
            if (tiempoAviso <= 0) {
                sonidoGiro.play();
                EstadoJuego nuevoEstado = MathUtils.randomBoolean() ? EstadoJuego.GRAVEDAD_IZQUIERDA : EstadoJuego.GRAVEDAD_DERECHA;
                estadoActual = nuevoEstado;
                tiempoPowerUp = DURACION_POWERUP;
                tarro.reposicionarYRotar(nuevoEstado);
                controlador.reorientarTodosLosObjetos(nuevoEstado);
            }
        } else if (estadoActual != EstadoJuego.NORMAL) {
            tiempoPowerUp -= delta;
            if (tiempoPowerUp <= 0) {
                estadoActual = EstadoJuego.NORMAL;
                tarro.reposicionarYRotar(EstadoJuego.NORMAL);
                controlador.setEstrategia(EstadoJuego.NORMAL); // volvemos a la estrategia normal
            }
        }

        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        if (fondo != null) batch.draw(fondo, 0, 0, 800, 480);

        // dibujamos el hud
        font.draw(batch, "Puntos: " + tarro.getPuntos(), 5, 475);
        font.draw(batch, "Vidas : " + tarro.getVidas(), 670, 475);
        font.draw(batch, "HighScore : " + game.getHigherScore(), camera.viewportWidth / 2 - 100, 475);
        
        // dibujamos el estado de los powerups
        int hudY = 450;
        if (estadoActual == EstadoJuego.AVISO_GIRO) {
            font.draw(batch, "¡¡¡GIRO INMINENTE!!!", camera.viewportWidth / 2 - 150, 240);
        } else if (estadoActual != EstadoJuego.NORMAL) {
            font.draw(batch, "MODO GIRO: " + (int)tiempoPowerUp, camera.viewportWidth / 2 - 100, hudY);
            hudY -= 20; // bajamos la posicion para el siguiente texto
        }
        if (tarro.esGrande()) {
            font.draw(batch, "CESTA GRANDE: " + (int)tarro.getTiempoTamanio(), camera.viewportWidth / 2 - 100, hudY);
            hudY -= 20;
        }
        if (tarro.esRapido()) {
            font.draw(batch, "SUPER VELOCIDAD: " + (int)tarro.getTiempoVelocidad(), camera.viewportWidth / 2 - 100, hudY);
        }

        actualizarMovimientoJugador(delta);
        ObjetoCayendo objetoRecogido = controlador.actualizar(delta, tarro, estadoActual);
        
        if (objetoRecogido != null) {
            if (objetoRecogido instanceof PowerUpGiro) {
                estadoActual = EstadoJuego.AVISO_GIRO;
                tiempoAviso = DURACION_AVISO;
                sonidoAlerta.play();
            } else if (tarro.getVidas() <= 0) {
                if (game.getHigherScore() < tarro.getPuntos()) game.setHigherScore(tarro.getPuntos());
                game.setScreen(new GameOverScreen(game));
                dispose();
                return; 
            }
        }
        
        tarro.render(batch);
        controlador.render(batch);
        
        batch.end();
    }
    
    private void actualizarMovimientoJugador(float delta) {
        if (!tarro.estaHerido()) { // solo nos movemos si no estamos heridos
            if (estadoActual == EstadoJuego.NORMAL) {
                tarro.actualizarMovimientoHorizontal(delta);
            } else { 
                tarro.actualizarMovimientoVertical(delta);
            }
        }
    }
    
    @Override
    public void dispose() {
        tarro.destruir();
        controlador.destruir();
        // no disposear fondo ni sonidos, el singleton lo maneja
    }
    
    @Override
    public void show() { controlador.continuar(); }
    @Override
    public void pause() {
        controlador.pausar();
        game.setScreen(new PausaScreen(game, this));
    }
    @Override
    public void resize(int width, int height) {}
    @Override
    public void hide() {}
    @Override
    public void resume() {}
}