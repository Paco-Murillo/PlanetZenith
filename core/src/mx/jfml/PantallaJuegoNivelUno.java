package mx.jfml;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

public class PantallaJuegoNivelUno extends Nivel {

    private static final int ANCHO_MAPA = 6400;
    private static final int ALTO_MAPA = 736;

    private Jefe jefe;


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
        jefe = new Jefe(new Texture("Enemigos/JefeUnoJetPack.png"), 6876, 72, 1f, 30f, 30f, mundo); //Checar JEFE
    }

    @Override
    protected void actualizarCamara() {
        if(protagonista.sprite.getX() > ANCHO/2 && protagonista.sprite.getX() < ANCHO_MAPA-ANCHO/2) { // 6400 = Ancho en
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
