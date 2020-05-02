package mx.jfml;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Enemigo extends Personaje {
    //Para usarlo en disparando después

    private boolean disparando;
    private boolean sueloDisponible;

    public Enemigo(Texture textura, float x, float y, float vx, float vy, float vida, World mundo) {
        super(textura, x, y, vx, vy, vida, mundo);
        movimiento = Movimientos.IZQUIERDA;
        disparando = false;
        sueloDisponible = true;
        crearSueloSensor(textura);
    }

    public boolean isDisparando() {
        return disparando;
    }

    public void setDisparando(boolean disparando) {
        this.disparando = disparando;
    }


    public void crearSueloSensor(Texture textura){
        PolygonShape rectangulo = new PolygonShape();
        rectangulo.setAsBox(4,5, new Vector2(-textura.getWidth()/2, -textura.getHeight()/2),0);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape=rectangulo;
        fixtureDef.isSensor=true;
        body.createFixture(fixtureDef).setUserData("sensorEnemigo");

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
