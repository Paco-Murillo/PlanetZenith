package mx.jfml;
import com.badlogic.gdx.graphics.Texture;

public class Jefe extends Enemigo {

    public Jefe(Texture textura, float x, float y, float vx, float vy, float vida) {
        super(textura, x, y, 360, vy, 400);
    }
}
