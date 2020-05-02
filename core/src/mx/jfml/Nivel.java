package mx.jfml;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public abstract class Nivel extends Pantalla {
    protected final Juego juego;

    //Mundo
    protected World mundo; //Simulacion
    private Box2DDebugRenderer debugRenderer;

    //Mapa
    private TiledMap mapa;
    private OrthogonalTiledMapRenderer mapRenderer;

    //Personaje
    protected Protagonista protagonista;
    protected Movimiento movimiento = Movimiento.QUIETO;

    //Pausa
    private EscenaPausa escenaPausa;
    private EstadoJuego estadoJuego = EstadoJuego.JUGANDO; //Jugando, PAusado, Poner DEBUG en caso de checar nivel sin actualizaciones

    /*
    EstadoJuego.DEBUG les permite moverse rapidamente a traves del mapa para checar cosas,
    No actualiza posiciones de animacion (gravedad)
    ---- Importante ----
    Para no salirse de EstadoJuego.DEBUG, NO presionar el boton de pausa
     */

    //Bala
    protected Array<Bala> arrBalas;
    protected Texture texturaBala;
    protected int contadorBalas;

    //HUD
    protected Stage HUD;
    private OrthographicCamera orthographicCameraHUD;
    private Viewport viewportHUD;
    private Touchpad pad;

    //Enemigos
    protected Array<Enemigo> arrEnemigos;

    public Nivel(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show(){
        crearMundo();
        crearProtagonista("Principal/PersonajeNormalFinal.png");
        crearArrBalas();
        crearHUD();
    }

    @Override
    public void render(float delta) {
        borrarPantalla();

        if(protagonista.sprite.getY()+protagonista.sprite.getHeight()<-20){
            //Aqui deberia saltar a PantallaPerder
            System.out.println("Te caiste");
        }


        float x = protagonista.body.getPosition().x - protagonista.sprite.getWidth()/2;
        float y = protagonista.body.getPosition().y - protagonista.sprite.getHeight()/2;
        protagonista.sprite.setPosition(x,y);

        //Dibujar
        batch.setProjectionMatrix(camara.combined);
        mapRenderer.setView(camara);
        mapRenderer.render();

        debugRenderer.render(mundo, camara.combined);

        batch.begin();
        protagonista.render(batch);
        //render todos los enemigos
        for(Enemigo enemy: arrEnemigos){
            enemy.render(batch);
            float enemyX = enemy.body.getPosition().x - enemy.sprite.getWidth()/2;
            float enemyY = enemy.body.getPosition().y - enemy.sprite.getHeight()/2;
            enemy.sprite.setPosition(enemyX,enemyY);
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
            mundo.step(1/60f, 6, 2);
        }
        else if(estadoJuego == EstadoJuego.PAUSADO){
            batch.setProjectionMatrix(orthographicCameraHUD.combined); //Probar sin esto
            escenaPausa.draw();
        }
        else if (estadoJuego == EstadoJuego.DEBUG){
            //Solo para debugging, se quitará al final
            batch.setProjectionMatrix(orthographicCameraHUD.combined);
            HUD.draw();
            debugMoverCamara();
        }
    }

    private void debugMoverCamara() {
        if (pad.isTouched()){
            camara.position.set(camara.position.x+pad.getKnobPercentX()*50,camara.position.y,0);
            camara.update();
        }
    }

    protected abstract void actualizarCamara();

    protected void cargaMapa(String mapPath) {
        AssetManager manager = new AssetManager();
        manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        manager.load(mapPath, TiledMap.class);
        manager.finishLoading();

        mapa = manager.get(mapPath);
        mapRenderer = new OrthogonalTiledMapRenderer(mapa);
    }

    private void crearProtagonista(String imgPath){
        protagonista = new Protagonista(new Texture(imgPath), 60f, 250f, 1f, 30f, 100,mundo);
    }

    private void crearArrBalas(){
        contadorBalas = 0;
        arrBalas = new Array<>(3);
        System.out.println(arrBalas.toString());
    }

    protected void cargarTexturaBala(String imgPath) {
        texturaBala = new Texture(imgPath);
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

    private void crearMundo() {
        Box2D.init();
        Vector2 gravedad = new Vector2(0, -165);
        mundo = new World(gravedad, true);
        debugRenderer = new Box2DDebugRenderer();
    }

    private void crearBotones() {
        ImageButton botonPausa = new ImageButton(new TextureRegionDrawable(new Texture("BotonesHUD/pausa.png")));
        botonPausa.setPosition(0,ALTO-botonPausa.getHeight());
        botonPausa.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                estadoJuego = EstadoJuego.PAUSADO;
                escenaPausa = new EscenaPausa(viewportHUD,batch);
                Gdx.input.setInputProcessor(escenaPausa);
            }
        });
        HUD.addActor(botonPausa);

        ImageButton botonDisparar = new ImageButton(new TextureRegionDrawable(new Texture("BotonesHUD/botonDisparar.png")));
        botonDisparar.setPosition(ANCHO-botonDisparar.getWidth()-30,140);
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
            }
        });
        HUD.addActor(botonDisparar);

        ImageButton botonSaltar = new ImageButton(new TextureRegionDrawable(new Texture("BotonesHUD/botonSaltar.png")));
        botonSaltar.setPosition(ANCHO-botonDisparar.getWidth()-210,30);
        botonSaltar.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                protagonista.body.applyForceToCenter(0,3000,true);
            }
        });
        HUD.addActor(botonSaltar);
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
        HUD.addActor(pad);
    }

    private void actualizar(float delta) {
        //Actualizaciones
        actualizarCamara();
        moverProtagonista();
        moverBala();
        moverEnemigos();
    }

    protected void definirParedes(){
        cargarMapa.crearCuerpos(mapa,mundo);
    }

    private void moverProtagonista() {
        if(pad.isTouched()){
            float percentX = pad.getKnobPercentX();
            protagonista.body.applyForceToCenter(percentX*4000,0, true);
        }
    }

    protected abstract void moverBala();

    private void moverEnemigos(){
        for(Enemigo enemy: arrEnemigos){
            if (protagonista.sprite.getX()<=enemy.sprite.getX()) enemy.direccion= Personaje.Movimientos.IZQUIERDA;
            else enemy.direccion= Personaje.Movimientos.DERECHA;
        }
    }

    //Pureba si la bala le pegó a un enemigo

    //Falta implementar el choque del personaje con Enemigo
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
                    if(enemigo.getVida()<=0) {
                        mundo.destroyBody(enemigo.body);
                        arrEnemigos.removeIndex(indexEnemigos);
                    }

                    arrBalas.removeIndex(indexBala);
                    contadorBalas--;
                    return;
                }
            }
        }
    }

    //Clase Pausa( Ventana que se muestra cuando el usuario pausa el juego
    private class EscenaPausa extends Stage{
        private EscenaPausa(Viewport vista, SpriteBatch batch){
            super(vista, batch);
            ImageButton botonPlay = new ImageButton(new TextureRegionDrawable(new Texture("BotonesHUD/continuar.png")));
            botonPlay.setPosition(0, ALTO-botonPlay.getHeight());
            botonPlay.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    estadoJuego = PantallaJuegoNivelUno.EstadoJuego.JUGANDO;
                    escenaPausa = null;
                    Gdx.input.setInputProcessor(HUD);
                }
            });
            addActor(botonPlay);
        }
    }

    protected enum EstadoJuego {
        JUGANDO,
        PAUSADO,
        GANO,
        PERDIO,
        DEBUG
    }

}