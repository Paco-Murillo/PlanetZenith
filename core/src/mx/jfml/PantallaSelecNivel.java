package mx.jfml;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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

public class PantallaSelecNivel extends Pantalla {

    private final Juego juego;

    //Textura
    private Texture texturaFondo;
    private Texture texturaNvlUno;
    private Texture texturaNvlDos;
    private Texture texturaNvlTres;
    private Texture texturaBtnRegresar;

    //Seleccionador de Nivel
    private SeleccionaNivel seleccionaNivel;

    //Vista
    private Stage escenaSeleccion;

    //AssetManager
    private final AssetManager assetManager;

    //Audio Manejador
    private AudioManejador audioManejador;

    //Musica y Efectos
    private Music musicaFondo;
    private Sound efectoSelec;

    public PantallaSelecNivel(Juego juego){
        this.juego = juego;
        assetManager = juego.getAssetManager();
        audioManejador = juego.getAudioManejador();
        seleccionaNivel = juego.getSeleccionaNivel();
    }

    @Override
    public void show() {

        crearSeleccion();

    }

    private void crearSeleccion() throws GdxRuntimeException {
        cargarAssets();

        try{
            if(!audioManejador.getTocando()){
                musicaFondo.setLooping(true);
                musicaFondo.play();
            }
        } catch(GdxRuntimeException e){
            musicaFondo.dispose();

            assetManager.unload("Audio/Musica/superMetroid.mp3");

            assetManager.load("Audio/Musica/superMetroid.mp3", Music.class);

            assetManager.finishLoading();

            musicaFondo = assetManager.get("Audio/Musica/superMetroid.mp3");

            if(!audioManejador.getTocando()){
                musicaFondo.setLooping(true);
                musicaFondo.play();
            }
        }

        escenaSeleccion = new Stage(vista);

        Gdx.input.setInputProcessor(escenaSeleccion);
        Gdx.input.setCatchKey(Input.Keys.BACK, true);

        Image imagenFondo = new Image(texturaFondo);

        float escalaX = ANCHO / imagenFondo.getWidth();
        float escalaY = ALTO / imagenFondo.getHeight();
        imagenFondo.setScale(escalaX,escalaY);
        escenaSeleccion.addActor(imagenFondo);

        //Botones
        //Boton Regresar
        TextureRegionDrawable trdRegresar = new TextureRegionDrawable(new TextureRegion(texturaBtnRegresar));
        ImageButton btnRegresar = new ImageButton(trdRegresar);
        btnRegresar.setPosition(64,64);
        escenaSeleccion.addActor(btnRegresar);

        //Boton Nivel 2
        TextureRegionDrawable trdNivelDos = new TextureRegionDrawable(new TextureRegion(texturaNvlDos));
        ImageButton btnNivelDos = new ImageButton(trdNivelDos);
        btnNivelDos.setPosition(ANCHO/2 - btnNivelDos.getWidth()/2, ALTO/2 - btnNivelDos.getHeight()/2);
        escenaSeleccion.addActor(btnNivelDos);

        //Boton Nivel 1
        TextureRegionDrawable trdNivelUno = new TextureRegionDrawable(new TextureRegion(texturaNvlUno));
        ImageButton btnNivelUno = new ImageButton(trdNivelUno);
        btnNivelUno.setPosition(btnNivelDos.getX() - 300 - btnNivelUno.getWidth()/2, ALTO/2 - btnNivelUno.getHeight()/2);
        escenaSeleccion.addActor(btnNivelUno);

        //Boton Nivel 3
        TextureRegionDrawable trdNivelTres = new TextureRegionDrawable(new TextureRegion(texturaNvlTres));
        ImageButton btnNivelTres = new ImageButton(trdNivelTres);
        btnNivelTres.setPosition(btnNivelDos.getX() + btnNivelDos.getWidth() + 300 - btnNivelTres.getWidth()/2, ALTO/2 - btnNivelTres.getHeight()/2);
        escenaSeleccion.addActor(btnNivelTres);

        //Listener
        btnRegresar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                musicaFondo.stop();
                audioManejador.setTocando(!audioManejador.getTocando());
                musicaFondo.dispose();
                efectoSelec.dispose();
                juego.setScreen(new PantallaMenu(juego));
                }
            });

        btnNivelUno.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                super.clicked(event, x, y);
                efectoSelec.play(audioManejador.getVolEfectos());
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                musicaFondo.stop();
                juego.setSeleccionaNivel(seleccionaNivel.NIVELUNO);
                audioManejador.setTocando(!audioManejador.getTocando());
                musicaFondo.dispose();
                efectoSelec.dispose();
                juego.setScreen(new PantallaJuegoNivelUno(juego));
            }
        });

        btnNivelDos.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                efectoSelec.play(audioManejador.getVolEfectos());
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                musicaFondo.stop();
                juego.setSeleccionaNivel(seleccionaNivel.NIVELDOS);
                audioManejador.setTocando(!audioManejador.getTocando());
                musicaFondo.dispose();
                efectoSelec.dispose();
                juego.setScreen(new PantallaJuegoNivelDos(juego));
            }
        });

        btnNivelTres.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                efectoSelec.play(audioManejador.getVolEfectos());
                try{
                    Thread.sleep(1500);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                musicaFondo.stop();
                juego.setSeleccionaNivel(seleccionaNivel.NIVELTRES);
                audioManejador.setTocando(!audioManejador.getTocando());
                musicaFondo.dispose();
                efectoSelec.dispose();
                juego.setScreen(new PantallaJuegoNivelTres(juego));
            }
        });
    }

    private void cargarAssets() {
        assetManager.load("Fondos/fondoPantalla.png", Texture.class);
        assetManager.load("Audio/Musica/principal.wav", Music.class);
        assetManager.load("Audio/Efectos/selecNivel.wav", Sound.class);
        assetManager.load("BotonesConf/btnRegresar.png", Texture.class);
        assetManager.load("BotonesSelNivel/btnNiveles1.png", Texture.class);
        assetManager.load("BotonesSelNivel/btnNiveles2.png", Texture.class);
        assetManager.load("BotonesSelNivel/btnNiveles3.png", Texture.class);

        assetManager.finishLoading();

        texturaFondo = assetManager.get("Fondos/fondoPantalla.png");
        texturaBtnRegresar = assetManager.get("BotonesConf/btnRegresar.png");
        texturaNvlUno = assetManager.get("BotonesSelNivel/btnNiveles1.png");
        texturaNvlDos = assetManager.get("BotonesSelNivel/btnNiveles2.png");
        texturaNvlTres = assetManager.get("BotonesSelNivel/btnNiveles3.png");
        musicaFondo = assetManager.get("Audio/Musica/principal.wav");
        efectoSelec = assetManager.get("Audio/Efectos/selecNivel.wav");
    }

    @Override
    public void render(float delta) {
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);

        batch.begin();

        batch.end();

        escenaSeleccion.draw();

        if(Gdx.input.isKeyPressed(Input.Keys.BACK)){
            musicaFondo.stop();
            audioManejador.setTocando(!audioManejador.getTocando());
            musicaFondo.dispose();
            efectoSelec.dispose();
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
        texturaBtnRegresar.dispose();
        texturaNvlUno.dispose();
        texturaNvlDos.dispose();
        texturaNvlTres.dispose();
        texturaFondo.dispose();
        musicaFondo.dispose();
        efectoSelec.dispose();
        escenaSeleccion.dispose();

        assetManager.unload("Fondos/fondoPantalla.png");
        assetManager.unload("BotonesConf/btnRegresar.png");
        assetManager.unload("BotonesSelNivel/btnNiveles1.png");
        assetManager.unload("BotonesSelNivel/btnNiveles2.png");
        assetManager.unload("BotonesSelNivel/btnNiveles3.png");
        assetManager.unload("Audio/Musica/principal.wav");
        assetManager.unload("Audio/Efectos/selecNivel.wav");
    }
}
