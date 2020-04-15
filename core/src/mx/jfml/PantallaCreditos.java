package mx.jfml;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

class PantallaCreditos extends Pantalla {

    private final Juego juego;
    private Texture texturaFondo;

    //Creditos
    private Stage escenaCreditos;

    //Texto
    private Escritura txtCreditos;
    private Escritura txtJon;
    private Escritura txtEmi;
    private Escritura txtJulio;
    private Escritura txtJose;
    private Escritura txtDerechos1;
    private  Escritura txtDerechos2;

    public PantallaCreditos(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        texturaFondo = new Texture("Fondos/fondo1.jpg");

        crearCreditos();
    }

    private void crearCreditos() {
        escenaCreditos = new Stage(vista);

        cargarEscritura();

        //Boton Regresar
        Texture texturabtnRegresar = new Texture("BotonesConf/btnRegresar.png");
        TextureRegionDrawable trdRegresar = new TextureRegionDrawable(new TextureRegion(texturabtnRegresar));

        ImageButton btnRegresar = new ImageButton(trdRegresar);
        btnRegresar.setPosition(64,64);

        //Listener
        btnRegresar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new PantallaMenu(juego));
            }
        });

        escenaCreditos.addActor(btnRegresar);

        Gdx.input.setInputProcessor(escenaCreditos);
    }

    private void cargarEscritura() {
        txtCreditos = new Escritura(ANCHO/2, ALTO - ALTO*.1f);
        txtCreditos.setEnunciado("Creditos");

        txtJon = new Escritura(ANCHO/2, ALTO/3 + 195f);
        txtJon.setEnunciado("Jonathan Rosas");

        txtEmi = new Escritura(ANCHO/2, ALTO/3 + 130f);
        txtEmi.setEnunciado("Emiliano Gomez");

        txtJulio = new Escritura(ANCHO/2, ALTO/3 + 65f);
        txtJulio.setEnunciado("Julio Cesar Lugo");

        txtJose = new Escritura(ANCHO/2, ALTO/3);
        txtJose.setEnunciado("Jose Francicso Murillo");

        txtDerechos1 = new Escritura(ANCHO/2, ALTO/10 + 20);
        txtDerechos1.setTexto(new Texto("Fuentes/fuente30px.fnt"));
        txtDerechos1.setEnunciado("Todos los assets prestados pertenecen");

        txtDerechos2 = new Escritura(ANCHO/2, ALTO/10);
        txtDerechos2.setTexto(new Texto("Fuentes/fuente30px.fnt"));
        txtDerechos2.setEnunciado("a sus autores correspondientes");
    }

    @Override
    public void render(float delta) {
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);

        batch.begin();

        batch.draw(texturaFondo,0,0);

        //Textos
        txtCreditos.render(batch);
        txtJon.render(batch);
        txtEmi.render(batch);
        txtJulio.render(batch);
        txtJose.render(batch);
        txtDerechos1.render(batch);
        txtDerechos2.render(batch);

        batch.end();

        escenaCreditos.draw();
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
    }
}
