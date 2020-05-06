package mx.jfml;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Not Used
 */
public class Marcador {

	private int puntos;

	private float x;
	private float y;
	private Texto texto;

	public Marcador(float x, float y){
		this.x = x;
		this.y = y;
		this.puntos = 0;
		this.texto = new Texto("fuente.fnt");

	}

	public void reset(){
		puntos = 0;

	}

	public void marcar(int puntos){
		this.puntos += puntos;

	}

	public void render(SpriteBatch batch){
		String mensaje = "puntos: " + puntos;
		texto.render(batch, mensaje,x,y);
	}
}