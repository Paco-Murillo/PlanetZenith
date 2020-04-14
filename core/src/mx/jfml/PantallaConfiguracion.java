package mx.jfml;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

class PantallaConfiguracion extends Pantalla {

    private final Juego juego;

    //Fondo
    private Texture texturaFondo;

    //Texto Escritos
    private Escritura txtVolumen;


    //Boton Volumen Arriba

    //Boton Volumen Abajo

    //Boton Mutear

    //Boton Regresar


    public PantallaConfiguracion(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        cargarEscritura();
    }

    private void cargarEscritura() {
        txtVolumen = new Escritura(ANCHO/3, ALTO/3);
        txtVolumen.setEnunciado("Volumen");
    }

    @Override
    public void render(float delta) {

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
