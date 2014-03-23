package PaqueteJuego;

import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import VentanaJuego.Ventana;

public class JuegoNieve implements InterfazJuego {

	@Override
	public void setImagen(Juego game) throws IOException {
		Image fondo = ImageIO.read(Ventana.class.getResourceAsStream("/Imagenes/Nieve.png"));
		game.setImagen(fondo);
	}	

}
