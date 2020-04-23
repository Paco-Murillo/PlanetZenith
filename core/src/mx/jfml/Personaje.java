package mx.jfml;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Personaje extends Objeto{

    //Velocidades
    protected float vx;
    protected Body body;


    protected float vy;

    protected float vida;

    public Personaje(Texture textura, float x, float y, float vx, float vy, float vida, World mundo) {
        super(textura, x, y);
        this.vida = vida;
        this.vx = vx;
        this.vy = vy;



        BodyDef bodydef = new BodyDef();
        bodydef.type = BodyDef.BodyType.DynamicBody; //StaticBody
        bodydef.position.set(x, y);
        body = mundo.createBody(bodydef);   // objeto simulado




        PolygonShape rectangulo = new PolygonShape();
        rectangulo.setAsBox(textura.getWidth(), textura.getHeight());

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = rectangulo;
        fixtureDef.density = 0.3f;
        fixtureDef.friction = 0.1f;
        fixtureDef.restitution = 0f;
        body.createFixture(fixtureDef);
        rectangulo.dispose();
        body.setFixedRotation(true);




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

}
