package mx.jfml;
import com.badlogic.gdx.graphics.Texture;

public class Jefe extends Objeto {
    private float life = 400;
    private float vx = 360;

    public Jefe(Texture textura, float x, float y) {
        super(textura, x, y);
    }

    public void mover(float dt) {
        float dx = vx * dt;
        sprite.setX(sprite.getX() + dx);
    }

    public float getLifeJefe() {
        return life;
    }

    public void setLifeJefe(float dano) {
        this.life = life - dano;
    }
}
