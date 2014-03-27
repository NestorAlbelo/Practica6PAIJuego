package PaquetePersonaje;

import java.awt.Image;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import VentanaJuego.Ventana;

public class LocoNieve implements InterfazPersonaje {

	Random random = new Random();
	
	@Override
	public void cambiarPoder(Personaje p) {
		p.setAtaque(PODER_LOCO_NIEVE);
	}

	@Override
	public void cambiarImagenes(Personaje p) throws IOException {
		Image img = ImageIO.read(Ventana.class.getResourceAsStream("/Imagenes/LocoNieve.png"));
		p.setImagenes(img, img);
	}

	@Override
	public void mover(Personaje p) {
		p.fisica(random.nextInt(5)-1);
	}

}
