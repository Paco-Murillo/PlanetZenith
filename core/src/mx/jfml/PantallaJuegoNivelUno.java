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

public class PantallaJuegoNivelUno extends Pantalla {
    private Juego juego;

    //Personaje
    private Protagonista protagonista;
    private Texture texturaProtagonista;
    private Movimiento movimiento = Movimiento.QUIETO;

    //Pausa
    private EscenaPausa escenaPausa;
    private EstadoJuego estadoJuego = EstadoJuego.JUGANDO; //Jugando, PAusado

    //Bala
    private Bala bala1;
    private Texture TexturaBala1;

    //Enemigos
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
        //crearBotones();


        Gdx.input.setInputProcessor(new ProcesadorEntrada());
    }

    private void crearProtagonista(){
        protagonista = new Protagonista(texturaProtagonista, 30f, 250f, 30f, 30f, 30f);
    }

    private void crearEnemigos(){
        enemigoUno = new Enemigo(texturaEnemigoUno,900f, 250f, 30f, 30f, 30f);
        enemigoDos = new Enemigo(texturaEnemigoDos, 700f, 250f, 30f, 30f, 30f);
    }


    private void cargarTexturas() {
        texturaEnemigoUno = new Texture("enemigo.jpg");
        texturaEnemigoDos = new Texture("enemigo2.jpg");
        texturaProtagonista =new Texture("principal.jpg");
    }

    @Override
    public void render(float delta) {

        if(estadoJuego== EstadoJuego.JUGANDO){
            actualizar(delta);

        }
        borrarPantalla(0,0,0);
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        protagonista.render(batch);
        enemigoUno.render(batch);
        enemigoDos.render(batch);
        batch.end();
        if(estadoJuego == EstadoJuego.PAUSADO){
            //escenaPausa.draw();
        }
    }

    private void actualizar(float delta) {
        //Actualizaciones
        moverProtagonista();
        moverBala1(delta);
    }




    private void moverProtagonista() {
        switch(movimiento){
            case DERECHA:
                protagonista.moverX(10);
                break;
            case IZQUIERDA:
                protagonista.moverX(-10);
                break;
            default:
                break;
        }
    }


    private void moverBala1(float delta) {
        if(bala1 != null){
            bala1.moverX(delta);
            //Salio??
            //Esto debe ser mejorado cuando nos enseñen a desplazarnos por pantallas
            if(bala1.sprite.getY() > ALTO || bala1.sprite.getX() >ANCHO){
                //Fuera de la pantalla
                bala1 = null;
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
            return false;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
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
    public enum MovimientoEnemigos{
        DERECHA,
        IZQUIERDA,
        QUIETO
    }

    private enum EstadoJuego {
        JUGANDO,
        PAUSADO,
        GANO,
        PERDIO
    }

}
