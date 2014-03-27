package PaquetePersonaje;

import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import VentanaJuego.Ventana;

public class EnemigoNieve implements InterfazPersonaje {
	private static final int ARRIBA = 0;
	private static final int ABAJO= 1;
	private static final int IZQ = 2;
	private static final int DER = 3;
	private static final int QUIETO = -1;
	
	@Override
	public void cambiarPoder(Personaje p) {
		p.setAtaque(PODER_ENEMIGO_NIEVE);			
	}

	@Override
	public void cambiarImagenes(Personaje p) throws IOException {
		Image Vivo = ImageIO.read(Ventana.class.getResourceAsStream("/Imagenes/EnemigoNieveVivo.png"));
		Image Muerto = ImageIO.read(Ventana.class.getResourceAsStream("/Imagenes/EnemigoNieveMuerto.png"));
		p.setImagenes(Vivo, Muerto);
	}
	
	@Override
	public void mover(Personaje p) {
		if(!p.muerto()){
			p.fisica(desplazamientoNecesario(p, p.getJuego().getHeroe()));
			p.atacar();
		}
	}
	
	private int  desplazamientoNecesario(Personaje actual, Personaje objetivo){
		if(actual.getPosX() > ( objetivo.getPosX() + (3*Ventana.getDiametroImagen()/4) )){				//Si esta mas a la derecha que el heroe
			return IZQ;
		}
		else if ((actual.getPosX()+Ventana.getDiametroImagen()) < (objetivo.getPosX() + Ventana.getDiametroImagen()/4)){			//Si esta mas a la izquierda que el heroe
			return DER;
		}	
		if(actual.getPosY() > ( objetivo.getPosY() + (3*Ventana.getDiametroImagen()/4) )){				//Si esta mas abajo que el heroe
			return ARRIBA;
		}
		else if ((actual.getPosY()+Ventana.getDiametroImagen()) < (objetivo.getPosY() + Ventana.getDiametroImagen()/4)){			//Si esta mas arriba que el heroe
			return ABAJO;
		}
		return QUIETO;
	}
}
