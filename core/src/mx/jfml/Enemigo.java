package mx.jfml;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;

public class Enemigo extends Personaje {
    //Para usarlo en disparando después

    private boolean disparando;

    public Enemigo(Texture textura, float x, float y, float vx, float vy, float vida, World mundo) {
        super(textura, x, y, vx, vy, vida, mundo);
        movimiento = Movimientos.IZQUIERDA;
        disparando = false;
    }

    public boolean isDisparando() {
        return disparando;
    }

    public void setDisparando(boolean disparando) {
        this.disparando = disparando;
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof Enemigo) {
            Enemigo enemigo = (Enemigo)o;
            return enemigo.sprite.getX() == sprite.getX() && enemigo.sprite.getY() == sprite.getY();
        }
        return super.equals(o);
    }
    
    
}
