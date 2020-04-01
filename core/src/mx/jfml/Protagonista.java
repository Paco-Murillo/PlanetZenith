package mx.jfml;

import com.badlogic.gdx.graphics.Texture;

public class Protagonista extends Personaje {
    public Protagonista(Texture textura, float x, float y, float vx, float vy, float vida) {
        super(textura, x, y, vx, vy, vida);
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
