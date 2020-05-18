package mx.jfml;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

public class PantallaJuegoNivelDos extends Nivel {

    private static final int ANCHO_MAPA = 6400;
    private static final int ALTO_MAPA = 576;

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
        //creararrayEstadoEnemigoSuelo();
        super.show();
        cargaMapa("NivelDos.tmx");
        cargarTexturaBala("Proyectiles/bala1.png");
        Gdx.input.setInputProcessor(HUD);
        definirParedes();
    }

    private void crearEnemigos(){
        arrEnemigos = new Array<>(10);
        Texture enemigoTexture = new Texture("Enemigos/EnemigoGeneralAzul.png");
        Enemigo enemigo = new Enemigo(enemigoTexture, 640, 256, 1f, 30f, 30f, mundo); //Enmigo 1
        //enemigo.direccion= Personaje.Movimientos.IZQUIERDA;
        arrEnemigos.add(enemigo);
        enemigo = new Enemigo(enemigoTexture, 1504, 192, 1f, 30f, 30f, mundo); //Enemigo 2
        arrEnemigos.add(enemigo);
        enemigo = new Enemigo(enemigoTexture, 1920, 32, 1f, 30f, 30f, mundo); //Enemigo 3
        arrEnemigos.add(enemigo);
        enemigo = new Enemigo(enemigoTexture, 2040, 32, 1f, 30f, 30f, mundo); //Enemigo 4
        arrEnemigos.add(enemigo);
        enemigo = new Enemigo(enemigoTexture, 2680, 32, 1f, 30f, 30f, mundo); //Enemigo 5
        arrEnemigos.add(enemigo);
        enemigo = new Enemigo(enemigoTexture, 3040, 32, 1f, 30f, 30f, mundo); //Enemigo 6
        arrEnemigos.add(enemigo);
        enemigo = new Enemigo(enemigoTexture, 3520, 224, 1f, 30f, 30f, mundo); //Enemigo 7
        arrEnemigos.add(enemigo);
        enemigo = new Enemigo(enemigoTexture, 4256, 416, 1f, 30f, 30f, mundo); //Enemigo 8
        arrEnemigos.add(enemigo);
        enemigo = new Enemigo(enemigoTexture, 4224, 224, 1f, 30f, 30f, mundo); //Enemigo 9
        arrEnemigos.add(enemigo);
        //Enemigo 9
        enemigo = new Enemigo(enemigoTexture, 4928, 32, 1f, 30f, 30f, mundo); //Enemigo 10
        arrEnemigos.add(enemigo);

        //Cambiar Sprite Jefe
        jefe = new Jefe(new Texture("Enemigos/JefeUnoJetPack.png"), 6240, 128, 1f, 30f, 400, mundo); //Checar JEFE
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
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
