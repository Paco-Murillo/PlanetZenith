package mx.jfml;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class PantallaMenu extends Pantalla {

    private final Juego juego;

    //Textura
    private Texture texturaFondo;
    private Texture texturaBotonJugar;
    private Texture texturaBotonConfigurar;
    private Texture texturaBotonCreditos;

    // Menu
    private Stage escenaMenu;

    //Seleccionador de nivel
    private  SeleccionaNivel seleccionaNivel;

    //Texto
    private Escritura txtZenith;

    //Asset Manager
    private  final AssetManager assetManager;

    //Audio Manager
    private AudioManejador audioManager;

    //Musica y Efectos
    private Music musicaFondo;

    public PantallaMenu(Juego juego) {

        this.juego = juego;
        assetManager = juego.getAssetManager();
        audioManager = juego.getAudioManejador();
        seleccionaNivel = juego.getSeleccionaNivel();
    }


    @Override
    public void show() {
        texturaFondo = new Texture("Fondos/fondoPantalla.png");

        crearMenu();

    }

    private void crearMenu() throws GdxRuntimeException {
        cargarAssets();

        escenaMenu = new Stage(vista);

        Gdx.input.setInputProcessor(escenaMenu);

        musicaFondo.setVolume(audioManager.getVolMusica());

        try {
            if (!audioManager.getTocando()) {
                musicaFondo.setLooping(true);
                musicaFondo.play();
            }
        } catch(GdxRuntimeException g){
            musicaFondo.dispose();

            assetManager.unload("Audio/Musica/superMetroid.mp3");

            assetManager.load("Audio/Musica/superMetroid.mp3", Music.class);

            assetManager.finishLoading();

            musicaFondo = assetManager.get("Audio/Musica/superMetroid.mp3");

            if(!audioManager.getTocando()){
                musicaFondo.setLooping(true);
                musicaFondo.play();
            }
        }

        //Texto
        txtZenith = new Escritura(ANCHO/2, ALTO - 120);
        txtZenith.setEnunciado("Planet Zenith");

        // Boton jugar
        TextureRegionDrawable trdJugar = new TextureRegionDrawable(new TextureRegion(texturaBotonJugar));
        ImageButton botonJugar = new ImageButton(trdJugar);
        botonJugar.setPosition(ANCHO/3 - botonJugar.getWidth()/2, ALTO/2 - botonJugar.getHeight()/2);
        escenaMenu.addActor(botonJugar);

        //Boton Creditos
        TextureRegionDrawable trdCreditos = new TextureRegionDrawable(new TextureRegion(texturaBotonCreditos));
        ImageButton botonCreditos = new ImageButton(trdCreditos);
        botonCreditos.setPosition(botonJugar.getX() + botonJugar.getWidth() + 120 - botonCreditos.getWidth()/2, ALTO/2 - botonCreditos.getHeight()/2);
        escenaMenu.addActor(botonCreditos);

        //Boton Configuración
        TextureRegionDrawable trdConfigurar = new TextureRegionDrawable(new TextureRegion(texturaBotonConfigurar));
        ImageButton botonConfigurar = new ImageButton(trdConfigurar);
        botonConfigurar.setPosition(botonCreditos.getX() + botonCreditos.getWidth() + 120 - botonConfigurar.getWidth()/2, ALTO/2 - botonConfigurar.getHeight()/2);
        escenaMenu.addActor(botonConfigurar);

        //Listener
        botonJugar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                audioManager.setTocando(!audioManager.getTocando());
                juego.setScreen(new PantallaSelecNivel(juego));
            }
        });

        botonConfigurar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                audioManager.setTocando(!audioManager.getTocando());
                juego.setScreen(new PantallaConfiguracion(juego));
            }
        });

        botonCreditos.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                audioManager.setTocando(!audioManager.getTocando());
                juego.setScreen(new PantallaCreditos(juego));
            }
        });

    }

    private void cargarAssets() {
        //Musica de fondo
        assetManager.load("Audio/Musica/principal.wav", Music.class);


        //Textura botones
        assetManager.load("BotonesMenu/btnJugar.png", Texture.class);
        assetManager.load("BotonesMenu/btnCred.png", Texture.class);
        assetManager.load("BotonesMenu/btnConf.png", Texture.class);

        assetManager.finishLoading();

        texturaBotonJugar = assetManager.get("BotonesMenu/btnJugar.png");
        texturaBotonConfigurar = assetManager.get("BotonesMenu/btnConf.png");
        texturaBotonCreditos = assetManager.get("BotonesMenu/btnCred.png");

        musicaFondo = assetManager.get("Audio/Musica/principal.wav");

    }


    @Override
    public void render(float delta) {
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);

        batch.begin();

        batch.draw(texturaFondo,0,0);

        txtZenith.render(batch);

        batch.end();

        escenaMenu.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        texturaFondo.dispose();
        texturaBotonCreditos.dispose();
        texturaBotonConfigurar.dispose();
        texturaBotonJugar.dispose();
        musicaFondo.dispose();
        escenaMenu.dispose();

        assetManager.unload("BotonesMenu/btnJugar.png");
        assetManager.unload("BotonesMenu/btnConf.png");
        assetManager.unload("BotonesMenu/btnConf.png");
        assetManager.unload("Audio/Musica/principal.wav");
    }
}
