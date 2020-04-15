package mx.jfml;

import com.badlogic.gdx.graphics.Texture;

public class Bala extends Objeto {

	//velocidad
	private float vx; //Pixeles por segundo
	private float vy; //Pixeles por segundi
	private float danio;

	public Bala(Texture textura, float x, float y, float vx, float vy, float danio)
	{
		super(textura, x, y);
		this.vx=vx;
		this.vy=vy;
		this.danio = danio;

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

    public float getDanio(){ return danio;}

	public boolean equals(Bala bala){
        return bala.sprite.getX() == sprite.getX();
    }
}
