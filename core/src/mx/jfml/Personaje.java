package mx.jfml;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import java.awt.Polygon;

public abstract class Personaje extends Objeto{

    //Velocidades
    private float vy;
    private float vx;
    //private int contactonum;

    protected Body body;

    //Movimientos
    protected Movimientos movimiento = Movimientos.DERECHA;

    private float vida;

    /**
     * La clase abstracta Personaje representa a aquellos objetos (personificables)
     * que se mueven en el mapa y son afectados por la gravedad
     * @param textura La imagen que representa este proyectil
     * @param x Posicion inicial en x
     * @param y Posicion inicial en y
     * @param vx Velocidad definida en x
     * @param vy Velocidad definida en y
     * @param vida Vida del objeto creado
     * @param mundo Animacion en donde se agrega el "cuerpo" que representa a este objeto en la
     *              animacion
     */
    public Personaje(Texture textura, float x, float y, float vx, float vy, float vida, World mundo) {
        super(textura, x, y);
        this.vida = vida;
        this.vx = vx;
        this.vy = vy;

        if(!(this instanceof Jefe)) {
            body = crearBody(x, y, mundo);
            FixtureDef fixtureDef = crearFixtureDef(textura);
            body.createFixture(fixtureDef).setUserData("personaje");
        }
    }

    private Body crearBody(float x, float y, World mundo){
        BodyDef bodydef = new BodyDef();
        bodydef.type = BodyDef.BodyType.DynamicBody;
        bodydef.position.set(x+sprite.getWidth()/2, y+sprite.getHeight()/2);
        bodydef.fixedRotation = true;
        body = mundo.createBody(bodydef);

        return body;
    }

    private FixtureDef crearFixtureDef(Texture textura){
        PolygonShape rectangulo = new PolygonShape();
        rectangulo.setAsBox(textura.getWidth()/2f, textura.getHeight()/2f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = rectangulo;
        fixtureDef.friction = 1;
        fixtureDef.restitution = 0;

        return fixtureDef;
    }

    public Movimientos getMovimiento() {
        return movimiento;
    }

    public float getVida(){
        return vida;
    }

    public void setMovimiento(Movimientos movimiento) {
        this.movimiento = movimiento;
    }

    public void setVida(float danio){
        this.vida = vida-danio;
    }

    /*
    public int getContactonum() {
        return contactonum;
    }

    public void setContactonum(int contactonum) {
        this.contactonum = contactonum;
    }
    */

    public enum Movimientos {
        DERECHA,
        IZQUIERDA,
        QUIETO
    }


}
