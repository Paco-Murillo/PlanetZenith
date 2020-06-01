package mx.jfml;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class PantallaConfiguracion extends Pantalla {

    private final Juego juego;

    private Stage escenaConfig;

    //Fondo
    private Texture texturaFondo;

    //Botones
    private Texture texturaBtnVolUpMusica;
    private Texture texturaBtnVolDownMusica;
    private Texture texturaBtnRegresar;


    //Texto Escritos
    private Escritura txtAjustes;
    private Escritura txtVolMusica;

    //Asset Manager
    private final AssetManager assetManager;

    //Manager de audio
    private AudioManejador audioManager;

    //Audio
    private Music musicaFondo;
    private Sound efectoBoton;


    public PantallaConfiguracion(Juego juego) {
        this.juego = juego;
        assetManager = juego.getAssetManager();
        audioManager = juego.getAudioManejador();
    }

    @Override
    public void show() {
        texturaFondo = new Texture("Fondos/fondoPantalla.png");

        crearAjustes();
    }

    private void crearAjustes() {
        cargarEscritura();
        cargarAssets();

        escenaConfig = new Stage(vista);

        Gdx.input.setInputProcessor(escenaConfig);
        Gdx.input.setCatchKey(Input.Keys.BACK, true);

        //Botones
        //Boton Bajar Volumen Musica
        TextureRegionDrawable trdVolDownMusica = new TextureRegionDrawable(new TextureRegion(texturaBtnVolDownMusica));
        ImageButton btnVolDownMusica = new ImageButton(trdVolDownMusica);
        btnVolDownMusica.setPosition(4*ANCHO/9 + btnVolDownMusica.getWidth()/2 + 30f, ALTO/2 - btnVolDownMusica.getHeight()/2);
        escenaConfig.addActor(btnVolDownMusica);

        //Boton Subir Volumen Musica
        TextureRegionDrawable trdVolUpMusica = new TextureRegionDrawable(new TextureRegion(texturaBtnVolUpMusica));
        ImageButton btnVolUpMusica = new ImageButton(trdVolUpMusica);
        btnVolUpMusica.setPosition(btnVolDownMusica.getX() + btnVolDownMusica.getWidth() + 50f, ALTO/2 - btnVolUpMusica.getHeight()/2);
        escenaConfig.addActor(btnVolUpMusica);


        //Boton Regresar
        TextureRegionDrawable trdRegresar = new TextureRegionDrawable(new TextureRegion(texturaBtnRegresar));
        ImageButton btnRegresar = new ImageButton(trdRegresar);
        btnRegresar.setPosition(64, 64);
        escenaConfig.addActor(btnRegresar);

        //Listener
        btnVolUpMusica.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                efectoBoton.play(audioManager.getVolEfectos());
                if(audioManager.getVolMusica() < 1f){
                    audioManager.setVolMusica(audioManager.getVolMusica() + .10f);
                    musicaFondo.setVolume(audioManager.getVolMusica());
                }
                if(audioManager.getVolEfectos() < 1f){
                    audioManager.setVolEfectos(audioManager.getVolEfectos() + .10f);
                }
            }
        });

        btnVolDownMusica.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                efectoBoton.play(audioManager.getVolEfectos());
                if(audioManager.getVolMusica() > 0){
                    audioManager.setVolMusica(audioManager.getVolMusica() - .10f);
                    musicaFondo.setVolume(audioManager.getVolMusica());

                }
                if(audioManager.getVolEfectos() > 0){
                    audioManager.setVolEfectos(audioManager.getVolEfectos() - .10f);
                }

            }
        });


        btnRegresar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                musicaFondo.stop();
                audioManager.setTocando(!audioManager.getTocando());
                musicaFondo.dispose();
                efectoBoton.dispose();
                juego.setScreen(new PantallaMenu(juego));
            }
        });


    }

    private void cargarAssets() {
        //Audio y Musica
        assetManager.load("Audio/Musica/principal.wav", Music.class);
        assetManager.load("Audio/Efectos/sonidoboton.mp3", Sound.class);

        //Textura de botones
        assetManager.load("BotonesConf/btnVolArriba.png", Texture.class);
        assetManager.load("BotonesConf/btnVolAbajo.png", Texture.class);
        assetManager.load("BotonesConf/btnRegresar.png", Texture.class);

        //Se bloquea hasta cargar los recursos
        assetManager.finishLoading();

        texturaBtnVolUpMusica = assetManager.get("BotonesConf/btnVolArriba.png");
        texturaBtnVolDownMusica = assetManager.get("BotonesConf/btnVolAbajo.png");
        texturaBtnRegresar = assetManager.get("BotonesConf/btnRegresar.png");

        musicaFondo = assetManager.get("Audio/Musica/principal.wav");
        efectoBoton = assetManager.get("Audio/Efectos/sonidoboton.mp3");
    }

    private void cargarEscritura() {

        txtAjustes = new Escritura(ANCHO/2, ALTO - ALTO*.1f);
        txtAjustes.setEnunciado("Ajustes");
        txtVolMusica = new Escritura(ANCHO/3, ALTO/2);
        txtVolMusica.setEnunciado("Volumen: ");
    }

    @Override
    public void render(float delta) {
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);

        batch.begin();

        batch.draw(texturaFondo,0,0);

        //Textos
        txtAjustes.render(batch);
        txtVolMusica.render(batch);


        batch.end();

        escenaConfig.draw();

        //Tecla de Back
        if(Gdx.input.isKeyPressed(Input.Keys.BACK)){
            musicaFondo.stop();
            audioManager.setTocando(!audioManager.getTocando());
            musicaFondo.dispose();
            efectoBoton.dispose();
            juego.setScreen(new PantallaMenu(juego));
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
        //Liberar la memoria usada
        texturaFondo.dispose();
        texturaBtnRegresar.dispose();
        texturaBtnVolUpMusica.dispose();
        musicaFondo.dispose();
        efectoBoton.dispose();
        escenaConfig.dispose();

        //Ahora el asset manager libera los recursos
        assetManager.unload("BotonesConf/btnVolArriba.png");
        assetManager.unload("BotonesConf/btnVolAbajo.png");
        assetManager.unload("BotonesConf/btnRegresar.png");
        assetManager.unload("Audio/Musica/principal.wav");
        assetManager.unload("Audio/Efectos/sonidoboton.mp3");

    }
}
