package mx.jfml;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Random;

public abstract class Nivel extends Pantalla {
    protected final Juego juego;

    //Mundo
    protected World mundo; //Simulacion
    private Box2DDebugRenderer debugRenderer;
    private Contacto contacto;
    protected Vector2 gravedad;

    //Mapa
    protected TiledMap mapa;
    private OrthogonalTiledMapRenderer mapRenderer;

    //Personaje
    protected Protagonista protagonista;

    //Pausa
    private EscenaPausa escenaPausa;
    protected EstadoJuego estadoJuego = EstadoJuego.JUGANDO; //Jugando, PAusado, Poner DEBUG en caso de checar nivel sin actualizaciones

    //AssetManager para la musica y otras cosas
    private AssetManager assetManager;

    //El manejador de audio para controlar el volumen
    private AudioManejador audioManejador;

    /*
    EstadoJuego.DEBUG les permite moverse rapidamente a traves del mapa para checar cosas,
    No actualiza posiciones de animacion (gravedad)
    ---- Importante ----
    Para no salirse de EstadoJuego.DEBUG, NO presionar el boton de pausa
     */

    //Texto para marcador del juego
    private int puntosJugador = 0;
    private Texto textoMarcador;

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
    protected Array<Bala> arrBalasEnemigos;
    protected Texture texturaBalaEnemigos;
    private float timeAcumForEnemyShots;
    private Random random;

    //Jefe
    protected Jefe jefe;
    private ShapeRenderer shapeRenderer;

    //Musica y Efectos
    protected Music musicaNivelUno;
    protected Music musicaNivelDos;
    protected Music musicaNivelTres;

    //Selector de nivel dice que musica poner mientras esta el nivel
    private  SeleccionaNivel seleccionaNivel;

    /**
     * Clase abstracta que permite representar los fundamentos de cada nivel
     * @param juego Referencia al objeto que creo la pantalla
     */
    public Nivel(Juego juego) {
        this.juego = juego;
        assetManager = juego.getAssetManager();
        audioManejador = juego.getAudioManejador();
        seleccionaNivel = juego.getSeleccionaNivel();
    }

    @Override
    public void show(){
        crearProtagonista("Principal/PersonajeNormalFinal.png");
        cargarTexturaBalaEnemigos("Proyectiles/balaenemigo.png");
        cargarAssets();
        cargarMusica();
        crearTextoMarcador();
        crearArrBalas();
        crearHUD();
        crearContacto();
        crearShapeRenderer();
        Gdx.input.setCatchKey(Input.Keys.BACK, true);
    }

    private void cargarMusica() throws GdxRuntimeException {
        switch(seleccionaNivel){
            case NIVELUNO:
                try{
                    musicaNivelUno.setLooping(true);
                    musicaNivelUno.setVolume(audioManejador.getVolMusica());
                    musicaNivelUno.play();

                } catch (GdxRuntimeException e){
                    recargarMusisca(musicaNivelUno, "Audio/Musica/nivelUno.mp3");

                    musicaNivelUno.setLooping(true);
                    musicaNivelUno.setVolume(audioManejador.getVolMusica());
                    musicaNivelUno.play();

                }
                break;
            case NIVELDOS:
                try{
                    musicaNivelDos.setLooping(true);
                    musicaNivelDos.setVolume(audioManejador.getVolMusica());
                    musicaNivelDos.play();


                } catch (GdxRuntimeException e){
                    recargarMusisca(musicaNivelDos, "Audio/Musica/nivelDos.wav");

                    musicaNivelDos.setLooping(true);
                    musicaNivelDos.setVolume(audioManejador.getVolMusica());
                    musicaNivelDos.play();

                }
            case NIVELTRES:
                try{
                    musicaNivelTres.setLooping(true);
                    musicaNivelTres.setVolume(audioManejador.getVolMusica());
                    musicaNivelTres.play();
                }catch (GdxRuntimeException e){
                    recargarMusisca(musicaNivelTres, "Audio/Musica/nivelTres.mp3");

                    musicaNivelTres.setLooping(true);
                    musicaNivelTres.setVolume(audioManejador.getVolMusica());
                    musicaNivelTres.play();
                }
            default:
                break;
        }
    }
    private void recargarMusisca(Music musica, String dirMusica){
        musica.dispose();
        assetManager.unload(dirMusica);

        assetManager.load(dirMusica, Music.class);

        assetManager.finishLoading();

        musica = assetManager.get(dirMusica);
    }

