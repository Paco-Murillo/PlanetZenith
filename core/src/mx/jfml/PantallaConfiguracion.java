package mx.jfml;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
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


    public PantallaConfiguracion(Juego juego) {
        this.juego = juego;
        assetManager = juego.getAssetManager();
    }

    @Override
    public void show() {
        texturaFondo = new Texture("Fondos/fondoPantalla.png");

        crearAjustes();
    }

    private void crearAjustes() {
        cargarEscritura();
        cargarTexturas();

        audioManager = new AudioManejador(assetManager);

        escenaConfig = new Stage(vista);

        Gdx.input.setInputProcessor(escenaConfig);

        audioManager.playMusica();

        //Botones
        //Boton Subir Volumen Musica
        TextureRegionDrawable trdVolUpMusica = new TextureRegionDrawable(new TextureRegion(texturaBtnVolUpMusica));
        ImageButton btnVolUpMusica = new ImageButton(trdVolUpMusica);
        btnVolUpMusica.setPosition(ANCHO*.66f + btnVolUpMusica.getWidth()/2, ALTO*.66f - btnVolUpMusica.getHeight()/2);
        escenaConfig.addActor(btnVolUpMusica);

        //Boton Bajar Volumen Musica
        TextureRegionDrawable trdVolDownMusica = new TextureRegionDrawable(new TextureRegion(texturaBtnVolDownMusica));
        ImageButton btnVolDownMusica = new ImageButton(trdVolDownMusica);
        btnVolDownMusica.setPosition(btnVolUpMusica.getX() + btnVolUpMusica.getWidth() + 30f, ALTO*.66f - btnVolDownMusica.getHeight()/2);
        escenaConfig.addActor(btnVolDownMusica);

        //Boton Subir Volumen Efectos
        TextureRegionDrawable trdVolUpEfectos = new TextureRegionDrawable(new TextureRegion(texturaBtnVolUpEfectos));
        ImageButton btnVolUpEfectos = new ImageButton(trdVolUpEfectos);
        btnVolUpEfectos.setPosition(ANCHO*.66f, ALTO/2 - btnVolUpEfectos.getHeight()/2);
        escenaConfig.addActor(btnVolUpEfectos);

        //Boton Bajar Volumen Efectos
        TextureRegionDrawable trdVolDownEfectos = new TextureRegionDrawable(new TextureRegion(texturaBtnVolDownEfectos));
        ImageButton btnVolDownEfectos = new ImageButton(trdVolDownEfectos);
        btnVolDownEfectos.setPosition(btnVolUpEfectos.getX() + btnVolUpEfectos.getWidth() + 30f, ALTO/2 - btnVolUpEfectos.getHeight()/2);
        escenaConfig.addActor(btnVolDownEfectos);

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
                audioManager.efectoBtnMenu.play(audioManager.getVolEfectos());
                if(audioManager.getVolMusica() < 1f){
                    audioManager.setVolMusica(audioManager.getVolMusica() + .15f);
                } else if(audioManager.getVolMusica() >= 1f){
                    audioManager.setVolMusica(1f);
                }
            }
        });

        btnVolDownMusica.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                audioManager.efectoBtnMenu.play(audioManager.getVolEfectos());
                if(audioManager.getVolMusica() > 0){
                    audioManager.setVolMusica(audioManager.getVolMusica() - .15f);
                } else if(audioManager.getVolMusica() <= 0){
                    audioManager.setVolMusica(0);
                }
            }
        });

        btnVolUpEfectos.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                audioManager.efectoBtnMenu.play(audioManager.getVolEfectos());
                if(audioManager.getVolEfectos() < 1f){
                    audioManager.setVolEfectos(audioManager.getVolEfectos() + .15f);
                } else if(audioManager.getVolEfectos() >= 1f){
                    audioManager.setVolEfectos(1f);
                }
            }
        });

        btnVolDownEfectos.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                audioManager.efectoBtnMenu.play(audioManager.getVolEfectos());
                if(audioManager.getVolEfectos() > 0){
                    audioManager.setVolEfectos(audioManager.getVolEfectos()-.15f);
                } else if(audioManager.getVolMusica() <= 0){
                    audioManager.setVolEfectos(0);
                }
            }
        });

        btnMute.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                audioManager.efectoBtnMenu.play(audioManager.getVolEfectos());
                audioManager.setVolMusica(0);
                audioManager.setVolEfectos(-1f);
            }
        });

        btnRegresar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                audioManager.setTocando(false);
                audioManager.efectoBtnMenu.play(audioManager.getVolEfectos());
                juego.setScreen(new PantallaMenu(juego));
                audioManager.stopMusica();
            }
        });


    }


    private void cargarTexturas() {


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
        texturaBtnRegresar.dispose();
        texturaBtnMute.dispose();
        texturaBtnVolDownEfectos.dispose();
        texturaBtnVolDownMusica.dispose();
        texturaBtnVolUpMusica.dispose();
        texturaBtnVolUpEfectos.dispose();
        audioManager.dispose();
        escenaConfig.dispose();

        //Ahora el asset manager libera los recursos
        assetManager.unload("BotonesConf/btnVolArriba.png");
        assetManager.unload("BotonesConf/btnVolAbajo.png");
        assetManager.unload("BotonesConf/btnVolArribaEf.png");
        assetManager.unload("BotonesConf/btnVolAbajoEf.png");
        assetManager.unload("BotonesConf/btnMute.png");
        assetManager.unload("BotonesConf/btnRegresar.png");
        audioManager.unLoad();

    }
}
