package mx.jfml;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bala extends Objeto {

	//velocidad
	private float vx = 350; //Pixeles por segundo

	public Bala(Texture textura, float x, float y) {
		super(textura, x, y);
	}

	public void mover(float dt){
		float dx = vx*dt;
		sprite.setX(sprite.getX()+dx);
	}
}
