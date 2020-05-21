package mx.jfml;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Enemigo extends Personaje {

    private int tiempoDisparos;
    protected TipoEnemigo tipoEnemigo;

    /**
     * La clase Enemigo representa a aquellos objetos que se les permite hacer danio a Protagonista
     * @param textura La imagen que representa este proyectil
     * @param x Posicion inicial en x
     * @param y Posicion inicial en y
     * @param vx Velocidad definida en x
     * @param vy Velocidad definida en y
     * @param vida Vida del objeto creado
     * @param mundo Animacion en donde se agrega el "cuerpo" que representa a este objeto en la
     *              animacion
     */
    public Enemigo(Texture textura, float x, float y, float vx, float vy, float vida, World mundo, TipoEnemigo tipoEnemigo) {
        super(textura, x, y, vx, vy, vida, mundo);
        movimiento = Movimientos.IZQUIERDA;
        tiempoDisparos = 0;
        this.tipoEnemigo=tipoEnemigo;
        if(!(this instanceof Jefe)) {
            crearSensores(textura);
        }
    }

    /**
     * Permite crear un sensor para detectar si los enemigos estan en el suelo
     * @param textura Imagen que representa al objeto
     */
    private void crearSensores(Texture textura){

        if(tipoEnemigo==TipoEnemigo.CAMINANTE) {
            PolygonShape rectangulo = new PolygonShape();
            rectangulo.setAsBox(4, 5, new Vector2(-textura.getWidth() / 2f - 10, -textura.getHeight() / 2f), 0);
            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = rectangulo;
            fixtureDef.isSensor = true;
            body.createFixture(fixtureDef).setUserData("sensorEnemigoIzquierda");
        }
        if (tipoEnemigo==TipoEnemigo.JETPACK){
            PolygonShape rectangulo = new PolygonShape();
            rectangulo.setAsBox(4, 12, new Vector2(0, -textura.getHeight() / 2f), 0);
            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = rectangulo;
            fixtureDef.isSensor = true;
            body.createFixture(fixtureDef).setUserData("sensorJetPack");

        }
    }

    public int getTiempoDisparos() {
        return tiempoDisparos;
    }

    public void setTiempoDisparos(int tiempoDisparos) {
        this.tiempoDisparos = tiempoDisparos;
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof Enemigo) {
            Enemigo enemigo = (Enemigo)o;
            return enemigo.sprite.getX() == sprite.getX() && enemigo.sprite.getY() == sprite.getY();
        }
        return super.equals(o);
    }
    protected enum TipoEnemigo{
        CAMINANTE,
        JETPACK,
        JEFE
    }
    
    
}
