package mx.jfml;

import com.badlogic.gdx.graphics.Texture;

public class Enemigo extends Personaje {
    protected MovimientoEnemigos direccion;



    public Enemigo(Texture textura, float x, float y, float vx, float vy, float vida) {
        super(textura, x, y, vx, vy, vida);
        direccion = MovimientoEnemigos.QUIETO;
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

    public enum MovimientoEnemigos{
        DERECHA,
        IZQUIERDA,
        QUIETO
    }

}
