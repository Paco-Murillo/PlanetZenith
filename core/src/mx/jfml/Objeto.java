package mx.jfml;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Objeto {

    protected Sprite sprite;

    /**
     * Representa a cualquier cosa que se vaya a dibujar en la pantalla
     * @param textura La imagen que representa este proyectil
     * @param x Posicion inicial en x
     * @param y Posicion inicial en y
     */
    public  Objeto(Texture textura, float x, float y){
        sprite = new Sprite(textura);
        sprite.setPosition(x,y);
    }

    public void render(SpriteBatch batch){
        sprite.draw(batch);
    }

}
