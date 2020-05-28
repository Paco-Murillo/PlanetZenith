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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class PantallaPerder extends Pantalla {

    private final Juego juego;

    //Texture
    private Texture texturaFondo;
    private Texture texturaBtnHogar;
    private Texture texturaBtnReintentar;

    //Escena Perder
    private Stage escenaPerder;

    //Asset Manager
    private final AssetManager assetManager;

    //Audio Manejador
    private  final  AudioManejador audioManejador;

    //Audio fondo
    private Music musicaPerder;

    //Seleccionador de nivel
    private SeleccionaNivel seleccionaNivel;

    public PantallaPerder(Juego juego){
        this.juego = juego;
        this.assetManager = juego.getAssetManager();
        this.audioManejador = juego.getAudioManejador();
        this.seleccionaNivel = juego.getSeleccionaNivel();
    }

    @Override
    public void show() {
        crearPerder();
    }

    private void crearPerder()  throws GdxRuntimeException {
        cargarAssets();

        escenaPerder = new Stage(vista);


        try{
            musicaPerder.setLooping(true);
            musicaPerder.setVolume(audioManejador.getVolMusica());
            musicaPerder.play();
        } catch(GdxRuntimeException e){
            musicaPerder.dispose();
            assetManager.unload("Audio/Musica/perder.mp3");

            assetManager.load("Audio/Musica/perder.mp3", Music.class);

            assetManager.finishLoading();

            musicaPerder = assetManager.get("Audio/Musica/perder.mp3");

            musicaPerder.setLooping(true);
            musicaPerder.setVolume(audioManejador.getVolMusica());
            musicaPerder.play();

        }
        Gdx.input.setInputProcessor(escenaPerder);

        Image imageFondo = new Image(texturaFondo);

        float escalaX = ANCHO / imageFondo.getWidth();
        float escalaY = ALTO / imageFondo.getHeight();
        imageFondo.setScale(escalaX, escalaY);
        escenaPerder.addActor(imageFondo);

        //Botones
        //Boton Hogar
        TextureRegionDrawable trdBtnHogar = new TextureRegionDrawable(new TextureRegion(texturaBtnHogar));
        ImageButton BtnHogar = new ImageButton(trdBtnHogar);
        BtnHogar.setPosition(4* ANCHO / 9 - BtnHogar.getWidth() / 2, ALTO / 3 - BtnHogar.getHeight() / 2);
        escenaPerder.addActor(BtnHogar);

        //Boton Reintentar
        TextureRegionDrawable trdBtnRetry = new TextureRegionDrawable(new TextureRegion(texturaBtnReintentar));
        ImageButton BtnRetry = new ImageButton(trdBtnRetry);
        BtnRetry.setPosition(BtnHogar.getX() + BtnHogar.getWidth() + 200f - BtnRetry.getWidth() / 2,ALTO / 3 - BtnRetry.getHeight() / 2);
        escenaPerder.addActor(BtnRetry);

        //Listener
        BtnHogar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                musicaPerder.stop();
                musicaPerder.dispose();
                audioManejador.setTocando(false);
                juego.setScreen(new PantallaMenu(juego));
            }
        });

        BtnRetry.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                switch(seleccionaNivel){
                    case NIVELUNO:
                        musicaPerder.stop();
                        musicaPerder.dispose();
                        juego.setScreen(new PantallaJuegoNivelUno(juego));
                        break;
                    case NIVELDOS:
                        musicaPerder.stop();
                        musicaPerder.dispose();
                        juego.setScreen(new PantallaJuegoNivelDos(juego));
                        break;
                    case NIVELTRES:
                        musicaPerder.stop();
                        musicaPerder.dispose();
                        juego.setScreen(new PantallaJuegoNivelTres(juego));
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void cargarAssets() {
        //Texturas
        assetManager.load("Fondos/GameOver.png", Texture.class);
        assetManager.load("BotonesGyP/btnHogar.png", Texture.class);
        assetManager.load("BotonesGyP/btnReintentar.png", Texture.class);
        assetManager.load("Audio/Musica/perder.mp3", Music.class);


        assetManager.finishLoading();

        texturaFondo = assetManager.get("Fondos/GameOver.png");
        texturaBtnHogar = assetManager.get("BotonesGyP/btnHogar.png");
        texturaBtnReintentar = assetManager.get("BotonesGyP/btnReintentar.png");
        musicaPerder = assetManager.get("Audio/Musica/perder.mp3");
    }

    @Override
    public void render(float delta) {
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);

        batch.begin();

        batch.end();

        escenaPerder.draw();
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
        texturaBtnReintentar.dispose();
        texturaBtnHogar.dispose();
        escenaPerder.dispose();
        musicaPerder.dispose();

        assetManager.unload("Fondos/GameOver.png");
        assetManager.unload("BotonesGyP/btnHogar.png");
        assetManager.unload("BotonesGyP/btnReintentar.png");
        assetManager.unload("Audio/Musica/perder.mp3");

    }
}
