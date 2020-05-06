package mx.jfml;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Texto {

	private BitmapFont font;

	/**
	 * Clase que permite poner mensajes en la pantalla
	 * @param archivo Path al archivo .fnt
	 */
	public Texto(String archivo){
		font = new BitmapFont(Gdx.files.internal((archivo))); //Archivo fnt
	}

	public void render(SpriteBatch batch, String mensaje, float x, float y) {
		GlyphLayout glyph = new GlyphLayout();
		glyph.setText(font, mensaje);
		float anchoTexto = glyph.width;
		font.draw(batch, glyph, x-anchoTexto/2, y);
	}
}
