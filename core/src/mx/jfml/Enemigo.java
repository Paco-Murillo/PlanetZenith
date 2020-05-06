package mx.jfml;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Enemigo extends Personaje {
    //Para usarlo en disparando después

    private int tiempoDisparos;
    private boolean sueloDisponible;
    private int index;

    public Enemigo(Texture textura, float x, float y, float vx, float vy, float vida, World mundo,int index) {
        super(textura, x, y, vx, vy, vida, mundo);
        movimiento = Movimientos.IZQUIERDA;
        tiempoDisparos = 0;
        sueloDisponible = true;
        this.index=index;
        crearSensores(textura);
    }

    public int getTiempoDisparos() {
        return tiempoDisparos;
    }

    public void setTiempoDisparos(int tiempoDisparos) {
        this.tiempoDisparos = tiempoDisparos;
    }

    public void crearSensores(Texture textura){
        String indice = Integer.toString(index);
        PolygonShape rectangulo = new PolygonShape();
        rectangulo.setAsBox(4,5, new Vector2(-textura.getWidth()/2-10, -textura.getHeight()/2),0);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape=rectangulo;
        fixtureDef.isSensor=true;
        body.createFixture(fixtureDef).setUserData("sensorEnemigoIzquierda");
        rectangulo.setAsBox(8,5,new Vector2(0,-textura.getHeight()/2),9);
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
