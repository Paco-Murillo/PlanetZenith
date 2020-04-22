package mx.jfml;

import com.badlogic.gdx.graphics.Texture;

public class Enemigo extends Personaje {
    protected MovimientoEnemigos direccion;



    public Enemigo(Texture textura, float x, float y, float vx, float vy, float vida) {
        super(textura, x, y, vx, vy, vida);
        direccion = MovimientoEnemigos.QUIETO;
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof Enemigo) {
            Enemigo enemigo = (Enemigo)o;
            return enemigo.sprite.getX() == sprite.getX() && enemigo.sprite.getY() == sprite.getY();
        }
        return super.equals(o);
    }

    public enum MovimientoEnemigos{
        DERECHA,
        IZQUIERDA,
        QUIETO
    }

}
