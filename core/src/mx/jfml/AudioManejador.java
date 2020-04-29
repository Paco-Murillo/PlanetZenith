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
    public  float volEfectos = 1f;

    //Variable que dice si la musica se esta reproduciendo actualmente
    public static boolean tocando;

    public AudioManejador(AssetManager manager){
        this.tocando = false;

        this.manager = manager;
        manager.load("Audio/Musica/superMetroid.mp3", Music.class);
        manager.load("Audio/Efectos/sonidoboton.mp3", Sound.class);
        manager.finishLoading();

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

    public void setVolMusica(float volumen){
        musicaFondo.setVolume(volumen);
    }

    public void setVolEfectos(float volumen){
        volEfectos =  volumen;
    }

    public float getVolEfectos() {
        return volEfectos;
    }

    public void setTocando(boolean tocando){
        this.tocando = tocando;
    }

}