    private void cargarAssets() {
        assetManager.load("Audio/Musica/nivelUno.mp3", Music.class);
        assetManager.load("Audio/Musica/nivelDos.wav", Music.class);
        assetManager.load("Audio/Musica/nivelTres.mp3", Music.class);

        assetManager.finishLoading();

        musicaNivelUno = assetManager.get("Audio/Musica/nivelUno.mp3");
        musicaNivelDos = assetManager.get("Audio/Musica/nivelDos.wav");
        musicaNivelTres = assetManager.get("Audio/Musica/nivelTres.mp3");
    }

    /*
     * Metodos llamados por los metodos show() de cada nivel
     */

    protected void crearGravedad(){
        gravedad = new Vector2(0,-125);
    }

    /**
     * Crea el encargado de las animaciones
     * @param gravedad Vector de 2 dimensiones que representa la fuerza que se aplica en 'x' y 'y'
     */
    protected void crearMundo(Vector2 gravedad) {
        Box2D.init();
        mundo = new World(gravedad, true);
        debugRenderer = new Box2DDebugRenderer();
    }

    /*
     * Metodos llamados por el metodo show() de Nivel
     */

    /**
     * Crea el objeto Protagonista de cada nivel
     * @param imgPath Path de la imagen
     */
    private void crearProtagonista(String imgPath){
        protagonista = new Protagonista(new Texture(imgPath), 220, 100, 1f, 30f, 4000,mundo);
    }

    /**
     * Crea la textura de las balas de los enemigos
     * @param imgPath Path de la imagen
     */
    private void cargarTexturaBalaEnemigos(String imgPath) {
        texturaBalaEnemigos = new Texture(imgPath);
    }

    private void crearTextoMarcador(){
        textoMarcador = new Texto("Texto/FuenteCuadro.fnt");
    }

