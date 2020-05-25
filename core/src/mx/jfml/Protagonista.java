package mx.jfml;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Protagonista extends Personaje {

    /**
     * La clase Protagonista representa al personaje que usaran los usuarios para recorrer el juego
     * @param textura La imagen que representa este proyectil
     * @param x Posicion inicial en x
     * @param y Posicion inicial en y
     * @param vx Velocidad definida en x
     * @param vy Velocidad definida en y
     * @param vida Vida del objeto creado
     * @param mundo Animacion en donde se agrega el "cuerpo" que representa a este objeto en la
     *              animacion
     */
    public Protagonista(Texture textura, float x, float y, float vx, float vy, float vida, World mundo) {
        super(textura, x, y, vx, vy, vida, mundo);
        crearSensorPie(textura);

    }

    private void crearSensorPie(Texture textura){
        PolygonShape rectangulo = new PolygonShape();
        rectangulo.setAsBox(12,5, new Vector2(0, -textura.getHeight()/2f),0);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape=rectangulo;
        fixtureDef.isSensor=true;
        body.createFixture(fixtureDef).setUserData("sensorPie");
    }
}
