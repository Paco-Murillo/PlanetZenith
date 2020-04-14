package mx.jfml;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

class Escritura {

    private String enunciado;

    private float x;
    private float y;

    private Texto texto;

    public Escritura(float x, float y){
        this.x = x;
        this.y = y;
        this.texto = new Texto( "Fuentes/fuente60px.fnt");
    }

    public void setEnunciado(String enunciado){
        this.enunciado = enunciado;
    }

    public void render(SpriteBatch batch){
        texto.render(batch, enunciado, x, y);
    }
}
