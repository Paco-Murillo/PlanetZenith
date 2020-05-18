package mx.jfml;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.Array;

public class PantallaJuegoNivelUno extends Nivel {

    private static final int ANCHO_MAPA = 6400;
    private static final int ALTO_MAPA = 736;

    private boolean batallaJefeActiva;
    private boolean iniciarBatallaJefe;
    private int balasJefeMax;
    private int balasCounterJefe;
    private Array<Bala> balasJefe;
    private float timeAcumDisparoJefe;
    private float timeAcumMovJefe;
    private int randMov;

    private boolean dispararJefe;

    //public Array<Boolean> arrayEstadoEnemigoSuelo;

    /**
     * Clase representa el primer nivel
     * @param juego Referencia al objeto que creo la pantalla
     */
    public PantallaJuegoNivelUno(Juego juego){super(juego);}

    @Override
    public void show() {
        crearGravedad();
        crearMundo(gravedad);
        crearEnemigos();
        //creararrayEstadoEnemigoSuelo();
        super.show();
        cargaMapa("MapaJuego.tmx");
        cargarTexturaBala("Proyectiles/bala1.png");
        Gdx.input.setInputProcessor(HUD);
        definirParedes();
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
        jefe = new Jefe(new Texture("Enemigos/JefeUnoJetPackFinal.png"), 6100, 72, 1f, 30f, 400, mundo); //Checar JEFE
        iniciarVariablesJefe();
    }

    private void iniciarVariablesJefe() {
        iniciarBatallaJefe = false;
        batallaJefeActiva = false;
        balasJefe = new Array<>();
        timeAcumDisparoJefe = 0;
        timeAcumMovJefe = 0;
        randMov = 0;
    }

    /*
    private void creararrayEstadoEnemigoSuelo() {
        arrayEstadoEnemigoSuelo = new Array<>(arrEnemigos.size);
        for(int i=0; i<arrayEstadoEnemigoSuelo.size;i++) {
            arrayEstadoEnemigoSuelo.add(false);
        }
    }
     */

    @Override
    protected void actualizarCamara() {
        if(protagonista.sprite.getX() > ANCHO/2 && protagonista.sprite.getX() < ANCHO_MAPA-ANCHO/2 && !batallaJefeActiva) { // 6400 = Ancho en
            camara.position.set(protagonista.sprite.getX(), camara.position.y, 0);
            camara.update();
        }
    }

    @Override
    protected void moverBala(float delta) {
        for(int indexBalas = 0; indexBalas < arrBalas.size; indexBalas++){
            if(arrBalas.get(indexBalas) == null) continue;
            Bala bala = arrBalas.get(indexBalas);
            bala.moverX(delta);
            if(bala.sprite.getX() > camara.position.x+ANCHO/2 || bala.sprite.getX() < camara.position.x-ANCHO/2){
                arrBalas.removeIndex(indexBalas);
                contadorBalas--;
            }
        }
    }

    @Override
    public void render(float delta){
        super.render(delta);

        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        for (Bala bala : balasJefe){
            bala.render(batch);
        }
        batch.end();

        checarInicioBatallaJefe();
        if (iniciarBatallaJefe) batallaJefe();
        if (batallaJefeActiva && estadoJuego == EstadoJuego.JUGANDO) {
            moverJefe(delta);
            dispararJefe(delta);
            moverBalasJefe(delta);
            checarColisiones(arrBalas, jefe);
            checarColisiones(balasJefe, protagonista);
            checarFinal();
        }
    }

    private void checarFinal() {
        if(jefe.getVida()<=0){
            juego.setScreen(new PantallaGanar(juego));
        }
    }

    private void checarColisiones(Array<Bala> array, Personaje personaje) {
        for(int indexBalas = 0; indexBalas < array.size; indexBalas++) {
            if (array.get(indexBalas) == null) continue;
            Bala bala = array.get(indexBalas);
            Rectangle personajeRect = personaje.sprite.getBoundingRectangle();
            if (personajeRect.overlaps(bala.sprite.getBoundingRectangle())) {
                personaje.setVida(bala.getDanio());
                array.removeIndex(indexBalas);
                contadorBalas--;
            }
        }
    }

