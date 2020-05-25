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

public class PantallaJuegoNivelTres extends Nivel {

    private static final int ANCHO_MAPA = 6400;
    private static final int ALTO_MAPA = 576;

    private boolean batallaJefeActiva;
    private boolean iniciarBatallaJefe;
    private Array<Bala> balasJefe;
    private float timeAcumDisparoJefe;

    /**
     * Clase abstracta que permite representar los fundamentos de cada nivel
     *
     * @param juego Referencia al objeto que creo la pantalla
     */
    public PantallaJuegoNivelTres(Juego juego) {
        super(juego);
    }

    @Override
    public void show() {
        crearGravedad();
        crearMundo(gravedad);
        crearEnemigos();
        super.show();
        cargaMapa("NivelDos.tmx");
        cargarTexturaBala("Proyectiles/bala1.png");
        Gdx.input.setInputProcessor(HUD);
        definirParedes();
    }

    private void crearEnemigos(){
        arrEnemigos = new Array<>(10);
        Texture enemigoTexture = new Texture("Enemigos/EnemigoGeneralAzul.png");
        Texture subJefeTexture = new Texture("Enemigos/MiniJedeNivelDos.png");

        Enemigo enemigo = new Enemigo(enemigoTexture, 640, 256, 1f, 30f, 30f, mundo, Enemigo.TipoEnemigo.CAMINANTE); //Enmigo 1
        //enemigo.direccion= Personaje.Movimientos.IZQUIERDA;
        arrEnemigos.add(enemigo);
        enemigo = new Enemigo(enemigoTexture, 1504, 192, 1f, 30f, 30f, mundo, Enemigo.TipoEnemigo.CAMINANTE); //Enemigo 2
        arrEnemigos.add(enemigo);
        enemigo = new Enemigo(enemigoTexture, 1920, 32, 1f, 30f, 30f, mundo, Enemigo.TipoEnemigo.CAMINANTE); //Enemigo 3
        arrEnemigos.add(enemigo);
        enemigo = new Enemigo(enemigoTexture, 2040, 32, 1f, 30f, 30f, mundo, Enemigo.TipoEnemigo.CAMINANTE); //Enemigo 4
        arrEnemigos.add(enemigo);
        enemigo = new Enemigo(enemigoTexture, 2680, 32, 1f, 30f, 30f, mundo, Enemigo.TipoEnemigo.CAMINANTE); //Enemigo 5
        arrEnemigos.add(enemigo);
        enemigo = new Enemigo(enemigoTexture, 3040, 32, 1f, 30f, 30f, mundo, Enemigo.TipoEnemigo.CAMINANTE); //Enemigo 6
        arrEnemigos.add(enemigo);
        enemigo = new Enemigo(enemigoTexture, 3520, 224, 1f, 30f, 30f, mundo, Enemigo.TipoEnemigo.CAMINANTE); //Enemigo 7
        arrEnemigos.add(enemigo);
        enemigo = new Enemigo(enemigoTexture, 4256, 416, 1f, 30f, 30f, mundo, Enemigo.TipoEnemigo.CAMINANTE); //Enemigo 8
        arrEnemigos.add(enemigo);
        enemigo = new Enemigo(enemigoTexture, 4224, 224, 1f, 30f, 30f, mundo, Enemigo.TipoEnemigo.CAMINANTE); //Enemigo 9
        arrEnemigos.add(enemigo);

        //Enemigo 9
        enemigo = new Enemigo(subJefeTexture, 5100, 62, 1f, 30f, 200, mundo, Enemigo.TipoEnemigo.JETPACK); //Enemigo 10
        arrEnemigos.add(enemigo);

        //Cambiar Sprite Jefe
        jefe = new Jefe(new Texture("Enemigos/JefeFinalDos.png"), 6240, 128, 1f, 30f, 400, mundo); //Checar JEFE
        iniciarVariablesJefe();
    }

    private void iniciarVariablesJefe() {
        iniciarBatallaJefe = false;
        batallaJefeActiva = false;
        balasJefe = new Array<>();
        timeAcumDisparoJefe = 0;
    }

    @Override
    protected void actualizarCamara() {
        if(protagonista.sprite.getX() > ANCHO/2 && protagonista.sprite.getX() < ANCHO_MAPA-ANCHO/2) { // 6400 = Ancho en
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
            checarFinal(jefe);
        }
    }

    private void moverJefe(float delta) {

    }

    private void dispararJefe(float delta){
        timeAcumDisparoJefe += delta;
        if (timeAcumDisparoJefe > 2) {
            if (jefe.movimiento == Personaje.Movimientos.IZQUIERDA) {
                Bala bala = new Bala(texturaBalaEnemigos, jefe.sprite.getX(), jefe.sprite.getY() + (2 * jefe.sprite.getHeight() / 3) - texturaBalaEnemigos.getHeight() / 2f,
                        -300f, 0f, 50f);
                balasJefe.add(bala);
            } else if (jefe.movimiento == Personaje.Movimientos.DERECHA) {
                Bala bala = new Bala(texturaBalaEnemigos, jefe.sprite.getX() + jefe.sprite.getWidth() - texturaBalaEnemigos.getWidth(),
                        jefe.sprite.getY() + (2 * jefe.sprite.getHeight() / 3) - texturaBalaEnemigos.getHeight() / 2f, 300f, 0f, 100f);
                balasJefe.add(bala);
            }
            timeAcumDisparoJefe = 0;
        }
    }
    private void moverBalasJefe(float delta) {
        for (int indexBalas = 0; indexBalas < balasJefe.size; indexBalas++) {
            if (balasJefe.get(indexBalas) == null) continue;
            Bala bala = balasJefe.get(indexBalas);
            bala.moverX(delta);
            if (bala.sprite.getX() > camara.position.x + ANCHO / 2 || bala.sprite.getX() < camara.position.x - ANCHO / 2) {
                balasJefe.removeIndex(indexBalas);
            }
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


    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        texturaBala.dispose();
        texturaBalaEnemigos.dispose();
    }
}
