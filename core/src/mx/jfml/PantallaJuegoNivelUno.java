package mx.jfml;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

public class PantallaJuegoNivelUno extends Pantalla {
    private final Juego juego;

    //Personaje
    private Protagonista protagonista;
    private Texture texturaProtagonista;
    private Movimiento movimiento = Movimiento.QUIETO;

    //Pausa
    private EscenaPausa escenaPausa;
    private EstadoJuego estadoJuego = EstadoJuego.JUGANDO; //Jugando, PAusado

    //Bala
    private Bala bala1;
    private Array<Bala> arrBalas1;
    private Texture texturaBala1;


    //Enemigos
    private Array<Enemigo> arrEnemigos;
    private Enemigo enemigoUno;
    private Enemigo enemigoDos;
    private Texture texturaEnemigoUno;
    private Texture texturaEnemigoDos;
    private int maxPasosPositivos = 30;


    public PantallaJuegoNivelUno(Juego juego){this.juego = juego;}

    @Override
    public void show() {
        cargarTexturas();
        crearProtagonista();
        crearEnemigos();
        crearArrBalas1();

        Gdx.input.setInputProcessor(new ProcesadorEntrada());
    }

    private void crearProtagonista(){
        protagonista = new Protagonista(texturaProtagonista, 30f, 250f, 5f, 30f, 30f);
    }

    private void crearEnemigos(){
        arrEnemigos = new Array<>(20);
        enemigoUno = new Enemigo(texturaEnemigoUno,900f, 250f, 1f, 30f, 30f);
        enemigoDos = new Enemigo(texturaEnemigoDos, 700f, 250f, 1f, 30f, 30f);
        enemigoUno.direccion=Enemigo.MovimientoEnemigos.IZQUIERDA;
        enemigoDos.direccion=Enemigo.MovimientoEnemigos.IZQUIERDA;
        arrEnemigos.add(enemigoUno);
        arrEnemigos.add(enemigoDos);
    }
    private void crearArrBalas1(){
        arrBalas1 = new Array<>(5);
    }


    private void cargarTexturas() {
        texturaEnemigoUno = new Texture("Enemigos/enemigo.jpg");
        texturaEnemigoDos = new Texture("Enemigos/enemigo2.jpg");
        texturaProtagonista =new Texture("Principal/principal.jpg");
        texturaBala1 = new Texture("Proyectiles/bala1.png");
    }

    @Override
    public void render(float delta) {
        if(estadoJuego== EstadoJuego.JUGANDO){
            actualizar(delta);
        }

        //Dibujar
        borrarPantalla(0,0,0);
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        protagonista.render(batch);
        //render todos los enemigos
        for(Enemigo enemy: arrEnemigos){
            enemy.render(batch);
        }
        batch.end();
        if(estadoJuego == EstadoJuego.PAUSADO){
            //escenaPausa.draw();
        }
    }

    private void actualizar(float delta) {
        //Actualizaciones
        moverProtagonista();
        moverBala1(delta);
        moverEnemigos();
    }




    private void moverProtagonista() {
        switch(movimiento){
            case DERECHA:
                protagonista.moverX(protagonista.vx);
                break;
            case IZQUIERDA:
                protagonista.moverX(-protagonista.vx);
                break;
            default:
                break;
        }
    }
    private void moverEnemigos(){
        for(Enemigo enemy: arrEnemigos){
            if (protagonista.sprite.getX()-enemy.sprite.getX()<=0){
                enemy.direccion=Enemigo.MovimientoEnemigos.IZQUIERDA;
            }
            else{
                enemy.direccion=Enemigo.MovimientoEnemigos.DERECHA;
            }
        }

        for(Enemigo enemy: arrEnemigos){
            switch(enemy.direccion){
                case DERECHA:
                    enemy.moverX(enemy.vx);
                    break;
                case IZQUIERDA:
                    enemy.moverX(-enemy.vx);
                    break;
                default:
                    break;
            }
        }
    }


    private void moverBala1(float delta) {
        for(Bala bala: arrBalas1){
            if(bala != null){
                bala.moverX(delta);
                //Salio??
                if(bala.sprite.getY() > ALTO){
                    //Fuera de la pantalla
                    bala = null;
                }
            }
        }

    }


    //Pureba si la bala le pegó a un enemigo
    private void probarColisiones() {
        for(int i=0; i<arrBalas1.size;i++) {
            Bala bala = arrBalas1.get(i);
            if (bala != null) {
                Rectangle rectBala = bala.sprite.getBoundingRectangle();
                for(int j=0; j<arrEnemigos.size; j++){
                    Enemigo enemigo = arrEnemigos.get(j);
                    Rectangle rectEnemigo = enemigo.sprite.getBoundingRectangle();

                    if (rectEnemigo.overlaps(rectBala)) {
                        arrEnemigos.removeIndex(j);
                        arrBalas1.removeIndex(i);
                    }
                }
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
        texturaEnemigoDos.dispose();
        texturaEnemigoUno.dispose();
        texturaProtagonista.dispose();
    }

    private class ProcesadorEntrada implements InputProcessor{

        @Override
        public boolean keyDown(int keycode) {
            return false;
        }

        @Override
        public boolean keyUp(int keycode) {
            return false;
        }

        @Override
        public boolean keyTyped(char character) {
            return false;
        }

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            Vector3 v = new Vector3(screenX, screenY, 0);
            camara.unproject(v);
            //Disparo??
            if (v.y < ALTO / 2) {
                //Disparo!!
                for(int i=0; i<arrBalas1.size;i++) {
                    Bala bala = arrBalas1.get(i);
                    if (bala == null) {
                        float xBala = protagonista.sprite.getX() + protagonista.sprite.getWidth() / 2 - texturaBala1.getWidth() / 2;
                        float yBala = protagonista.sprite.getY() + protagonista.sprite.getHeight()/2;
                        bala = new Bala(texturaBala1, xBala, yBala,  7f,0f,30f);
                        arrBalas1.add(bala);
                    }
                }
            } else {
                if (v.x >= ANCHO / 2) {
                    //Derecha
                    movimiento = Movimiento.DERECHA;
                } else {
                    //Izquierda
                    movimiento = Movimiento.IZQUIERDA;
                }

            }
            return true;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            movimiento = Movimiento.QUIETO;
            return false;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            return false;
        }

        @Override
        public boolean mouseMoved(int screenX, int screenY) {
            return false;
        }

        @Override
        public boolean scrolled(int amount) {
            return false;
        }
    }


    //Clase Pausa( Ventana que se muestra cuando el usuario pausa el juego
    class EscenaPausa extends Stage{
        public EscenaPausa(Viewport vista, SpriteBatch batch){
            super(vista, batch);
            Pixmap pixmap = new Pixmap((int)(ANCHO*0.7f), (int)(ALTO*0.8f),
                    Pixmap.Format.RGBA8888);
            pixmap.setColor(.3f,0,0,0.5f);
            pixmap.fillCircle(300,300,300);
            Texture texturaCirculo = new Texture(pixmap);


            Image imgCirculo = new Image(texturaCirculo);
            imgCirculo.setPosition(ANCHO/2 - pixmap.getWidth()/2, ALTO/2-pixmap.getHeight()/2);
            this.addActor(imgCirculo);

        }
    }


    //Movimiento Personaje
    public enum Movimiento{
        DERECHA,
        IZQUIERDA,
        ARRIBA,
        QUIETO
    }
    //Movimiento enemigos


    private enum EstadoJuego {
        JUGANDO,
        PAUSADO,
        GANO,
        PERDIO
    }

}
