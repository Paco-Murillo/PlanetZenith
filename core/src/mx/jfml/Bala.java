package mx.jfml;

import com.badlogic.gdx.graphics.Texture;

public class Bala extends Objeto {

	private float vx;
	private float vy;
	private float danio;

	/**
	 * La clase Bala nos permite representar un proyectil, ya sea del Protagonista, Enemigo o Jefe
	 * esta clase no respeta la gravedad.
	 * @param textura La imagen que representa este proyectil
	 * @param x Posicion inicial en x
	 * @param y Posicion inicial en y
	 * @param vx Velocidad definida en x
	 * @param vy Velocidad definida en y
	 * @param danio Danio que hara al impactar con un Sprite
	 */

	public Bala(Texture textura, float x, float y, float vx, float vy, float danio)
	{
		super(textura, x, y);
		this.vx=vx;
		this.vy=vy;
		this.danio = danio;

	}

	/**
	 * Permite actualizar la posicion en x del Sprite que representa la bala basado en la cantidad de
	 * tiempo que ha pasado asi como la velocidad definida en el constructor
	 * @param dt Cantidad de tiempo que ha pasado desde la llamada anterior al metodo
	 */
	public void moverX(float dt){
		float dx = vx*dt;
		sprite.setX(sprite.getX()+dx);
	}

	/**
	 * Permite actualizar la posicion en y del Sprite que representa la bala basado en la cantidad de
	 * tiempo que ha pasado asi como la velocidad definida en el constructor
	 * @param dt Cantidad de tiempo que ha pasado desde la llamada anterior al metodo
	 */
    public void moverY(float dt){
        float dy = vy*dt;
        sprite.setY(sprite.getY()+dy);
    }

    public float getDanio(){ return danio;}

	public float getVx() {
		return vx;
	}

	public float getVy() {
		return vy;
	}

	@Override
	public boolean equals(Object o){
		if(o instanceof Bala) {
			Bala bala = (Bala)o;
			return bala.sprite.getX() == sprite.getX();
		}
		return super.equals(o);
	}
}
