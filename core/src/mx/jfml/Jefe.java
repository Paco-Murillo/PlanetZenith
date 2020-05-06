package mx.jfml;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;

public class Jefe extends Enemigo {

    private int vidaOriginal;

    public Jefe(Texture textura, float x, float y, float vx, float vy, float vida, World mundo) {
        super(textura, x, y, vx, vy, vida, mundo);
        vidaOriginal = (int)vida;
    }

    public int getVidaOriginal() {
        return vidaOriginal;
    }
}
