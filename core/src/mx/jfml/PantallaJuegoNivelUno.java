package mx.jfml;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

public class PantallaJuegoNivelUno extends Nivel {


    public PantallaJuegoNivelUno(Juego juego){super(juego);}

    @Override
    public void show() {
        super.show();
        cargaMapa("MapaJuego.tmx");
        crearEnemigos();
        cargarTexturaBala("Proyectiles/bala1.png");
        Gdx.input.setInputProcessor(HUD);
        definirParedes();
    }

    private void crearEnemigos(){
        arrEnemigos = new Array<>(10);
        Enemigo enemigoUno = new Enemigo(new Texture("Enemigos/Enemigo.png"), 900f, 250f, 1f, 30f, 30f, mundo);
        enemigoUno.direccion= Personaje.Movimientos.IZQUIERDA;
        arrEnemigos.add(enemigoUno);
    }

    @Override
    public void render(float delta){
        super.render(delta);
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
