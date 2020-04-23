package mx.jfml;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

public class cargarMapa {
    private static final float Tamanio_Bloque = 1;

    public static void crearCuerpos(TiledMap mapa, World mundo) {
        //Obtener los objetos con el nombre piso del tiled map
        MapObjects objetos = mapa.getLayers().get("Piso").getObjects();
        for(MapObject objeto: objetos){
            Shape recatangulo = getRectangle((RectangleMapObject)objeto); //cargar la forma del rectangulo, para cada objeto generado

        }
    }

    //Crear una nueva forma poligonal con el objeto mandado
    private static PolygonShape getRectangle(RectangleMapObject objeto) {
        //Obtener el rectangulo del objeto
        Rectangle rectangulo = objeto.getRectangle();
        //Crear nuevo poligono a usar
        PolygonShape polygon = new PolygonShape();

        Vector2 tamanio = new Vector2((rectangulo.width*0.5f)/Tamanio_Bloque,(rectangulo.height*0.5f)/Tamanio_Bloque);
        polygon.setAsBox(rectangulo.width*0.5f/Tamanio_Bloque, rectangulo.height*0.5f/Tamanio_Bloque, tamanio, 0f);
        return polygon;
    }
}
