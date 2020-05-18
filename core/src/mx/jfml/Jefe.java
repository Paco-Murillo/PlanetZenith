package mx.jfml;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;

public class Jefe extends Enemigo {

    /**
     * @value vidaOriginal Valor de vida inicial
     */
    private int vidaOriginal;

    /**
     * Define objetos que se comporten como un Jefe "Boss" en el videojuego
     * @param textura La imagen que representa este proyectil
     * @param x Posicion inicial en x
     * @param y Posicion inicial en y
     * @param vx Velocidad definida en x
     * @param vy Velocidad definida en y
     * @param vida Vida del objeto creado
     * @param mundo Animacion en donde se agrega el "cuerpo" que representa a este objeto en la
     *              animacion
     */
    public Jefe(Texture textura, float x, float y, float vx, float vy, float vida, World mundo) {
        super(textura, x, y, vx, vy, vida, mundo, TipoEnemigo.JEFE);
        vidaOriginal = (int)vida;
    }

    public int getVidaOriginal() {
        return vidaOriginal;
    }
}
