package mx.jfml;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

public class PantallaJuegoNivelUno extends Pantalla {
    private final Juego juego;

    //Mapa
    private TiledMap mapa;
    private OrthogonalTiledMapRenderer mapRenderer;

    //Personaje
    private Protagonista protagonista;
    private Movimiento movimiento = Movimiento.QUIETO;

    //Pausa
    private EscenaPausa escenaPausa;
    private EstadoJuego estadoJuego = EstadoJuego.JUGANDO; //Jugando, PAusado

    //Bala
    private Array<Bala> arrBalas;
    private Texture texturaBala;
    private int contadorBalas;

    //HUD
    private Stage HUD;
    private OrthographicCamera orthographicCameraHUD;
    private Viewport viewportHUD;
    private Touchpad pad;

    //Enemigos
    private Array<Enemigo> arrEnemigos;


    public PantallaJuegoNivelUno(Juego juego){this.juego = juego;}

    @Override
    public void show() {
        cargaMapa();
        crearProtagonista();
        crearEnemigos();
        crearArrBalas();
        cargarTexturaBala();
        crearHUD();

        Gdx.input.setInputProcessor(HUD);
    }

    private void cargaMapa() {
        AssetManager manager = new AssetManager();
        manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        manager.load("MapaJuego.tmx", TiledMap.class);
        manager.finishLoading();

        mapa = manager.get("MapaJuego.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(mapa);
    }

    private void crearHUD() {
        orthographicCameraHUD = new OrthographicCamera(ANCHO, ALTO);
        orthographicCameraHUD.position.set(ANCHO/2,ALTO/2,0);
        orthographicCameraHUD.update();
        viewportHUD = new StretchViewport(ANCHO,ALTO,orthographicCameraHUD);
        HUD = new Stage(viewportHUD);
        crearBotones();
        crearPad();
    }

    private void crearPad() {
        Skin skin = new Skin();
        skin.add("background", new Texture("BotonesHUD/padBack.png"));
        skin.add("knob", new Texture("BotonesHUD/padKnob.png"));
        Touchpad.TouchpadStyle touchpadStyle = new Touchpad.TouchpadStyle();
        touchpadStyle.background = skin.getDrawable("background");
        touchpadStyle.knob = skin.getDrawable("knob");

        //Crear el pad
        pad = new Touchpad(16, touchpadStyle);
        pad.setBounds(16,16,256,256);
        pad.setColor(1,1,1,0.7f);
        //Eventos
        /*
        pad.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Touchpad pad = (Touchpad)actor;
                if(pad.getKnobX()>0){
                    movimiento = Movimiento.DERECHA;
                }else if(pad.getKnobX()<0){
                    movimiento = Movimiento.IZQUIERDA;
                }else{
                    movimiento = Movimiento.QUIETO;
                }
            }
        });
         */
        HUD.addActor(pad);
    }

    private void crearBotones() {
        ImageButton botonPausa = new ImageButton(new TextureRegionDrawable(new Texture("BotonesHUD/pausa.png")));
        botonPausa.setPosition(0,ALTO-botonPausa.getHeight());
        botonPausa.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                estadoJuego = EstadoJuego.PAUSADO;
                escenaPausa = new EscenaPausa(vista,batch);
            }
        });
        HUD.addActor(botonPausa);

        ImageButton botonDisparar = new ImageButton(new TextureRegionDrawable(new Texture("BotonesHUD/botonDisparar.png")));
        botonDisparar.setPosition(ANCHO-botonDisparar.getWidth(),0);
        botonDisparar.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (contadorBalas<3) {
                    float xBala = protagonista.sprite.getX() + protagonista.sprite.getWidth() - texturaBala.getWidth();
                    float yBala = protagonista.sprite.getY() + (2 * protagonista.sprite.getHeight() / 3) - texturaBala.getHeight() / 2f;
                    Bala bala = new Bala(texturaBala, xBala, yBala, 100f, 0f, 30f);
                    arrBalas.add(bala);
                    contadorBalas++;
                }
                /*
                if(contadorBalas < 3) {
                    for(int indexBalas = 0; indexBalas<arrBalas.size; indexBalas++) {
                        if(arrBalas.get(indexBalas)!=null) continue;
                        float xBala = protagonista.sprite.getX() + protagonista.sprite.getWidth() - texturaBala.getWidth();
                        float yBala = protagonista.sprite.getY() + (2 * protagonista.sprite.getHeight() / 3) - texturaBala.getHeight() / 2f;
                        Bala bala = new Bala(texturaBala, xBala, yBala, 25f, 0f, 30f);
                        arrBalas.set(indexBalas, bala);
                        break;
                    }
                }
                 */
            }
        });
        HUD.addActor(botonDisparar);
    }

    private void crearProtagonista(){
        protagonista = new Protagonista(new Texture("Principal/PersonajeNormal.png"), 60f, 250f, 1f, 30f, 30f);
    }

    private void crearEnemigos(){
        arrEnemigos = new Array<>(10);
        Enemigo enemigoUno = new Enemigo(new Texture("Enemigos/Enemigo.png"), 900f, 250f, 1f, 30f, 30f);
        //Enemigo enemigoDos = new Enemigo(new Texture("Enemigos/Enemigo.png"), 700f, 250f, 1f, 30f, 30f);
        enemigoUno.direccion=Enemigo.MovimientoEnemigos.IZQUIERDA;
        //enemigoDos.direccion=Enemigo.MovimientoEnemigos.IZQUIERDA;
        arrEnemigos.add(enemigoUno);
        //arrEnemigos.add(enemigoDos);
    }

    private void crearArrBalas(){
        contadorBalas = 0;
        arrBalas = new Array<>(3);
        System.out.println(arrBalas.toString());
    }

    private void cargarTexturaBala() {
        texturaBala = new Texture("Proyectiles/bala1.png");
    }

    @Override
    public void render(float delta) {

        borrarPantalla();

        //Dibujar
        batch.setProjectionMatrix(camara.combined);
        mapRenderer.setView(camara);
        mapRenderer.render();
        batch.begin();
        protagonista.render(batch);
        //render todos los enemigos
        for(Enemigo enemy: arrEnemigos){
            enemy.render(batch);
        }
        for(Bala bala: arrBalas){
            bala.render(batch);
        }
        batch.end();

        if(estadoJuego== EstadoJuego.JUGANDO){
            batch.setProjectionMatrix(orthographicCameraHUD.combined);
            HUD.draw();
            actualizar(delta);
            probarColisiones();
        }
        if(estadoJuego == EstadoJuego.PAUSADO){
            escenaPausa.draw();
            Gdx.input.setInputProcessor(escenaPausa);
        }
    }

    private void actualizar(float delta) {
        //Actualizaciones
        moverProtagonista();
        moverBala(delta);
        moverEnemigos();
    }

    private void moverProtagonista() {

        if(pad.isTouched()){
            if (pad.getKnobPercentX()>0) {
                if(protagonista.sprite.getX()<500) protagonista.moverX(protagonista.vx);
                else {
                    //vista.setScreenX((int)(vista.getScreenX()+protagonista.vx));
                    camara.translate(protagonista.vx,0);
                    camara.update();
                }
            }
            else if (pad.getKnobPercentX()<0) {
                if (protagonista.sprite.getX()>50)protagonista.moverX(-protagonista.vx);
                else {
                    camara.translate(-protagonista.vx,0);
                    camara.update();
                }
            }
        }
    }

    private void moverEnemigos(){
        for(Enemigo enemy: arrEnemigos){
            if (protagonista.sprite.getX()<=enemy.sprite.getX()) enemy.direccion=Enemigo.MovimientoEnemigos.IZQUIERDA;
            else enemy.direccion=Enemigo.MovimientoEnemigos.DERECHA;
        }
    }

    private void moverBala(float delta) {
        for(int indexBalas = 0; indexBalas < arrBalas.size; indexBalas++){
            if(arrBalas.get(indexBalas) == null) continue;
            Bala bala = arrBalas.get(indexBalas);
            bala.moverX(delta);
            //Salio??
            if(bala.sprite.getX() > ANCHO){
                arrBalas.removeIndex(indexBalas);
                contadorBalas--;
            }
        }

    }


    //Pureba si la bala le pegó a un enemigo
    private void probarColisiones() {
        for(int indexEnemigos = 0; indexEnemigos < arrEnemigos.size;indexEnemigos++) {
            if (arrEnemigos.get(indexEnemigos)==null) continue;
            Enemigo enemigo = arrEnemigos.get(indexEnemigos);
            Rectangle rectEnemigo = enemigo.sprite.getBoundingRectangle();
            for(int indexBala = 0; indexBala < arrBalas.size; indexBala++){
                if(arrBalas.get(indexBala)== null) continue;
                Bala bala = arrBalas.get(indexBala);
                if(rectEnemigo.overlaps(bala.sprite.getBoundingRectangle())){
                    enemigo.setVida(bala.getDanio());
                    if(enemigo.getVida()<=0)
                        arrEnemigos.removeIndex(indexEnemigos);
                    arrBalas.removeIndex(indexBala);
                    contadorBalas--;
                    return;
                }
            }
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
        texturaBala.dispose();
    }


    //Clase Pausa( Ventana que se muestra cuando el usuario pausa el juego
    class EscenaPausa extends Stage{
        public EscenaPausa(Viewport vista, SpriteBatch batch){
            super(vista, batch);
            ImageButton botonPlay = new ImageButton(new TextureRegionDrawable(new Texture("BotonesHUD/continuar.png")));
            botonPlay.setPosition(0, ALTO-botonPlay.getHeight());
            botonPlay.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    estadoJuego = EstadoJuego.JUGANDO;
                    escenaPausa = null;
                    Gdx.input.setInputProcessor(HUD);
                }
            });
            addActor(botonPlay);
        }
    }


    //Movimiento Personaje
    public enum Movimiento{
        DERECHA,
        IZQUIERDA,
        SALTO,
        SALTODERECHA,
        SALTOIZQUIERDA,
        QUIETO
    }
    //Movimiento enemigos


    private enum EstadoJuego {
        JUGANDO,
        PAUSADO,
        GANO,
        PERDIO
    }

}
