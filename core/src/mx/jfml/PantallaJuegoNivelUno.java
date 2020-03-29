package mx.jfml;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PantallaJuegoNivelUno extends Pantalla {
    private Juego juego;

    //Necesario para dibujar en la pantalla
    protected SpriteBatch batch;

    //Personaje
    private Personaje personaje;
    private Texture texturaPersonaje;
    private Movimiento movimiento = Movimiento.QUIETO;

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

        Gdx.input.setInputProcessor(new ProcesadorEntrada());
    }

    private void cargarTexturas() {
        texturaEnemigoUno = new Texture("enemigo.jpg");
        texturaEnemigoDos = new Texture("enemigo2.jpg");
        texturaPersonaje =new Texture("principal.jpg");
    }

    @Override
    public void render(float delta) {
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        personaje.render(batch);
        enemigoUno.render(batch);
        enemigoDos.render(batch);
        batch.end();
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
        IZQUIERDA
    }
}
