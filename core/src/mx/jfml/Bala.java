package mx.jfml;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bala extends Objeto {

	//velocidad
	private float vx; //Pixeles por segundo
	private  float vy; //Pixeles por segundi

	public Bala(Texture textura, float x, float y, float vx, float vy)
	{
		super(textura, x, y);
		this.vx=vx;
		this.vy=vy;

	}
	//Mover
	public void moverX(float dt){
		float dx = vx*dt;
		sprite.setX(sprite.getX()+dx);
	}
    public void moverY(float dt){
        float dy = vy*dt;
        sprite.setY(sprite.getY()+dy);
    }
}
