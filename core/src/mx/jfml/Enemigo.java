package mx.jfml;

import com.badlogic.gdx.graphics.Texture;

public class Enemigo extends Objeto {
    private float life = 200f;
    private float vx = 360;

    public Enemigo(Texture textura, float x, float y) {
        super(textura, x, y);
    }
    public void mover(float dt){
        float dx = vx*dt;
        sprite.setX(sprite.getX()+dx);
    }
    public float getLifeEnemigo(){
        return life;
    }
    public void setLifeEnemigo(float dano){
        this.life = life-dano;
    }
}
