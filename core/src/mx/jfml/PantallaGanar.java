package mx.jfml;

import com.badlogic.gdx.Gdx;
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

public class PantallaGanar extends Pantalla {

    private final Juego juego;

    //Textura
    private Texture texturaFondo;
    private Texture texturaBotonHogar;
    private Texture texturaBotonSiguiente;

    //Ganar
    private Stage escenaGanar;

    //Seleccionador de Nivel
    private  SeleccionaNivel seleccionaNivel;

    //Asset Manager
    private AssetManager assetManager;

    //Audio Manejador
    private AudioManejador audioManejador;

    //Audio
    private Music victoria;

    public PantallaGanar(Juego juego){
        this.juego = juego;
        this.assetManager = juego.getAssetManager();
        this.audioManejador = juego.getAudioManejador();
        this.seleccionaNivel = juego.getSeleccionaNivel();
    }

    @Override
    public void show() {

        crearGanar();

    }

    private void crearGanar() {
        cargarAssets();

        escenaGanar = new Stage(vista);

        Gdx.input.setInputProcessor(escenaGanar);

        Image imageFondo = new Image(texturaFondo);

        float escalaX = ANCHO / imageFondo.getWidth();
        float escalaY = ALTO / imageFondo.getHeight();
        imageFondo.setScale(escalaX, escalaY);
        escenaGanar.addActor(imageFondo);

        //Botones
        //Hogar
        TextureRegionDrawable trdHogar = new TextureRegionDrawable(new TextureRegion(texturaBotonHogar));
        ImageButton botonHogar = new ImageButton(trdHogar);
        botonHogar.setPosition(ANCHO / 3 - botonHogar.getWidth() / 2, ALTO / 3 - botonHogar.getHeight() / 2);
        escenaGanar.addActor(botonHogar);

        //Siguiente
        TextureRegionDrawable trdSiguiente = new TextureRegionDrawable(new TextureRegion(texturaBotonSiguiente));
        ImageButton botonSiguiente = new ImageButton(trdSiguiente);
        botonSiguiente.setPosition(botonHogar.getX() + botonHogar.getWidth() + 200f - botonSiguiente.getWidth()/2, ALTO/3 - botonSiguiente.getHeight()/2);
        escenaGanar.addActor(botonSiguiente);

        //Listener
        botonHogar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new PantallaMenu(juego));
            }
        });

        botonSiguiente.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event,x,y);
                switch(seleccionaNivel){
                    case NIVELUNO:
                        juego.setSeleccionaNivel(SeleccionaNivel.NIVELDOS);
                        juego.setScreen(new PantallaJuegoNivelDos(juego));
                    default:
                        break;
                }

            }
        });
    }

    private void cargarAssets() {
        assetManager.load("Fondos/Success.png", Texture.class);
        assetManager.load("BotonesGyP/btnHogar.png", Texture.class);
        assetManager.load("BotonesGyP/btnSiguiente.png", Texture.class);

        assetManager.finishLoading();

        texturaFondo = assetManager.get("Fondos/Success.png");
        texturaBotonHogar = assetManager.get("BotonesGyP/btnHogar.png");
        texturaBotonSiguiente = assetManager.get("BotonesGyP/btnSiguiente.png");
    }

    @Override
    public void render(float delta) {
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);

        batch.begin();

        batch.end();

        escenaGanar.draw();
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
        texturaBotonSiguiente.dispose();
        texturaBotonHogar.dispose();
        escenaGanar.dispose();

        assetManager.unload("Fondos/Success.png");
        assetManager.unload("BotonesGyP/btnHogar.png");
        assetManager.unload("BotonesGyP/btnSiguiente.png");
    }
}
