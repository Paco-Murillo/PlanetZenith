package mx.jfml;


public class Bala extends Objeto {

	//velocidad
	private float vx = 350; //Pixeles por segundo

	public Bala(Texture textura, float x, float y) {
		super(textura, x, y);
	}
	//Mover
	public void mover(float dt){
		float dx = vx*dt;
		sprite.setX(sprite.getX()+dx);
	}
}
