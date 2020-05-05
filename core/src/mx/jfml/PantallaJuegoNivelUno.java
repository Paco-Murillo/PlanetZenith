package mx.jfml;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.Array;

public class PantallaJuegoNivelUno extends Nivel {

    private static final int ANCHO_MAPA = 6400;
    private static final int ALTO_MAPA = 736;

    private boolean batallaJefeActiva;
    private boolean paredesBatallaJefeActivas;



    public PantallaJuegoNivelUno(Juego juego){super(juego);}

    @Override
    public void show() {
        crearGravedad();
        crearMundo(gravedad);
        crearEnemigos();
        super.show();
        cargaMapa("MapaJuego.tmx");
        cargarTexturaBala("Proyectiles/bala1.png");
        Gdx.input.setInputProcessor(HUD);
        definirParedes();
    }

    private void checarInicioBatallaJefe() {
        if (camara.position.x >= ANCHO_MAPA-ANCHO/2-10 && !batallaJefeActiva){
            camara.position.set(ANCHO_MAPA-ANCHO/2, camara.position.y, 0);
            camara.update();
            batallaJefeActiva = true;
        }
    }

    private void crearEnemigos(){
        arrEnemigos = new Array<>(10);
        Texture enemigoTexture = new Texture("Enemigos/EnemigoGeneralVerde.png");
        Enemigo enemigo = new Enemigo(enemigoTexture, 792, 72, 1f, 30f, 30f, mundo); //Enmigo 1
        //enemigo.direccion= Personaje.Movimientos.IZQUIERDA;
        arrEnemigos.add(enemigo);
        enemigo = new Enemigo(enemigoTexture, 900, 416, 1f, 30f, 30f, mundo); //Enemigo 2
        arrEnemigos.add(enemigo);
        enemigo = new Enemigo(enemigoTexture, 1472, 160, 1f, 30f, 30f, mundo); //Enemigo 3
        arrEnemigos.add(enemigo);
        enemigo = new Enemigo(enemigoTexture, 1920, 160, 1f, 30f, 30f, mundo); //Enemigo 4
        arrEnemigos.add(enemigo);
        enemigo = new Enemigo(enemigoTexture, 2720, 64, 1f, 30f, 30f, mundo); //Enemigo 5
        arrEnemigos.add(enemigo);
        enemigo = new Enemigo(enemigoTexture, 3008, 256, 1f, 30f, 30f, mundo); //Enemigo 6
        arrEnemigos.add(enemigo);
        enemigo = new Enemigo(enemigoTexture, 3456, 192, 1f, 30f, 30f, mundo); //Enemigo 7
        arrEnemigos.add(enemigo);
        enemigo = new Enemigo(enemigoTexture, 4000, 384, 1f, 30f, 30f, mundo); //Enemigo 8
        arrEnemigos.add(enemigo);
        enemigo = new Enemigo(enemigoTexture, 4384, 64, 1f, 30f, 30f, mundo); //Enemigo 9
        arrEnemigos.add(enemigo);
        enemigo = new Enemigo(enemigoTexture, 4960, 64, 1f, 30f, 30f, mundo); //Enemigo 10
        arrEnemigos.add(enemigo);
        enemigo = new Enemigo(enemigoTexture, 5088, 224, 1f, 30f, 30f, mundo); //Enemigo 11
        arrEnemigos.add(enemigo);
        enemigo = new Enemigo(enemigoTexture, 5440, 160, 1f, 30f, 30f, mundo); //Enenmigo 12
        arrEnemigos.add(enemigo);
        jefe = new Jefe(new Texture("Enemigos/JefeUnoJetPack.png"), 6100, 72, 1f, 30f, 400, mundo); //Checar JEFE
        batallaJefeActiva = false;
        paredesBatallaJefeActivas = false;
    }

    @Override
    protected void actualizarCamara() {
        if(protagonista.sprite.getX() > ANCHO/2 && protagonista.sprite.getX() < ANCHO_MAPA-ANCHO/2 && !batallaJefeActiva) { // 6400 = Ancho en
            camara.position.set(protagonista.sprite.getX(), camara.position.y, 0);
            camara.update();
        }
    }

    @Override
    protected void moverBala() {
        for(int indexBalas = 0; indexBalas < arrBalas.size; indexBalas++){
            if(arrBalas.get(indexBalas) == null) continue;
            Bala bala = arrBalas.get(indexBalas);
            bala.moverX(.1f);
            //Salio??
            if(bala.sprite.getX() > ANCHO_MAPA){
                arrBalas.removeIndex(indexBalas);
                contadorBalas--;
            }
        }
    }

    @Override
    public void render(float delta){
        super.render(delta);
        /*
        if(estadoJuego == EstadoJuego.JUGANDO) {
            checarInicioBatallaJefe();
            batallaJefe();
        }
        */
    }

    private void batallaJefe() {
        if (batallaJefeActiva){
            if(!paredesBatallaJefeActivas) {
                crearParedesBatallaJefe();
                paredesBatallaJefeActivas = true;
            }
        }
    }

    private void crearParedesBatallaJefe() {
        MapObjects objetos = mapa.getLayers().get("ParedesJefe").getObjects();
        for(MapObject objeto: objetos){
            Shape rectangulo = cargarMapa.getRectangle((RectangleMapObject)objeto);
            BodyDef bd = new BodyDef();
            bd.position.set(((RectangleMapObject) objeto).getRectangle().x, ((RectangleMapObject) objeto).getRectangle().y);
            bd.type  = BodyDef.BodyType.StaticBody;
            Body body = mundo.createBody(bd);
            body.createFixture(rectangulo,1);
            rectangulo.dispose();
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        texturaBala.dispose();
    }

}
