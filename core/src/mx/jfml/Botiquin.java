package mx.jfml;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;


public class Botiquin extends Objeto {

    public Botiquin(Texture textura, float x, float y, World mundo){
        super(textura, x, y);
    }
    public float getVidaBotiquin(){
        return 100;
    }
}
