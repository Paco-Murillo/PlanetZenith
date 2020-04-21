package mx.jfml;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class AudioManejador {

    //Asset Manager
    public AssetManager manager;

    //Musica
    protected Music musicaFondo;

    //Efectos
    protected Sound efectoBtnMenu;

    public AudioManejador(AssetManager manager){
        this.manager = manager;
        musicaFondo = manager.get("Audio/Musica/superMetroid.mp3", Music.class);
        efectoBtnMenu = manager.get("Audio/Efectos/sonidoboton.mp3", Sound.class);
    }

    public void setMusicaFondo(String musica) {
        musicaFondo = manager.get(musica, Music.class);
    }

    public void setLooping(Boolean loop){
        musicaFondo.setLooping(loop);
    }

    public void playMusica(){
        musicaFondo.play();
    }

    public void stopMusica(){
        musicaFondo.stop();
    }

    public float getVolMusica(){
        return musicaFondo.getVolume();
    }

}
