package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class Tarro implements Dibujable {
	   private Rectangle bucket;
	   private Texture bucketImage;
	   private Sound sonidoHerido;
	   private int vidas = 3;
	   private int puntos = 0;
	   private int velx = 400;
	   private boolean herido = false;
	   private int tiempoHeridoMax=50;
	   private int tiempoHerido;
	   private float rotacion = 0f;

       // atributos
       private boolean esGrande = false;
       private boolean esRapido = false;
       private float tiempoTamanio = 0f;
       private float tiempoVelocidad = 0f;
       private final float DURACION_POWERUP = 8f;
       private final float ESCALA_GRANDE = 1.5f;
       private final float MULTIPLICADOR_VELOCIDAD = 1.5f;
	   
	   public Tarro(Texture tex, Sound ss) {
		   bucketImage = tex;
		   sonidoHerido = ss;
	   }
	   
		public int getVidas() { return vidas; }
		public int getPuntos() { return puntos; }
		public Rectangle getArea() { return bucket; }
		public void sumarPuntos(int pp) { puntos+=pp; }
        public void ganarVida() { if (vidas < 5) vidas++; }

        public boolean esGrande() { return esGrande; }
        public boolean esRapido() { return esRapido; }
        public float getTiempoTamanio() { return tiempoTamanio; }
        public float getTiempoVelocidad() { return tiempoVelocidad; }
        
        public void activarPowerUpTamanio() {
           esGrande = true;
           tiempoTamanio = DURACION_POWERUP;
        }
        public void activarPowerUpVelocidad() {
           esRapido = true;
           tiempoVelocidad = DURACION_POWERUP;
        }
        
        public void actualizar(float delta) {
           if (esGrande) {
               tiempoTamanio -= delta;
               if (tiempoTamanio <= 0) {
                   esGrande = false;
               }
           }
           if (esRapido) {
               tiempoVelocidad -= delta;
               if (tiempoVelocidad <= 0) {
                   esRapido = false;
               }
           }
        }
    
		
	   public void crear() {
		    bucket = new Rectangle();
		    reposicionarYRotar(GameScreen.EstadoJuego.NORMAL);
	   }

	   public void reposicionarYRotar(GameScreen.EstadoJuego nuevoEstado) {
		   switch (nuevoEstado) {
			   case NORMAL:
				   bucket.x = 800 / 2 - 64 / 2;
		      	   bucket.y = 20;
				   bucket.width = 64;
				   bucket.height = 32;
				   rotacion = 0;
				   break;
			   case GRAVEDAD_IZQUIERDA:
				   bucket.x = 20; 
				   bucket.y = 480 / 2 - 64 / 2;
				   bucket.width = 32;
				   bucket.height = 64;
				   rotacion = -90;
				   break;
			   case GRAVEDAD_DERECHA:
				   bucket.x = 800 - 32 - 20; 
				   bucket.y = 480 / 2 - 64 / 2;
				   bucket.width = 32;
				   bucket.height = 64;
				   rotacion = 90;
				   break;
		   }
	   }
	   
	   public void dañar() {
		  vidas--;
		  herido = true;
		  tiempoHerido=tiempoHeridoMax;
		  sonidoHerido.play();
	   }

       @Override
	   public void render(SpriteBatch batch) {
           float escala = esGrande ? ESCALA_GRANDE : 1f;
           float imagenAncho = 64 * escala;
           float imagenAlto = 64 * escala;

           float drawX = bucket.x - (imagenAncho - bucket.width) / 2;
           float drawY = bucket.y - (imagenAlto - bucket.height) / 2;

		   float originX = imagenAncho / 2;
		   float originY = imagenAlto / 2;

		 if (!herido) {
			 batch.draw(bucketImage, drawX, drawY, originX, originY, imagenAncho, imagenAlto, 1, 1, rotacion, 0, 0, bucketImage.getWidth(), bucketImage.getHeight(), false, false);
		 } else {
			 batch.draw(bucketImage, drawX + MathUtils.random(-5,5), drawY + MathUtils.random(-5,5), originX, originY, imagenAncho, imagenAlto, 1, 1, rotacion, 0, 0, bucketImage.getWidth(), bucketImage.getHeight(), false, false);
		   tiempoHerido--;
		   if (tiempoHerido<=0) herido = false;
		 }
	   } 
	   
       public void actualizarMovimientoHorizontal(float delta) {
            float velocidadActual = esRapido ? velx * MULTIPLICADOR_VELOCIDAD : velx;
            boolean MoverIzquierda = Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A);
            boolean MoverDerecha = Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D);
            if(MoverIzquierda) bucket.x -= velocidadActual * delta;
            if(MoverDerecha) bucket.x += velocidadActual * delta;
            if(bucket.x < 0) bucket.x = 0;
            if(bucket.x > 800 - bucket.width) bucket.x = 800 - bucket.width;
        }

        public void actualizarMovimientoVertical(float delta) {
            float velocidadActual = esRapido ? velx * MULTIPLICADOR_VELOCIDAD : velx;
            boolean MoverArriba = Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W);
            boolean MoverAbajo = Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S);
            if(MoverAbajo) bucket.y -= velocidadActual * delta;
            if(MoverArriba) bucket.y += velocidadActual * delta;
            if(bucket.y < 0) bucket.y = 0;
            if(bucket.y > 480 - bucket.height) bucket.y = 480 - bucket.height;
        }
	    
	   public void destruir() { bucketImage.dispose(); }
	   public boolean estaHerido() { return herido; }
}