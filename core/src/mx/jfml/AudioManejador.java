package mx.jfml;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class AudioManejador {


    //Volumen
    public  float volEfectos;
    public float volMusica;

    //Variable que dice si la musica se esta reproduciendo actualmente
    public boolean tocando;

    public AudioManejador(boolean tocando,float volEfectos, float volMusica){
        this.tocando = tocando;
        this.volEfectos = volEfectos;
        this.volMusica = volMusica;
    }

    public float getVolMusica(){
        return volMusica;
    }

    public void setVolMusica(float volMusica){
        this.volMusica = volMusica;
    }

    public void setVolEfectos(float volumen){
        volEfectos =  volumen;
    }

    public float getVolEfectos() {
        return volEfectos;
    }

    public boolean getTocando(){
        return tocando;
    }

    public void setTocando(boolean tocando){
        this.tocando = tocando;
    }


}
