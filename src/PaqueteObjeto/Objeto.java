package PaqueteObjeto;

import java.awt.Image;
import java.io.IOException;

public class Objeto {
	private int posX;
	private int posY;
	private Image imagen;
	private InterfazObjeto tipoObjeto;
	
	public Objeto(InterfazObjeto tipo, int x, int y) throws IOException{
		this.tipoObjeto = tipo;
		this.tipoObjeto.setImagen(this);
		posX = x;
		posY = y;
	}
	
	public void setImagenes(Image image){
		imagen = image;
	}
	
	//Metodos Getter
	public int getPosX(){
		return posX;
	}
	
	public int getPosY(){
		return posY;
	}
	
	public InterfazObjeto getTipoObjeto(){
		return tipoObjeto;
	}
	
	public Image getImagen(){
		return imagen;
	}
}

