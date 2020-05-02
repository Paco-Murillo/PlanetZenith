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
    private int contactonum;

    protected Body body;
    private FixtureDef fixtureDef;

    //Movimientos
    protected Movimientos movimiento = Movimientos.DERECHA;

    public Movimientos getMovimiento() {
        return movimiento;
    }

    public void setMovimiento(Movimientos movimiento) {
        this.movimiento = movimiento;
    }

    private float vida;

    public Personaje(Texture textura, float x, float y, float vx, float vy, float vida, World mundo) {
        super(textura, x, y);
        this.vida = vida;
        this.vx = vx;
        this.vy = vy;

        body = crearBody(x,y,mundo);
        fixtureDef = crearFixtureDef(textura);
        body.createFixture(fixtureDef);
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
        fixtureDef.friction = 1f;
        fixtureDef.restitution = 0f;

        return fixtureDef;
    }

    public float getVida(){
        return vida;
    }

    public void setVida(float danio){
        this.vida = vida-danio;
    }

    public int getContactonum() {
        return contactonum;
    }

    public void setContactonum(int contactonum) {
        this.contactonum = contactonum;
    }

    public enum Movimientos {
        DERECHA,
        IZQUIERDA,
        QUIETO
    }


}
