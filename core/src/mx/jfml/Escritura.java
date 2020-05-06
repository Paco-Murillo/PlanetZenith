package mx.jfml;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Escritura {

    private String enunciado;
    private float x;
    private float y;
    private Texto texto;

    /**
     * La clase Escritura permite dibujar texto en la pantalla
     * @param x Posicion en x
     * @param y Posicion en y
     */
    public Escritura(float x, float y){
        this.x = x;
        this.y = y;
        this.texto = new Texto("Fuentes/fuente60px.fnt");
    }

    /** Se llama para dibujar el texto */
    public void render(SpriteBatch batch){ texto.render(batch, enunciado, x, y); }

    public void setEnunciado(String enunciado){ this.enunciado = enunciado; }

    public void setTexto(Texto texto){ this.texto = texto; }
}