    private void crearArrBalas(){
        contadorBalas = 0;
        arrBalas = new Array<>(3);
        timeAcumForEnemyShots = 0;
        arrBalasEnemigos = new Array<>();
        //System.out.println(arrBalas.toString());
        random = new Random();
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
        botonDisparar.setPosition(ANCHO-botonDisparar.getWidth()-30,135);
        botonDisparar.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (contadorBalas<3) {
                    if(protagonista.getMovimiento() == Personaje.Movimientos.DERECHA) {
                        float xBala = protagonista.sprite.getX() + protagonista.sprite.getWidth() - texturaBala.getWidth();
                        float yBala = protagonista.sprite.getY() + (2 * protagonista.sprite.getHeight() / 3) - texturaBala.getHeight() / 2f;
                        Bala bala = new Bala(texturaBala, xBala, yBala, 500f, 0f, 30f);
                        arrBalas.add(bala);
                    }else if(protagonista.getMovimiento() == Personaje.Movimientos.IZQUIERDA){
                        float xBala = protagonista.sprite.getX();
                        float yBala = protagonista.sprite.getY() + (2 * protagonista.sprite.getHeight() / 3) - texturaBala.getHeight() / 2f;
                        Bala bala = new Bala(texturaBala, xBala, yBala, -500f, 0f, 30f);
                        arrBalas.add(bala);
                    }
                    contadorBalas++;
                }
            }
        });
        HUD.addActor(botonDisparar);

        ImageButton botonSaltar = new ImageButton(new TextureRegionDrawable(new Texture("BotonesHUD/botonSaltar.png")));
        botonSaltar.setPosition(ANCHO-botonDisparar.getWidth()-150,25);
        botonSaltar.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if(contacto.isPersonajeSuelo()) {
                    protagonista.body.setGravityScale(16/49f);
                    protagonista.body.applyForceToCenter(0, 25000, true);
                }
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
        pad.setBounds(16,16,190,190);
        pad.setColor(1,1,1,0.7f);
        HUD.addActor(pad);
    }

    private void crearContacto() {
        contacto = new Contacto(false);
        mundo.setContactListener(contacto);
    }

    private void crearShapeRenderer() {
        shapeRenderer = new ShapeRenderer();
    }

    /*
     * Metodos llamados por los metodos show() de cada nivel
     */

    /**
     * Crea mapa y su renderer
     * @param mapPath Path al archivo .tmx del nivel
     */
    protected void cargaMapa(String mapPath) {
        AssetManager manager = new AssetManager();
        manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        manager.load(mapPath, TiledMap.class);
        manager.finishLoading();

        mapa = manager.get(mapPath);
        mapRenderer = new OrthogonalTiledMapRenderer(mapa);
    }

    /**
     * Crea la textura de las balas del Protagonista
     * @param imgPath Path de la imagen
     */
    protected void cargarTexturaBala(String imgPath) {
        texturaBala = new Texture(imgPath);
    }

    protected void definirParedes(){
        CargarMapa.crearCuerpos(mapa,mundo);
    }

    @Override
    public void render(float delta) {
        borrarPantalla();

        //System.out.println(contacto.personajeSuelo);

        //Condiciones para que pierda el jugador
        if(protagonista.sprite.getY()+protagonista.sprite.getHeight()<-20 || protagonista.getVida()<=0) {
            //Aqui deberia saltar a PantallaPerder
            detenerMusica();
            juego.setScreen(new PantallaPerder(juego));
        }

        float x = protagonista.body.getPosition().x - protagonista.sprite.getWidth()/2;
        float y = protagonista.body.getPosition().y - protagonista.sprite.getHeight()/2;
        protagonista.sprite.setPosition(x, y);

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
        for (Bala bala :
                arrBalasEnemigos){
            bala.render(batch);
        }
        jefe.render(batch);
        // jefe.sprite.setPosition(jefe.body.getPosition().x - jefe.sprite.getWidth()/2, jefe.body.getPosition().y - jefe.sprite.getHeight()/2);
        batch.end();

        actualizarBarraJefe();

        if(estadoJuego== EstadoJuego.JUGANDO){
            batch.setProjectionMatrix(orthographicCameraHUD.combined);
            HUD.draw();
            //Texto para el marcador, por ahora muestra los enemigos asesinados * 100
            batch.begin();
            textoMarcador.render(batch, " Vida: ", 200, 700);
            textoMarcador.render(batch, Float.toString(protagonista.getVida()), 300, 700);
            textoMarcador.render(batch, " Puntos: ", 460, 700);
            textoMarcador.render(batch, Integer.toString(puntosJugador), 560, 700);
            batch.end();
            actualizar(delta);
            dispararEnemigos(delta);
            probarColisiones();
            probarColisionesProtagonista();
            mundo.step(delta, 6, 2);  //1/60f, 6, 2);
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
        if(Gdx.input.isKeyPressed(Input.Keys.BACK)){
            estadoJuego = EstadoJuego.PAUSADO;
            escenaPausa = new EscenaPausa(viewportHUD,batch);
            Gdx.input.setInputProcessor(escenaPausa);
        }
    }

    /**
     * Actualiza el tamanio de la barra de vida del Jefe del nivel
     */
    private void actualizarBarraJefe() {
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(jefe.sprite.getX(), jefe.sprite.getY()+jefe.sprite.getHeight()+20, jefe.sprite.getWidth(), 10);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(jefe.sprite.getX(), jefe.sprite.getY()+jefe.sprite.getHeight()+20, (jefe.sprite.getWidth()*jefe.getVida())/jefe.getVidaOriginal(), 10);
        shapeRenderer.end();
    }



    protected void checarFinal(Jefe jefe) {
        if(jefe.getVida()<=0){
            detenerMusica();
            juego.setScreen(new PantallaGanar(juego));
        }
    }

    /**
     * Hace una actualizacion a aquello que se debe mover el la pantalla
     * @param delta Tiempo que a pasado desde la ejecucion anterior
     */

    private void actualizar(float delta) {
        //Actualizaciones
        actualizarCamara();
        moverBala(delta);
        moverBalasEnemigos(delta);
        moverProtagonista();
        moverEnemigos();
    }

    protected abstract void actualizarCamara();

    protected abstract void moverBala(float delta);

    /**
     * Actualiza la posicion de las balas de los enemigos
     * @param delta Tiempo que a pasado desde la ejecucion anterior
     */
    private void moverBalasEnemigos(float delta) {
        for (Bala bala : arrBalasEnemigos) {
            bala.moverX(delta);
        }
    }

    /**
     * Mueve al Protagonista haciendo uso del input Pad que pertenece al HUD
     */
    private void moverProtagonista() {
        if(pad.isTouched()){
            float percentX = pad.getKnobPercentX();
            if(percentX >= 0){
                protagonista.setMovimiento(Personaje.Movimientos.DERECHA);
                protagonista.sprite.setFlip(false,false);
            }else{
                protagonista.setMovimiento(Personaje.Movimientos.IZQUIERDA);
                protagonista.sprite.setFlip(true,false);
            }
            if(protagonista.body.getLinearVelocity().y > 0) {
                protagonista.body.applyForceToCenter(percentX * 4000, 0, true);
            }
            else {
                protagonista.body.applyForceToCenter(percentX * 4000, -1000*Math.abs(percentX), true);
            }
        }
    }

    /**
     * Mueve a los Enemigos contenidos en arrEnemigos hacia el personaje
     */
    private void moverEnemigos(){
        for(Enemigo enemy: arrEnemigos) {
            if (protagonista.sprite.getX() <= enemy.sprite.getX())
                enemy.setMovimiento(Personaje.Movimientos.IZQUIERDA);
            else enemy.setMovimiento(Personaje.Movimientos.DERECHA);

            if (enemy.movimiento == Personaje.Movimientos.IZQUIERDA) {
                enemy.sprite.setFlip(false, false);
                if(enemy.tipoEnemigo== Enemigo.TipoEnemigo.CAMINANTE) {
                    if (enemy.body.isAwake() && enemy.sprite.getX() - protagonista.sprite.getX() > 300) {
                        enemy.body.applyForceToCenter(-300, 0, true);
                    }
                }
            }

            if (enemy.movimiento == Personaje.Movimientos.DERECHA) {
                enemy.sprite.setFlip(true, false);
                if (enemy.tipoEnemigo == Enemigo.TipoEnemigo.CAMINANTE){
                    if (enemy.body.isAwake() && enemy.sprite.getX() - protagonista.sprite.getX() > -300) {
                        enemy.body.applyForceToCenter(300, 0, true);
                    }
                }
            }








        }
    }

    /**
     * Comportamiento de disparo de los enemigos hacia el personaje (Bidireccional)
     */
    private void dispararEnemigos(float delta) {
        timeAcumForEnemyShots += delta;
        if(timeAcumForEnemyShots > 3){
            for (Enemigo enemigo : arrEnemigos) {
                if (camara.position.x-ANCHO/2 < enemigo.sprite.getX() && enemigo.sprite.getX() <camara.position.x+ANCHO && enemigo.getTiempoDisparos()<=0){
                    if(enemigo.getMovimiento() == Personaje.Movimientos.DERECHA) {
                        float xBala = enemigo.sprite.getX() + enemigo.sprite.getWidth() - texturaBalaEnemigos.getWidth();
                        float yBala = enemigo.sprite.getY() + (2 * enemigo.sprite.getHeight() / 3) - texturaBalaEnemigos.getHeight() / 2f;
                        enemigo.setTiempoDisparos(random.nextInt(2));
                        Bala bala = new Bala(texturaBalaEnemigos, xBala, yBala, 100f, 0f, 20f);
                        arrBalasEnemigos.add(bala);
                    }else if(enemigo.getMovimiento() == Personaje.Movimientos.IZQUIERDA){
                        float xBala = enemigo.sprite.getX();
                        float yBala = enemigo.sprite.getY() + (2 * enemigo.sprite.getHeight() / 3) - texturaBalaEnemigos.getHeight() / 2f;
                        enemigo.setTiempoDisparos(random.nextInt(2));
                        Bala bala = new Bala(texturaBalaEnemigos, xBala, yBala, -100f, 0f, 20f);
                        arrBalasEnemigos.add(bala);
                    }
                }else {
                    enemigo.setTiempoDisparos(enemigo.getTiempoDisparos() - 1);
                }
            }
            timeAcumForEnemyShots = 0;
        }
    }

    //Prueba si la bala le pegó a un enemigo

    //Falta implementar el choque del personaje con Enemigo

    /**
     * Prueba las colisiones reductoras de vida de los enemigos
     */
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
                    puntosJugador += 100;
                    contadorBalas--;
                    return;
                }
            }
        }
    }

    /**
     * Prueba las colisiones reductoras de vida del Protagonista
     */
    private void probarColisionesProtagonista() {
        Rectangle rectProtagonista = protagonista.sprite.getBoundingRectangle();
        //System.out.println(arrBalasEnemigos.size);
        for(int indexBala= 0; indexBala < arrBalasEnemigos.size; indexBala++) {
            if (arrBalasEnemigos.get(indexBala) == null) continue;
            Bala bala = arrBalasEnemigos.get(indexBala);
            if (rectProtagonista.overlaps(bala.sprite.getBoundingRectangle())) {
                protagonista.setVida(bala.getDanio());
                arrBalasEnemigos.removeIndex(indexBala);
                return;
            }
        }
    }

    private void debugMoverCamara() {
        if (pad.isTouched()){
            camara.position.set(camara.position.x+pad.getKnobPercentX()*50,camara.position.y,0);
            camara.update();
        }
    }

    protected void detenerMusica(){
        if(musicaNivelUno.isPlaying()){
            musicaNivelUno.stop();
            musicaNivelUno.dispose();
        } else if (musicaNivelDos.isPlaying()){
            musicaNivelDos.stop();
            musicaNivelUno.dispose();
        } else if(musicaNivelTres.isPlaying()){
            musicaNivelTres.stop();
            musicaNivelTres.dispose();
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

            ImageButton botonNiveles = new ImageButton(new TextureRegionDrawable(new Texture("BotonesHUD/botonNivel.png")));
            botonNiveles.setPosition(ANCHO-botonNiveles.getWidth()-15,ALTO-botonNiveles.getHeight());
            botonNiveles.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y){
                    super.clicked(event,x,y);
                    detenerMusica();
                    audioManejador.setTocando(false);
                    juego.setScreen(new PantallaSelecNivel(juego));
                }
            });
            addActor(botonNiveles);

            ImageButton botonHome = new ImageButton(new TextureRegionDrawable(new Texture("BotonesHUD/botonMenu.png")));
            botonHome.setPosition(ANCHO-(2*(botonHome.getWidth()+15)),ALTO-botonHome.getHeight());
            botonHome.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y){
                    super.clicked(event,x,y);
                    detenerMusica();
                    audioManejador.setTocando(false);
                    juego.setScreen(new PantallaMenu(juego));
                }
            });
            addActor(botonHome);
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