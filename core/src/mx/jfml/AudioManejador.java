package mx.jfml;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class AudioManejador {

    private float volEfectos;
    private float volMusica;
    private boolean tocando;

    /**
     * La clase AudioManejador nos pemite tener un controlador de sonidos, incluyendo las variables
     * volumen tanto de los efectos como de la música asi como una bandera "tocando" que permite
     * saber si se esta reproduciendo algun sonido
     * @param tocando Bandera para saber si se esta reproduciendo un sonido
     * @param volEfectos Volumen de 0 a 1 inclusivo para los efectos
     * @param volMusica Volumen de 0 a 1 inclusivo para la musica
     */
    public AudioManejador(boolean tocando,float volEfectos, float volMusica){
        this.tocando = tocando;
        this.volEfectos = volEfectos;
        this.volMusica = volMusica;
    }

    public boolean getTocando(){
        return tocando;
    }

    public float getVolEfectos() {
        return volEfectos;
    }

    public float getVolMusica(){
        return volMusica;
    }

    public void setTocando(boolean tocando){
        this.tocando = tocando;
    }

    public void setVolEfectos(float volumen){
        volEfectos =  volumen;
    }

    public void setVolMusica(float volMusica){
        this.volMusica = volMusica;
    }
}
