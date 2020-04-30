package mx.jfml;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

public class PantallaJuegoNivelUno extends Nivel {

    private static final int ANCHO_MAPA = 6400;
    private static final int ALTO_MAPA = 736;


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
        Enemigo enemigo = new Enemigo(new Texture("Enemigos/Enemigo.png"), 792, 72, 1f, 30f, 30f, mundo);
        //enemigo.direccion= Personaje.Movimientos.IZQUIERDA;
        arrEnemigos.add(enemigo);
        enemigo = new Enemigo(new Texture("Enemigos/Enemigo.png"), 936, 396, 1f, 30f, 30f, mundo);
        arrEnemigos.add(enemigo);
        enemigo = new Enemigo(new Texture("Enemigos/Enemigo.png"), 1728, 180, 1f, 30f, 30f, mundo);
        arrEnemigos.add(enemigo);
        enemigo = new Enemigo(new Texture("Enemigos/Enemigo.png"), 2232, 180, 1f, 30f, 30f, mundo);
        arrEnemigos.add(enemigo);
        enemigo = new Enemigo(new Texture("Enemigos/Enemigo.png"), 2952, 72, 1f, 30f, 30f, mundo);
        arrEnemigos.add(enemigo);
        enemigo = new Enemigo(new Texture("Enemigos/Enemigo.png"), 3348, 288, 1f, 30f, 30f, mundo);
        arrEnemigos.add(enemigo);
        enemigo = new Enemigo(new Texture("Enemigos/Enemigo.png"), 3852, 216, 1f, 30f, 30f, mundo);
        arrEnemigos.add(enemigo);
        enemigo = new Enemigo(new Texture("Enemigos/Enemigo.png"), 4500, 432, 1f, 30f, 30f, mundo);
        arrEnemigos.add(enemigo);
        enemigo = new Enemigo(new Texture("Enemigos/Enemigo.png"), 4896, 72, 1f, 30f, 30f, mundo);
        arrEnemigos.add(enemigo);
        enemigo = new Enemigo(new Texture("Enemigos/Enemigo.png"), 5400, 72, 1f, 30f, 30f, mundo);
        arrEnemigos.add(enemigo);
        enemigo = new Enemigo(new Texture("Enemigos/Enemigo.png"), 5724, 252, 1f, 30f, 30f, mundo);
        arrEnemigos.add(enemigo);
        enemigo = new Enemigo(new Texture("Enemigos/Enemigo.png"), 6120, 180, 1f, 30f, 30f, mundo);
        arrEnemigos.add(enemigo);
        enemigo = new Jefe(new Texture("Enemigos/Enemigo.png"), 6876, 72, 1f, 30f, 30f, mundo); //Checar JEFE
        arrEnemigos.add(enemigo);
    }

    @Override
    protected void actualizarCamara() {
        if(protagonista.sprite.getX() > ANCHO/2 && protagonista.sprite.getX() < ANCHO_MAPA-ANCHO/2) { // 6400 = Ancho en
            camara.position.set(protagonista.sprite.getX(), camara.position.y, 0);
            camara.update();
        }
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
