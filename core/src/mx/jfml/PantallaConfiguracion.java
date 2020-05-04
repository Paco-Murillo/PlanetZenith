package mx.jfml;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
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

class PantallaConfiguracion extends Pantalla {

    private final Juego juego;

    private Stage escenaConfig;

    //Fondo
    private Texture texturaFondo;

    //Botones
    private Texture texturaBtnVolUpMusica;
    private Texture texturaBtnVolDownMusica;
    private Texture texturaBtnVolUpEfectos;
    private Texture texturaBtnVolDownEfectos;
    private Texture texturaBtnMute;
    private Texture texturaBtnRegresar;


    //Texto Escritos
    private Escritura txtAjustes;
    private Escritura txtVolMusica;
    private Escritura txtVolEfectos;

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


        //Botones
        //Boton Bajar Volumen Musica
        TextureRegionDrawable trdVolDownMusica = new TextureRegionDrawable(new TextureRegion(texturaBtnVolDownMusica));
        ImageButton btnVolDownMusica = new ImageButton(trdVolDownMusica);
        btnVolDownMusica.setPosition(ANCHO*.66f + btnVolDownMusica.getWidth()/2 + 30f, ALTO*.66f - btnVolDownMusica.getHeight()/2);
        escenaConfig.addActor(btnVolDownMusica);

        //Boton Subir Volumen Musica
        TextureRegionDrawable trdVolUpMusica = new TextureRegionDrawable(new TextureRegion(texturaBtnVolUpMusica));
        ImageButton btnVolUpMusica = new ImageButton(trdVolUpMusica);
        btnVolUpMusica.setPosition(btnVolDownMusica.getX() + btnVolDownMusica.getWidth(), ALTO*.66f - btnVolUpMusica.getHeight()/2);
        escenaConfig.addActor(btnVolUpMusica);

        //Boton Bajar Volumen Efectos
        TextureRegionDrawable trdVolDownEfectos = new TextureRegionDrawable(new TextureRegion(texturaBtnVolDownEfectos));
        ImageButton btnVolDownEfectos = new ImageButton(trdVolDownEfectos);
        btnVolDownEfectos.setPosition(ANCHO*.66f, ALTO/2 - btnVolDownEfectos.getHeight()/2);
        escenaConfig.addActor(btnVolDownEfectos);

        //Boton Subir Volumen Efectos
        TextureRegionDrawable trdVolUpEfectos = new TextureRegionDrawable(new TextureRegion(texturaBtnVolUpEfectos));
        ImageButton btnVolUpEfectos = new ImageButton(trdVolUpEfectos);
        btnVolUpEfectos.setPosition(btnVolDownEfectos.getX() + btnVolDownEfectos.getWidth() + 30f, ALTO/2 - btnVolDownEfectos.getHeight()/2);
        escenaConfig.addActor(btnVolUpEfectos);

        //Boton Mutear
        TextureRegionDrawable trdMute = new TextureRegionDrawable(new TextureRegion(texturaBtnMute));
        ImageButton btnMute = new ImageButton(trdMute);
        btnMute.setPosition(ANCHO/2 - btnMute.getWidth()/2, ALTO/3 - btnMute.getHeight());
        escenaConfig.addActor(btnMute);

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
                    audioManager.setVolMusica(audioManager.getVolMusica() + .25f);
                    musicaFondo.setVolume(audioManager.getVolMusica());
                }
            }
        });

        btnVolDownMusica.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                efectoBoton.play(audioManager.getVolEfectos());
                if(audioManager.getVolMusica() > 0){
                    audioManager.setVolMusica(audioManager.getVolMusica() - .25f);
                    musicaFondo.setVolume(audioManager.getVolMusica());
                }

            }
        });

        btnVolUpEfectos.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                efectoBoton.play(audioManager.getVolEfectos());
                if(audioManager.getVolEfectos() < 1f){
                    audioManager.setVolEfectos(audioManager.getVolEfectos() + .25f);
                }
            }
        });

        btnVolDownEfectos.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                efectoBoton.play(audioManager.getVolEfectos());
                if(audioManager.getVolEfectos() > 0){
                    audioManager.setVolEfectos(audioManager.getVolEfectos() - .25f);
                }
            }
        });

        btnMute.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                efectoBoton.play(audioManager.getVolEfectos());
                audioManager.setVolMusica(0);
                audioManager.setVolEfectos(0);
                musicaFondo.setVolume(audioManager.getVolMusica());
            }
        });

        btnRegresar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                efectoBoton.play(audioManager.getVolEfectos());
                efectoBoton.stop();
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
        assetManager.load("Audio/Musica/superMetroid.mp3", Music.class);
        assetManager.load("Audio/Efectos/sonidoboton.mp3", Sound.class);

        //Textura de botones
        assetManager.load("BotonesConf/btnVolArriba.png", Texture.class);
        assetManager.load("BotonesConf/btnVolAbajo.png", Texture.class);
        assetManager.load("BotonesConf/btnVolArribaEf.png", Texture.class);
        assetManager.load("BotonesConf/btnVolAbajoEf.png", Texture.class);
        assetManager.load("BotonesConf/btnMute.png", Texture.class);
        assetManager.load("BotonesConf/btnRegresar.png", Texture.class);

        //Se bloquea hasta cargar los recursos
        assetManager.finishLoading();


        texturaBtnVolUpMusica = assetManager.get("BotonesConf/btnVolArriba.png");
        texturaBtnVolDownMusica = assetManager.get("BotonesConf/btnVolAbajo.png");
        texturaBtnVolUpEfectos = assetManager.get("BotonesConf/btnVolArribaEf.png");
        texturaBtnVolDownEfectos = assetManager.get("BotonesConf/btnVolAbajoEf.png");
        texturaBtnMute = assetManager.get("BotonesConf/btnMute.png");
        texturaBtnRegresar = assetManager.get("BotonesConf/btnRegresar.png");

        musicaFondo = assetManager.get("Audio/Musica/superMetroid.mp3");
        efectoBoton = assetManager.get("Audio/Efectos/sonidoboton.mp3");
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
        //Liberar la memoria usada
        texturaFondo.dispose();
        texturaBtnRegresar.dispose();
        texturaBtnMute.dispose();
        texturaBtnVolDownEfectos.dispose();
        texturaBtnVolDownMusica.dispose();
        texturaBtnVolUpMusica.dispose();
        texturaBtnVolUpEfectos.dispose();
        musicaFondo.dispose();
        efectoBoton.dispose();
        escenaConfig.dispose();

        //Ahora el asset manager libera los recursos
        assetManager.unload("BotonesConf/btnVolArriba.png");
        assetManager.unload("BotonesConf/btnVolAbajo.png");
        assetManager.unload("BotonesConf/btnVolArribaEf.png");
        assetManager.unload("BotonesConf/btnVolAbajoEf.png");
        assetManager.unload("BotonesConf/btnMute.png");
        assetManager.unload("BotonesConf/btnRegresar.png");
        assetManager.unload("Audio/Musica/superMetroid.mp3");
        assetManager.unload("Audio/Efectos/sonidoboton.mp3");

    }
}
