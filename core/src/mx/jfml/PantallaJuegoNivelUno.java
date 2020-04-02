package mx.jfml;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PantallaJuegoNivelUno extends Pantalla {
    private Juego juego;


    //Botones (No hacen nada, pero estan ahi para orientar)
    private Texture texturabotonIzquierdo;
    private Texture texturabotonDerecho;
    private Texture texturabotonDisparar;
    private Texture texturabotonPausa;
    private Boton botonIzquierdo;
    private Boton botonDerecho;
    private Boton botondisparar;
    private Boton botonpausa;



    //Necesario para dibujar en la pantalla
    //protected SpriteBatch batch;

    //Personaje
    //Pulir esto (Personaje es un abstract, no puede ser inicializado, crear clase protagonista)
    //private Personaje personaje;
    private Protagonista protagonista;
    private Texture texturaProtagonista;
    private Movimiento movimiento = Movimiento.QUIETO;

    //Pausa
    //private EscenaPausa escenaPausa;
    private EstadoJuego estadoJuego = EstadoJuego.JUGANDO; //Jugando, PAusado

    //Bala
    private Bala bala;
    private Texture TexturaBala;

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
        crearBotones();


        Gdx.input.setInputProcessor(new ProcesadorEntrada());
    }

    private void crearProtagonista(){
        //Pulir esto (Personaje es un abstract, no puede ser inicializado, crear clase protagonista)
        protagonista = new Protagonista(texturaProtagonista, 30f, 250f, 30f, 30f, 30f);
    }

    private void crearEnemigos(){
        enemigoUno = new Enemigo(texturaEnemigoUno,900f, 250f, 30f, 30f, 30f);
        enemigoDos = new Enemigo(texturaEnemigoDos, 700f, 250f, 30f, 30f, 30f);
    }
    private void crearBotones(){
        botonDerecho = new Boton(texturabotonDerecho, 300, 30 );
        botonIzquierdo = new Boton(texturabotonIzquierdo, 30, 30);
        botondisparar = new Boton(texturabotonDisparar, 1000, 30);
        botonpausa = new Boton(texturabotonPausa, 30, 550);
    }

    private void cargarTexturas() {
        texturaEnemigoUno = new Texture("enemigo.jpg");
        texturaEnemigoDos = new Texture("enemigo2.jpg");
        texturaProtagonista =new Texture("principal.jpg");
        texturabotonIzquierdo = new Texture("botonIzquierdo.png");
        texturabotonDerecho = new Texture("botonDerecho.png");
        texturabotonDisparar = new Texture("botonDisparar.png");
        texturabotonPausa = new Texture("pausa.png");
    }

    @Override
    public void render(float delta) {
        borrarPantalla(0,0,0);
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        protagonista.render(batch);
        enemigoUno.render(batch);
        enemigoDos.render(batch);
        botonIzquierdo.render(batch);
        botonDerecho.render(batch);
        botonpausa.render(batch);
        botondisparar.render(batch);
        //Pulir esto (Personaje es un abstract, no puede ser inicializado, crear clase protagonista)
        //personaje.render(batch);
        //enemigoUno.render(batch);
        //enemigoDos.render(batch);
        batch.end();
        if(estadoJuego == EstadoJuego.PAUSADO){
            //escenaPausa.draw();
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
    //class EscenaPausa extends Stage{
     //   public EscenaPausa(Viewport vista, SpriteBatch batch){
      //      super(vista, batch);

            //Texture texturaPausa = new Texture("pausa.png");


            //Image pausa = new Image(texturaPausa);
            //pausa.setPosition(50,800);
            //this.addActor(pausa);

        //}
    //}


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
