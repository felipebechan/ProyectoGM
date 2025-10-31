package com.mygdx.game;
// adaptacion

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

// adaptacion
public class ProyectoJuegoLluvia extends Game {


	private SpriteBatch batch;
	private BitmapFont font;
	private int higherScore;

	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont(); 
		this.setScreen(new MainMenuScreen(this));
	}

	public void render() {
		super.render(); 
	}

	public void dispose() {
		batch.dispose();
		font.dispose();
	}

	//  contexto
	public SpriteBatch getBatch() {
		return batch;
	}

	public BitmapFont getFont() {
		return font;
	}

	public int getHigherScore() {
		return higherScore;
	}

	public void setHigherScore(int higherScore) {
		this.higherScore = higherScore;
	}
}