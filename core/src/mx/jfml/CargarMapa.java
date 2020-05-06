package mx.jfml;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

public class CargarMapa {
    /**
     * @value TAMANIO_BLOQUE El tamaño de las tiles en el mapa
     */
    private static final float Tamanio_Bloque = 1;

    /**
     * Agrega a mundo (encargado de la animacion) aquellas formas que definen el
     * comportamiento del mapa
     * @param mapa Informacion del mapa grafico en un formato creado por Tiled (doc termina en .tmx)
     * @param mundo Encargado de la animacion de los cuerpos
     */
    public static void crearCuerpos(TiledMap mapa, World mundo) {
        //Obtener los objetos con el nombre piso del tiled map
        MapObjects objetos = mapa.getLayers().get("Piso").getObjects();
        for(MapObject objeto: objetos){
            Shape rectangulo = getRectangle((RectangleMapObject)objeto); //cargar la forma del rectangulo, para cada objeto generado

            //Contructor de body def
            //Los body def almacenan la infomracion de un cuerpo rigifo
            BodyDef bd = new BodyDef();
            bd.position.set(((RectangleMapObject) objeto).getRectangle().x, ((RectangleMapObject) objeto).getRectangle().y);
            bd.type  = BodyDef.BodyType.StaticBody;
            //Agregar el cuerpo al mundo
            Body body = mundo.createBody(bd);
            //Seleccionar forma de rectangulo para el cuerpo
            body.createFixture(rectangulo,1);
            //desaparecer el objeto al terminar
            rectangulo.dispose();
        }
    }

    /**
     * Crea una nueva forma poligonal con el objeto del mapa mandado
     * @param objeto Referencia del mapa que contiene la posicion, ancho y alto del cuerpo a crear
     * @return Un rectangulo en la posicion que define el RectangleMapObject
     */
    public static PolygonShape getRectangle(RectangleMapObject objeto) {
        //Obtener el rectangulo del objeto
        Rectangle rectangulo = objeto.getRectangle();
        //Crear nuevo poligono a usar
        PolygonShape polygon = new PolygonShape();

        Vector2 tamanio = new Vector2((rectangulo.width*0.5f)/Tamanio_Bloque,(rectangulo.height*0.5f)/Tamanio_Bloque);
        polygon.setAsBox(rectangulo.width*0.5f/Tamanio_Bloque, rectangulo.height*0.5f/Tamanio_Bloque, tamanio, 0f);
        return polygon;
    }
}
