package mx.jfml;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

class PantallaMenu extends Pantalla {

    private final Juego juego;
    private Texture texturaFondo;

    // Menu
    private Stage escenaMenu;

    // Audio
    public static AudioManejador audioManager;

    //Texto
    private Escritura txtZenith;

    public PantallaMenu(Juego juego) {
        this.juego = juego;
    }


    @Override
    public void show() {
        texturaFondo = new Texture("Fondos/fondoPantalla.png");

        crearMenu();
    }

    private void crearMenu() {
        escenaMenu = new Stage(vista);

        audioManager = new AudioManejador(new AssetManager());

        audioManager.setLooping(true);
        if(!audioManager.tocando) {
            audioManager.playMusica();
        }

        //Texto
        txtZenith = new Escritura(ANCHO/2, ALTO - 120);
        txtZenith.setEnunciado("Planet Zenith");

        // Boton jugar
        Texture texturaBotonJugar = new Texture("BotonesMenu/btnJugar.png");
        TextureRegionDrawable trdJugar = new TextureRegionDrawable(new TextureRegion(texturaBotonJugar));

        ImageButton botonJugar = new ImageButton(trdJugar);
        botonJugar.setPosition(ANCHO/3 - botonJugar.getWidth()/2, ALTO/2 - botonJugar.getHeight()/2);

        //Boton Creditos
        Texture texturaBotonCreditos = new Texture("BotonesMenu/btnCred.png");
        TextureRegionDrawable trdCreditos = new TextureRegionDrawable(new TextureRegion(texturaBotonCreditos));

        ImageButton botonCreditos = new ImageButton(trdCreditos);
        botonCreditos.setPosition(botonJugar.getX() + botonJugar.getWidth() + 120 - botonCreditos.getWidth()/2, ALTO/2 - botonCreditos.getHeight()/2);

        //Boton Configuración
        Texture texturaBotonConfigurar = new Texture("BotonesMenu/btnConf.png");
        TextureRegionDrawable trdConfigurar = new TextureRegionDrawable(new TextureRegion(texturaBotonConfigurar));

        ImageButton botonConfigurar = new ImageButton(trdConfigurar);
        botonConfigurar.setPosition(botonCreditos.getX() + botonCreditos.getWidth() + 120 - botonConfigurar.getWidth()/2, ALTO/2 - botonConfigurar.getHeight()/2);


        //Listener
        botonJugar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                audioManager.efectoBtnMenu.play();
                juego.setScreen(new PantallaJuegoNivelUno(juego));
            }
        });

        botonConfigurar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                audioManager.efectoBtnMenu.play();
                audioManager.stopMusica();
                audioManager.setTocando(true);
                juego.setScreen(new PantallaConfiguracion(juego));
            }
        });

        botonCreditos.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                audioManager.efectoBtnMenu.play();
                audioManager.stopMusica();
                audioManager.setTocando(true);
                juego.setScreen(new PantallaCreditos(juego));
            }
        });



        escenaMenu.addActor(botonJugar);
        escenaMenu.addActor(botonCreditos);
        escenaMenu.addActor(botonConfigurar);



        Gdx.input.setInputProcessor(escenaMenu);
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
    public void dispose() { texturaFondo.dispose(); }
}
