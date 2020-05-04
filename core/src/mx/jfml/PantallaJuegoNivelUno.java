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
        crearMundo();
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
        Enemigo enemigo = new Enemigo(enemigoTexture, 792, 72, 1f, 30f, 30f, mundo);
        //enemigo.direccion= Personaje.Movimientos.IZQUIERDA;
        arrEnemigos.add(enemigo);
        enemigo = new Enemigo(enemigoTexture, 936, 396, 1f, 30f, 30f, mundo);
        arrEnemigos.add(enemigo);
        enemigo = new Enemigo(enemigoTexture, 1728, 180, 1f, 30f, 30f, mundo);
        arrEnemigos.add(enemigo);
        enemigo = new Enemigo(enemigoTexture, 2232, 180, 1f, 30f, 30f, mundo);
        arrEnemigos.add(enemigo);
        enemigo = new Enemigo(enemigoTexture, 2952, 72, 1f, 30f, 30f, mundo);
        arrEnemigos.add(enemigo);
        enemigo = new Enemigo(enemigoTexture, 3348, 288, 1f, 30f, 30f, mundo);
        arrEnemigos.add(enemigo);
        enemigo = new Enemigo(enemigoTexture, 3852, 216, 1f, 30f, 30f, mundo);
        arrEnemigos.add(enemigo);
        enemigo = new Enemigo(enemigoTexture, 4500, 432, 1f, 30f, 30f, mundo);
        arrEnemigos.add(enemigo);
        enemigo = new Enemigo(enemigoTexture, 4896, 72, 1f, 30f, 30f, mundo);
        arrEnemigos.add(enemigo);
        enemigo = new Enemigo(enemigoTexture, 5400, 72, 1f, 30f, 30f, mundo);
        arrEnemigos.add(enemigo);
        enemigo = new Enemigo(enemigoTexture, 5724, 252, 1f, 30f, 30f, mundo);
        arrEnemigos.add(enemigo);
        enemigo = new Enemigo(enemigoTexture, 6120, 180, 1f, 30f, 30f, mundo);
        arrEnemigos.add(enemigo);
        jefe = new Jefe(new Texture("Enemigos/JefeUnoJetPack.png"), 6876, 72, 1f, 30f, 400, mundo); //Checar JEFE
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
