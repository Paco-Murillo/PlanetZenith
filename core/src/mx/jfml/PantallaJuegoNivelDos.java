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

public class PantallaJuegoNivelDos extends Nivel {

    private static final int ANCHO_MAPA = 6400;
    private static final int ALTO_MAPA = 576;

    private int DYJefe = 2;
    private int DXJefe = 2;
    private float murcielagoTimer=0;
    private boolean batallaJefeActiva;
    private boolean iniciarBatallaJefe;
    private Array<Bala> balasJefe;
    private float timeAcumDisparoJefe;

    /**
     * Clase abstracta que permite representar los fundamentos de cada nivel
     *
     * @param juego Referencia al objeto que creo la pantalla
     */
    public PantallaJuegoNivelDos(Juego juego) {
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
        crearBotones(new Texture("BotonesHUD/botonDispararNivelDos.png"),new Texture("BotonesHUD/botonSaltarNivelDos.png"));
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
        System.out.println(murcielagoTimer);
        batch.setProjectionMatrix(camara.combined);

        dispararMurcielago(delta);
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

    private void moverJefe(float delta) {
        if (protagonista.sprite.getX() <= jefe.sprite.getX())
            jefe.setMovimiento(Personaje.Movimientos.IZQUIERDA);
        else jefe.setMovimiento(Personaje.Movimientos.DERECHA);
        if (jefe.movimiento == Personaje.Movimientos.IZQUIERDA) {
            jefe.sprite.setFlip(true, false);
        } else if (jefe.movimiento == Personaje.Movimientos.DERECHA) {
            jefe.sprite.setFlip(false, false);
        }
        //Prueba Limites Derecha-Izquierda
        if (jefe.sprite.getX()>=ANCHO_MAPA-jefe.sprite.getWidth() || jefe.sprite.getX()<=5248){
            DXJefe = -DXJefe;
        }
        //Prueba limites Arriba-Abajo
        if (jefe.sprite.getY()>=ALTO_MAPA-jefe.sprite.getHeight() || jefe.sprite.getY()<=50 ){
            DYJefe= -DYJefe;
        }
        jefe.sprite.setPosition(jefe.sprite.getX()+DXJefe,jefe.sprite.getY()+DYJefe);
    }

    private void dispararJefe(float delta){
        timeAcumDisparoJefe += delta;
        if (timeAcumDisparoJefe > 1.8) {
            if (jefe.movimiento == Personaje.Movimientos.IZQUIERDA) {
                Bala bala = new Bala(new Texture("Proyectiles/DisparoEnemigoDos.png"), jefe.sprite.getX(), jefe.sprite.getY() + (2 * jefe.sprite.getHeight() / 3) - texturaBalaEnemigos.getHeight() / 2f,
                        -300f, 0f, 50f);
                balasJefe.add(bala);
            } else if (jefe.movimiento == Personaje.Movimientos.DERECHA) {
                Bala bala = new Bala(new Texture("Proyectiles/DisparoEnemigoDos.png"), jefe.sprite.getX() + jefe.sprite.getWidth() - texturaBalaEnemigos.getWidth(),
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
        crearParedesBatallaJefe(mapa,mundo);
        iniciarBatallaJefe = false;
        batallaJefeActiva = true;
    }



    private void dispararMurcielago(float delta) {
        for (Enemigo enemy : arrEnemigos) {
            if (enemy.tipoEnemigo.equals(Enemigo.TipoEnemigo.JETPACK) && murcielagoTimer>3
                    && enemy.sprite.getX()-protagonista.sprite.getX()<500 ) {
                Bala bala;
                if(enemy.movimiento.equals(Enemigo.Movimientos.DERECHA)) {
                    bala = new Bala(new Texture("Proyectiles/murcielago.png"), enemy.sprite.getX(),
                            enemy.sprite.getY() + (2 * enemy.sprite.getHeight() / 3), 300f, 0f, 35f);
                }
                else {
                     bala = new Bala(new Texture("Proyectiles/murcielago.png"), enemy.sprite.getX(),
                        enemy.sprite.getY() + (2 * enemy.sprite.getHeight() / 3), -300f, 0f, 35f);
                }
                arrBalasEnemigos.add(bala);
                murcielagoTimer=0;
            }
        }
        murcielagoTimer=murcielagoTimer+delta;
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
