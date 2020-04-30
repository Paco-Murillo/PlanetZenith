package mx.jfml;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Personaje extends Objeto{

    //Velocidades
    protected float vy;
    protected float vx;

    protected Body body;
    FixtureDef fixtureDef;

    protected float vida;

    public Personaje(Texture textura, float x, float y, float vx, float vy, float vida, World mundo) {
        super(textura, x, y);
        this.vida = vida;
        this.vx = vx;
        this.vy = vy;

        body = crearBody(x,y,mundo);
        fixtureDef = crearFixtureDef(textura);
        body.createFixture(fixtureDef);
    }

    public Body crearBody(float x, float y, World mundo){
        BodyDef bodydef = new BodyDef();
        bodydef.type = BodyDef.BodyType.DynamicBody;
        bodydef.position.set(x, y);
        bodydef.fixedRotation = true;
        body = mundo.createBody(bodydef);
        return body;
    }

    public FixtureDef crearFixtureDef(Texture textura){
        PolygonShape rectangulo = new PolygonShape();
        rectangulo.setAsBox(textura.getWidth()/2, textura.getHeight()/2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = rectangulo;
        fixtureDef.friction = 1f;
        fixtureDef.restitution = 0f;
        return fixtureDef;
    }

    public void moverX(float delta){
        float dx = vx*delta;
        sprite.setX(sprite.getX()+dx);
    }

    public void moverY(float delta){
        float dy = vy*delta;
        sprite.setY(sprite.getY()+dy);
    }

    public float getVida(){
        return vida;
    }

    public void setVida(float danio){
        this.vida = vida-danio;
    }

    public enum Movimientos {
        DERECHA,
        IZQUIERDA,
        QUIETO
    }

}
