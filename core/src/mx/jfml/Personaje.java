package mx.jfml;

import com.badlogic.gdx.graphics.Texture;

public abstract class Personaje extends Objeto{

    //Velocidades
    protected float vx;
    protected float vy;

    protected float vida;

    public Personaje(Texture textura, float x, float y, float vx, float vy, float vida) {
        super(textura, x, y);
        this.vida = vida;
        this.vx = vx;
        this.vy = vy;
    }

    public void moverX(float delta){
        float dx = vx*delta;
        sprite.setX(sprite.getX()+dx);
    }

    public void moverY(float delta){
        float dy = vy*delta;
        sprite.setY(sprite.getY()+dy);
    }

    public float getVida(){
        return vida;
    }

    public void setVida(float danio){
        this.vida = vida-danio;
    }

}
