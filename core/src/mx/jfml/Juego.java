package mx.jfml;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Juego extends Game {

	/**
	 * @value assetManager Objeto que permite cargar recursos
	 * @value audioManejador Controlador de audio
	 * @value seleccionaNivel Permite saber que nivel cargar
	 */
	private final AssetManager assetManager = new AssetManager();
	private AudioManejador audioManejador = new AudioManejador(false, 1f,1f);
	private SeleccionaNivel seleccionaNivel;

	@Override
	public void create(){

		//Desde aquí avisamos que podrá cargar Mapas
		assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));

		//Pantalla inicial
		setScreen(new PantallaMenu(this));
	}

	//Accesor del AssetManager, para que otras clases lo utilicen
	public AssetManager getAssetManager(){ return assetManager;	}

	public AudioManejador getAudioManejador(){ return audioManejador;}

	public SeleccionaNivel getSeleccionaNivel(){ return seleccionaNivel; }

	public void setSeleccionaNivel(SeleccionaNivel seleccionaNivel){
		this.seleccionaNivel = seleccionaNivel;
	}

	@Override
	public void dispose(){
		super.dispose();
		assetManager.clear(); //Esto ocurrira al final de la aplicacion
	}
}
