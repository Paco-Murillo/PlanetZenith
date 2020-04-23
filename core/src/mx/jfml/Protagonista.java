package mx.jfml;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;

public class Protagonista extends Personaje {
    public Protagonista(Texture textura, float x, float y, float vx, float vy, float vida, World mundo) {
        super(textura, x, y, vx, vy, 500, mundo);
    }

    @Override
    public void moverX(float delta) {
        super.moverX(delta);
    }

    @Override
    public void moverY(float delta) {
        super.moverY(delta);
    }

    @Override
    public float getVida() {
        return super.getVida();
    }

    @Override
    public void setVida(float danio) {
        super.setVida(danio);
    }
}
