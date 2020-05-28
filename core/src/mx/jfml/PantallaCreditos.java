package mx.jfml;

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
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class PantallaCreditos extends Pantalla {

    private final Juego juego;

    //Texturas
    private Texture texturaFondo;
    private Texture texturabtnRegresar;

    //Creditos
    private Stage escenaCreditos;

    //Texto
    private Escritura txtCreditos;
    private Escritura txtAutores;
    private Escritura txtJon;
    private Escritura txtEmi;
    private Escritura txtJulio;
    private Escritura txtJose;
    private Escritura txtDerechos1;
    private  Escritura txtDerechos2;
    private Escritura txtDerechos3;
    private Escritura txtDerechos4;
    private Escritura txtDerechos5;

    //Asset manager
    private final AssetManager assetManager;

    //Audio manager
    private AudioManejador audioManager;

    //Audio
    private Music musicaFondo;

    public PantallaCreditos(Juego juego) {
        this.juego = juego;
        assetManager = juego.getAssetManager();
        audioManager = juego.getAudioManejador();
    }

    @Override
    public void show() {
        texturaFondo = new Texture("Fondos/fondoPantalla.png");

        crearCreditos();
    }

    private void crearCreditos() {
        cargarEscritura();
        cargarAssets();

        escenaCreditos = new Stage(vista);

        Gdx.input.setInputProcessor(escenaCreditos);
        Gdx.input.setCatchKey(Input.Keys.BACK, true);

        //Boton Regresar
        TextureRegionDrawable trdRegresar = new TextureRegionDrawable(new TextureRegion(texturabtnRegresar));
        ImageButton btnRegresar = new ImageButton(trdRegresar);
        btnRegresar.setPosition(64,64);
        escenaCreditos.addActor(btnRegresar);

        //Listener
        btnRegresar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                musicaFondo.stop();
                audioManager.setTocando(!audioManager.getTocando());
                musicaFondo.dispose();
                juego.setScreen(new PantallaMenu(juego));
            }
        });
    }


    private void cargarAssets() {
        assetManager.load("BotonesConf/btnRegresar.png", Texture.class);

        assetManager.load("Audio/Musica/principal.wav", Music.class);


        assetManager.finishLoading();

        texturabtnRegresar = assetManager.get("BotonesConf/btnRegresar.png");

        musicaFondo = assetManager.get("Audio/Musica/principal.wav");
    }

    private void cargarEscritura() {
        txtCreditos = new Escritura(ANCHO/2, ALTO - ALTO*.1f);
        txtCreditos.setEnunciado("Creditos");

        txtAutores = new Escritura(ANCHO/2, 4*ALTO/9 + 195f);
        txtAutores.setTexto(new Texto("Fuentes/fuente30px.fnt"));
        txtAutores.setEnunciado("Autores");

        txtJon = new Escritura(ANCHO/2, 4*ALTO/9 + 160f);
        txtJon.setTexto(new Texto("Fuentes/fuente30px.fnt"));
        txtJon.setEnunciado("Jonathan Rosas");

        txtEmi = new Escritura(ANCHO/2, 4*ALTO/9 + 125f);
        txtEmi.setTexto(new Texto("Fuentes/fuente30px.fnt"));
        txtEmi.setEnunciado("Emiliano Gomez");

        txtJulio = new Escritura(ANCHO/2, 4*ALTO/9 + 90f);
        txtJulio.setTexto(new Texto("Fuentes/fuente30px.fnt"));
        txtJulio.setEnunciado("Julio Cesar Lugo");

        txtJose = new Escritura(ANCHO/2, 4*ALTO/9 + 55f);
        txtJose.setTexto(new Texto("Fuentes/fuente30px.fnt"));
        txtJose.setEnunciado("Jose Francicso Murillo");

        txtDerechos1 = new Escritura(ANCHO/2 , 4*ALTO/9 + 20f);
        txtDerechos1.setTexto(new Texto("Fuentes/fuente30px.fnt"));
        txtDerechos1.setEnunciado("Muchos de los assets prestados son de ");

        txtDerechos2 = new Escritura(ANCHO/2 , 4*ALTO/9 - 15f);
        txtDerechos2.setTexto(new Texto("Fuentes/fuente30px.fnt"));
        txtDerechos2.setEnunciado("dominio publico o sin regalia");

        txtDerechos3 = new Escritura(ANCHO/2, 4*ALTO/9 - 50f);
        txtDerechos3.setTexto(new Texto("Fuentes/fuente30px.fnt"));
        txtDerechos3.setEnunciado("Assets prestados pertenecen a sus autores");

        txtDerechos4 = new Escritura(ANCHO/2, 4*ALTO/9 - 85f);
        txtDerechos4.setTexto(new Texto("Fuentes/fuente30px.fnt"));
        txtDerechos4.setEnunciado("Mike Devils");

        txtDerechos5 = new Escritura(ANCHO/2, 4*ALTO/9 - 120f);
        txtDerechos5.setTexto(new Texto("Fuentes/fuente30px.fnt"));
        txtDerechos5.setEnunciado("Mike Koeing");
    }

    @Override
    public void render(float delta) {
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);

        batch.begin();

        batch.draw(texturaFondo,0,0);

        //Textos
        txtCreditos.render(batch);
        txtAutores.render(batch);
        txtJon.render(batch);
        txtEmi.render(batch);
        txtJulio.render(batch);
        txtJose.render(batch);
        txtDerechos1.render(batch);
        txtDerechos2.render(batch);
        txtDerechos3.render(batch);
        txtDerechos4.render(batch);
        txtDerechos5.render(batch);

        batch.end();

        escenaCreditos.draw();

        //Tecla de Back
        if(Gdx.input.isKeyPressed(Input.Keys.BACK)){
            musicaFondo.stop();
            audioManager.setTocando(!audioManager.getTocando());
            musicaFondo.dispose();
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
        texturaFondo.dispose();
        texturabtnRegresar.dispose();
        musicaFondo.dispose();

        assetManager.unload("BotonesConf/btnRegresar.png");
        assetManager.unload("Audio/Musica/principal.wav");
    }
}
