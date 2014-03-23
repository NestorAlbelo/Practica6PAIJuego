package PaqueteObjeto;

import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import VentanaJuego.Ventana;

public class ObjetoGema implements InterfazObjeto {

	@Override
	public void setImagen(Objeto obj) throws IOException {
		Image img = ImageIO.read(Ventana.class.getResourceAsStream("/Imagenes/Gema.png"));
		obj.setImagenes(img);
	}
	

}
