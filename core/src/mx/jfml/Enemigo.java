package mx.jfml;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;

public class Enemigo extends Personaje {
    protected MovimientoEnemigos direccion;



    public Enemigo(Texture textura, float x, float y, float vx, float vy, float vida, World mundo) {
        super(textura, x, y, vx, vy, vida, mundo);
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