    private void checarInicioBatallaJefe() {
        if (camara.position.x >= ANCHO_MAPA-ANCHO/2-30 && !batallaJefeActiva){
            camara.position.set(ANCHO_MAPA-ANCHO/2, camara.position.y, 0);
            camara.update();
            iniciarBatallaJefe = true;
        }
    }

    private void batallaJefe() {
        crearParedesBatallaJefe();
        iniciarBatallaJefe = false;
        batallaJefeActiva = true;
    }

    private void crearParedesBatallaJefe() {
        MapObjects objetos = mapa.getLayers().get("ParedesJefe").getObjects();
        for(MapObject objeto: objetos){
            Shape rectangulo = CargarMapa.getRectangle((RectangleMapObject)objeto);
            BodyDef bd = new BodyDef();
            bd.position.set(((RectangleMapObject) objeto).getRectangle().x, ((RectangleMapObject) objeto).getRectangle().y);
            bd.type  = BodyDef.BodyType.StaticBody;
            Body body = mundo.createBody(bd);
            body.createFixture(rectangulo,1);
            rectangulo.dispose();
        }
    }
    
    private void moverJefe(float delta){
        timeAcumMovJefe += delta;
        if (protagonista.sprite.getX() <= jefe.sprite.getX())
            jefe.setMovimiento(Personaje.Movimientos.IZQUIERDA);
        else jefe.setMovimiento(Personaje.Movimientos.DERECHA);

        if (jefe.movimiento == Personaje.Movimientos.IZQUIERDA) {
            jefe.sprite.setFlip(false, false);
        } else if (jefe.movimiento == Personaje.Movimientos.DERECHA) {
            jefe.sprite.setFlip(true, false);
        }
        if (timeAcumMovJefe > randMov) {
            if (jefe.movimiento == Personaje.Movimientos.IZQUIERDA) {
                jefe.sprite.setPosition(camara.position.x-ANCHO/2+5, 70);
            } else if (jefe.movimiento == Personaje.Movimientos.DERECHA) {
                jefe.sprite.setPosition(camara.position.x+ANCHO/2-5-jefe.sprite.getWidth(), 70);
            }
            timeAcumMovJefe = 0;
            randMov = MathUtils.random(3,10);
            dispararJefe = false;
        }else{
            dispararJefe = true;
        }
    }

    private void dispararJefe(float delta){
        timeAcumDisparoJefe += delta;
        if (timeAcumDisparoJefe > 2 && dispararJefe) {
            if (jefe.movimiento == Personaje.Movimientos.IZQUIERDA) {
                Bala bala = new Bala(texturaBalaEnemigos,
                        jefe.sprite.getX(),
                        jefe.sprite.getY() + (2 * jefe.sprite.getHeight() / 3) - texturaBalaEnemigos.getHeight() / 2f,
                        -100f, 0f, 100f);
                balasJefe.add(bala);
            } else if (jefe.movimiento == Personaje.Movimientos.DERECHA) {
                Bala bala = new Bala(texturaBalaEnemigos,
                        jefe.sprite.getX() + jefe.sprite.getWidth() - texturaBalaEnemigos.getWidth(),
                        jefe.sprite.getY() + (2 * jefe.sprite.getHeight() / 3) - texturaBalaEnemigos.getHeight() / 2f,
                        100f, 0f, 100f);
                balasJefe.add(bala);
            }
            timeAcumDisparoJefe = 0;
        }
    }

    private void moverBalasJefe(float delta) {
        for(int indexBalas = 0; indexBalas < balasJefe.size; indexBalas++){
            if(balasJefe.get(indexBalas) == null) continue;
            Bala bala = balasJefe.get(indexBalas);
            bala.moverX(delta);
            if(bala.sprite.getX() > camara.position.x+ANCHO/2 || bala.sprite.getX() < camara.position.x-ANCHO/2){
                balasJefe.removeIndex(indexBalas);
            }
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
