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
    private Music audioFondo;
    private Sound efectoBoton;

    public PantallaMenu(Juego juego) {
        this.juego = juego;
    }


    @Override
    public void show() {
        texturaFondo = new Texture("fondo1.jpg");

        crearMenu();
    }

    private void crearMenu() {
        escenaMenu = new Stage(vista);

        musicayEfectos();

        // Boton jugar
        Texture texturaBotonJugar = new Texture("button_jugar.png");
        TextureRegionDrawable trdJugar = new TextureRegionDrawable(new TextureRegion(texturaBotonJugar));

        ImageButton botonJugar = new ImageButton(trdJugar);
        botonJugar.setPosition(ANCHO/2-botonJugar.getWidth()/2, 2*ALTO/3);

        //Boton Creditos
        Texture texturaBotonCreditos = new Texture("button_creditos.png");
        TextureRegionDrawable trdCreditos = new TextureRegionDrawable(new TextureRegion(texturaBotonCreditos));

        ImageButton botonCreditos = new ImageButton(trdCreditos);
        botonCreditos.setPosition(ANCHO/2-botonCreditos.getWidth()/2, 2*ALTO/3-2*botonCreditos.getHeight());

        //Boton Configuración
        Texture texturaBotonConfigurar = new Texture("button_configurar.png");
        TextureRegionDrawable trdConfigurar = new TextureRegionDrawable(new TextureRegion(texturaBotonConfigurar));

        ImageButton botonConfigurar = new ImageButton(trdConfigurar);
        botonConfigurar.setPosition(ANCHO/2-botonConfigurar.getWidth()/2, 2*ALTO/3-4*botonConfigurar.getHeight());


        //Listener
        botonJugar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                efectoBoton.play();
                audioFondo.stop();
                juego.setScreen(new PantallaJuegoNivelUno(juego));
            }
        });



        escenaMenu.addActor(botonJugar);
        escenaMenu.addActor(botonCreditos);
        escenaMenu.addActor(botonConfigurar);



        Gdx.input.setInputProcessor(escenaMenu);
    }

    private void musicayEfectos(){
        //AssetManager y musica
        AssetManager manager = new AssetManager();
        manager.load("sonidoboton.mp3", Sound.class);
        manager.load("superMetroid.mp3", Music.class);
        manager.finishLoading();

        //audio
        audioFondo = manager.get("superMetroid.mp3");
        audioFondo.setLooping(true);
        audioFondo.play();

        //efecto
        efectoBoton = manager.get("sonidoboton.mp3");
    }

    @Override
    public void render(float delta) {
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);

        batch.begin();

        batch.draw(texturaFondo,0,0);
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
