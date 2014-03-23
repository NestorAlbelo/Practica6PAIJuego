package PaquetePersonaje;

import java.awt.Image;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;

import VentanaJuego.Ventana;
import PaqueteObjeto.*;

public class HeroeCueva implements InterfazPersonaje {

	@Override
	public void cambiarPoder(Personaje p) {
		p.setAtaque(PODER_HEROE_CUEVA);	
	}

	@Override
	public void cambiarImagenes(Personaje p) throws IOException {
		Image Vivo = ImageIO.read(Ventana.class.getResourceAsStream("/Imagenes/HeroeCuevaVivo.png"));
		Image Muerto = ImageIO.read(Ventana.class.getResourceAsStream("/Imagenes/HeroeCuevaMuerto.png"));
		p.setImagenes(Vivo, Muerto);
	}

	@Override
	public void mover(Personaje p) {
		p.fisica(-1);		
		takeObject(p);
		p.atacar();
	}
	
	private void takeObject(Personaje p){
		Objeto obj;
		int Diametro = Ventana.getDiametroImagen();
		for (int i=0; i < p.getJuego().getObjetos().size(); i++){
			obj = p.getJuego().getObjetos().get(i);
			if( (obj.getTipoObjeto().getClass() != (new ObjetoRoca().getClass()))  &&  ( ( (p.getPosX() >= obj.getPosX()) && (p.getPosX() <= ( obj.getPosX() + Diametro)) )  &&  (  (p.getPosY() >= obj.getPosY())  && (p.getPosY() <= (obj.getPosY() +Diametro ))   ) ) ){
				if(obj.getTipoObjeto().getClass() == (new ObjetoEscudo().getClass())){
					p.cogerObjeto("Escudo");
					p.getJuego().cogerObjeto(obj);
				}
				else if(obj.getTipoObjeto().getClass() == (new ObjetoEspada().getClass())){
					p.cogerObjeto("Espada");
					p.getJuego().cogerObjeto(obj);
				}
				else if(obj.getTipoObjeto().getClass() == (new ObjetoPocion().getClass())){
					p.cogerObjeto("Pocion");
					p.getJuego().cogerObjeto(obj);
				}
				else if(obj.getTipoObjeto().getClass() == (new ObjetoGema().getClass())){
					p.cogerObjeto("Gema");
					p.getJuego().cogerObjeto(obj);
				}
			}	
		}	 
	}
}


