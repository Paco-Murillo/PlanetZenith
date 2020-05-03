package mx.jfml;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Protagonista extends Personaje {
    public Protagonista(Texture textura, float x, float y, float vx, float vy, float vida, World mundo) {
        super(textura, x, y, vx, vy, vida, mundo);
        crearSensorPie(textura);

    }
    public void crearSensorPie(Texture textura){
        PolygonShape rectangulo = new PolygonShape();
        rectangulo.setAsBox(8,5, new Vector2(0, -textura.getHeight()/2),0);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape=rectangulo;
        fixtureDef.isSensor=true;
        body.createFixture(fixtureDef).setUserData("sensorPie");
    }
}
