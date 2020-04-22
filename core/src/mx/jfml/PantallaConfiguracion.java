package mx.jfml;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

class PantallaConfiguracion extends Pantalla {

    private final Juego juego;

    private Stage escenaConfig;

    //Fondo
    private Texture texturaFondo;

    //Texto Escritos
    private Escritura txtAjustes;
    private Escritura txtVolMusica;
    private Escritura txtVolEfectos;

    //Musica Y Efectos
    protected Music musicaFondo;
    protected Audio efectoBoton;

    public PantallaConfiguracion(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        texturaFondo = new Texture("Fondos/fondoMenu.png");

        crearAjustes();
    }

    private void crearAjustes() {
        escenaConfig = new Stage(vista);

        cargarEscritura();

        //Boton Subir Volumen Musica
        Texture texturaBtnVolUpMusica = new Texture("BotonesConf/btnVolArriba.png");
        TextureRegionDrawable trdVolUpMusica = new TextureRegionDrawable(new TextureRegion(texturaBtnVolUpMusica));

        ImageButton btnVolUpMusica = new ImageButton(trdVolUpMusica);
        btnVolUpMusica.setPosition(ANCHO*.66f + btnVolUpMusica.getWidth()/2, ALTO*.66f - btnVolUpMusica.getHeight()/2);

        //Boton Bajar Volumen Musica
        Texture texturaBtnVolDownMusica = new Texture("BotonesConf/btnVolAbajo.png");
        TextureRegionDrawable trdVolDownMusica = new TextureRegionDrawable(new TextureRegion(texturaBtnVolDownMusica));

        ImageButton btnVolDownMusica = new ImageButton(trdVolDownMusica);
        btnVolDownMusica.setPosition(btnVolUpMusica.getX() + btnVolUpMusica.getWidth() + 30f, ALTO*.66f - btnVolDownMusica.getHeight()/2);

        //Boton Subir Volumen Efectos
        Texture texturaBtnVolUpEfectos = new Texture("BotonesConf/btnVolArribaEf.png");
        TextureRegionDrawable trdVolUpEfectos = new TextureRegionDrawable(new TextureRegion(texturaBtnVolUpEfectos));

        ImageButton btnVolUpEfectos = new ImageButton(trdVolUpEfectos);
        btnVolUpEfectos.setPosition(ANCHO*.66f, ALTO/2 - btnVolUpEfectos.getHeight()/2);

        //Boton Bajar Volumen Efectos
        Texture texturaBtnVolDownEfectos = new Texture("BotonesConf/btnVolAbajoEf.png");
        TextureRegionDrawable trdVolDownEfectos = new TextureRegionDrawable(new TextureRegion(texturaBtnVolDownEfectos));

        ImageButton btnVolDownEfectos = new ImageButton(trdVolDownEfectos);
        btnVolDownEfectos.setPosition(btnVolUpEfectos.getX() + btnVolUpEfectos.getWidth() + 30f, ALTO/2 - btnVolUpEfectos.getHeight()/2);

        //Boton Mutear
        Texture texturaBtnMute= new Texture("BotonesConf/btnMute.png");
        TextureRegionDrawable trdMute = new TextureRegionDrawable(new TextureRegion(texturaBtnMute));

        ImageButton btnMute = new ImageButton(trdMute);
        btnMute.setPosition(ANCHO/2 - btnMute.getWidth()/2, ALTO/3 - btnMute.getHeight());

        //Boton Regresar
        Texture texturaBtnRegresar = new Texture("BotonesConf/btnRegresar.png");
        TextureRegionDrawable trdRegresar = new TextureRegionDrawable(new TextureRegion(texturaBtnRegresar));

        ImageButton btnRegresar = new ImageButton(trdRegresar);
        btnRegresar.setPosition(64, 64);

        //Listener
        btnRegresar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new PantallaMenu(juego));
            }
        });


        escenaConfig.addActor(btnVolUpMusica);
        escenaConfig.addActor(btnVolDownMusica);
        escenaConfig.addActor(btnVolUpEfectos);
        escenaConfig.addActor(btnVolDownEfectos);
        escenaConfig.addActor(btnMute);
        escenaConfig.addActor(btnRegresar);

        Gdx.input.setInputProcessor(escenaConfig);
    }

    private void cargarEscritura() {
        txtAjustes = new Escritura(ANCHO/2, ALTO - ALTO*.1f);
        txtAjustes.setEnunciado("Ajustes");
        txtVolMusica = new Escritura(ANCHO/3, ALTO*.66f);
        txtVolMusica.setEnunciado("Volumen Musica");
        txtVolEfectos = new Escritura(ANCHO/3, ALTO/2);
        txtVolEfectos.setEnunciado("Volumen Efectos");
    }

    @Override
    public void render(float delta) {
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);

        batch.begin();

        batch.draw(texturaFondo,0,0);

        //Textos
        txtAjustes.render(batch);
        txtVolEfectos.render(batch);
        txtVolMusica.render(batch);


        batch.end();

        escenaConfig.draw();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
     texturaFondo.dispose();
    }
}
