package PaqueteJuego;

import java.awt.Image;
import java.io.IOException;
import java.util.Random;
import java.util.Vector;

import javax.swing.SwingUtilities;

import PaqueteObjeto.*;
import PaquetePersonaje.*;
import VentanaJuego.Ventana;


public class Juego {
	private Ventana vent;
	private final int posXInicio = 0;
	private final int posYInicio = 0;
	private static int GAMEOVER = -1;
	private static int WIN = 1;
	private static int CONTINUAR = 0;
	private InterfazJuego tipoJuego;
	private Image imagenFondo;
	private static Vector <Objeto> listaObjetos = new Vector <Objeto>();
	private static Vector <Personaje> listaPersonajes = new Vector <Personaje>();
	private static Personaje Heroe=null;
	
	public Juego(Ventana window, InterfazJuego inter) throws IOException{
		this.vent = window;
		this.tipoJuego = inter;
		this.tipoJuego.setImagen(this);
	}
	
	public void cambiarTipo(InterfazJuego tipo) throws IOException{
		//Si el tipo es distinto al actual entramos sino no
		if ( this.tipoJuego.getClass() != tipo.getClass() ){
			this.tipoJuego = tipo;
			this.getTipo().setImagen(this);
			
			//Si ahora el tipo es de nieve cambiamos todos a nieve
			if(this.tipoJuego.getClass() == new JuegoNieve().getClass()){
				if(Heroe.getTipoPersonaje().getClass() == new HeroeCueva().getClass())
					Heroe.cambiarTipo(new HeroeNieve());
				for(int i=0; i < listaPersonajes.size(); i++){
					if(listaPersonajes.get(i).getTipoPersonaje().getClass() == new EnemigoCueva().getClass())
						listaPersonajes.get(i).cambiarTipo(new EnemigoNieve());
				}
			}
			//Si ahora el tipo es de Cueva cambiamos todos a cueva
			else if(this.tipoJuego.getClass() == new JuegoCueva().getClass()){
				if(Heroe.getTipoPersonaje().getClass() == new HeroeNieve().getClass())
					Heroe.cambiarTipo(new HeroeCueva());
				for(int i=0; i < listaPersonajes.size(); i++){
					if(listaPersonajes.get(i).getTipoPersonaje().getClass() == new EnemigoNieve().getClass())
						listaPersonajes.get(i).cambiarTipo(new EnemigoCueva());
				}
			}
		}
	}
	
	public boolean choqueObstaculo(int posicionX, int posicionY){
		for (int i = 0; i < listaObjetos.size(); i++){
			//Si es de tipo Roca  y la posicion esta dentro de su perimetro no permite el movimiento
			if( (listaObjetos.get(i).getTipoObjeto().getClass() == (new ObjetoRoca().getClass())) && ( (listaObjetos.get(i).getPosX() >= posicionX) && (listaObjetos.get(i).getPosX() <= (posicionX + Ventana.getDiametroImagen())) ) && ( (listaObjetos.get(i).getPosY() >= posicionY) && (listaObjetos.get(i).getPosY() <= (posicionY + Ventana.getDiametroImagen())) ) )
				return true;		
		}
		return false;
	}
	
	public boolean posLibre(int posicionX, int posicionY){
		for (int i = 0; i < listaObjetos.size(); i++){
			if(( (listaObjetos.get(i).getPosX() >= posicionX) && (listaObjetos.get(i).getPosX() <= (posicionX + Ventana.getDiametroImagen())) ) && ( (listaObjetos.get(i).getPosY() >= posicionY) && (listaObjetos.get(i).getPosY() <= (posicionY + Ventana.getDiametroImagen())) ) )
				return false;
		}
		for (int i = 0; i < listaPersonajes.size(); i++){
			if(  ( (listaPersonajes.get(i).getPosX() >= posicionX) && (listaPersonajes.get(i).getPosX() <= (posicionX + Ventana.getDiametroImagen())) ) && ( (listaPersonajes.get(i).getPosY() >= posicionY) && (listaPersonajes.get(i).getPosY() <= (posicionY + Ventana.getDiametroImagen())) )  )
				return false;
		}
		if(Heroe != null){	
			if( ( (Heroe.getPosX() >= posicionX) && (Heroe.getPosX() <= (posicionX + Ventana.getDiametroImagen())) ) && ( (Heroe.getPosY() >= posicionY) && (Heroe.getPosY() <= (posicionY + Ventana.getDiametroImagen())) )  )
				return false;
		}
		return true;
	}
	
	 private void dibuja() throws Exception {
		 SwingUtilities.invokeAndWait(new Runnable() {
             public void run() {
                 vent.paintImmediately(0, 0, Ventana.getAnchoVentana(), Ventana.getAltoVentana());
             }
         });
	 }
	 
	 public void movimientoPersonajes() throws Exception{
		 Heroe.moverPersonaje(this.vent, this);
		 for (int i=0; i < listaPersonajes.size(); i++)
			 listaPersonajes.get(i).moverPersonaje(this.vent, this);
		 dibuja();
	 }
	 
	 public void cogerObjeto(Objeto obj){
		 listaObjetos.remove(obj);
	 }
	 
	 public int finalJuego(){
		 if(Heroe.muerto()){
			 return GAMEOVER;
		 }
		 else if ((Heroe.poseeGema()) && ( Heroe.getPosX() == posXInicio) && (Heroe.getPosY() == posYInicio)){
			 return WIN;
		 }
		 else{
			 return CONTINUAR;
		 }
	 }
	
	//----------------------------------------------------------------------------------------------------------------------------
	
	public void setImagen(Image imagen){
		imagenFondo = imagen;
	}
	
	//----------------------------------------------------------------------------------------------------------------------------
	
	//Metodos Getter
	public Vector <Personaje> getPersonajes(){
		return listaPersonajes;
	}
	
	public Personaje getHeroe(){
		return Heroe;
	}
	
	public Vector <Objeto> getObjetos(){
		return listaObjetos;
	}

	public Image getImagen(){
		return imagenFondo;
	}
	
	public InterfazJuego getTipo(){
		return tipoJuego;
	}
	
	//----------------------------------------------------------------------------------------------------------------------------
	
	//Metodo de Creacion de Personajes y Objetos
	public void generarObjetosYPersonajes(int numeroEnemigos, int numeroRocas, int numeroLocos) throws IOException{
		Random rand = new Random();
		int posicionX, posicionY;
		
		//Generamos la posicion aleatoria de ESPADA
		do{
			posicionX = rand.nextInt(Ventana.getAnchoVentana()-Ventana.getDiametroImagen());
			posicionY = rand.nextInt(Ventana.getAltoVentana()-Ventana.getDiametroImagen());
		}while(!posLibre(posicionX, posicionY) || (posicionX == posXInicio && posicionY == posYInicio));
		listaObjetos.add(new Objeto((new ObjetoEspada()),posicionX, posicionY));
		//Generamos la posicion aleatoria de ESCUDO
		do{
			posicionX = rand.nextInt(Ventana.getAnchoVentana()-Ventana.getDiametroImagen());
			posicionY = rand.nextInt(Ventana.getAltoVentana()-Ventana.getDiametroImagen());
		}while(!posLibre(posicionX, posicionY) || (posicionX == posXInicio && posicionY == posYInicio));
		listaObjetos.add(new Objeto((new ObjetoEscudo()),posicionX, posicionY));
		//Generamos la posicion aleatoria de GEMA
		do{
			posicionX = rand.nextInt(Ventana.getAnchoVentana()-Ventana.getDiametroImagen());
			posicionY = rand.nextInt(Ventana.getAltoVentana()-Ventana.getDiametroImagen());
		}while(!posLibre(posicionX, posicionY) || (posicionX == posXInicio && posicionY == posYInicio));
		listaObjetos.add(new Objeto((new ObjetoGema()),posicionX, posicionY));
		
		//Generamos la posicion aleatoria de POCION
		do{
			posicionX = rand.nextInt(Ventana.getAnchoVentana()-Ventana.getDiametroImagen());
			posicionY = rand.nextInt(Ventana.getAltoVentana()-Ventana.getDiametroImagen());
		}while(!posLibre(posicionX, posicionY) || (posicionX == posXInicio && posicionY == posYInicio));
		listaObjetos.add(new Objeto((new ObjetoPocion()),posicionX, posicionY));
		//Generamos la posicion aleatoria de ROCAS
		for (int i=0; i < numeroRocas; i++){
			do{
				posicionX = rand.nextInt(Ventana.getAnchoVentana()-Ventana.getDiametroImagen());
				posicionY = rand.nextInt(Ventana.getAltoVentana()-Ventana.getDiametroImagen());
			}while(!posLibre(posicionX, posicionY) || (posicionX == posXInicio && posicionY == posYInicio));
			listaObjetos.add(new Objeto((new ObjetoRoca()),posicionX, posicionY));
		}		
		
		//Colocamos al HEROE y generamos las posiciones aleatorias de los enemigos teniendo en cuenta el tipo de Juego
		if(this.tipoJuego.getClass() == new JuegoNieve().getClass() ){
			Heroe = new Personaje((new HeroeNieve()),this, this.vent, posXInicio, posYInicio);
			for (int i=0 ;  i  < numeroEnemigos; i++){
				do{		
					posicionX = rand.nextInt(Ventana.getAnchoVentana()-Ventana.getDiametroImagen());
					posicionY = rand.nextInt(Ventana.getAltoVentana()-Ventana.getDiametroImagen());
				}while(!posLibre(posicionX, posicionY));
				listaPersonajes.add(new Personaje((new EnemigoNieve()), this, this.vent, posicionX, posicionY));
			}
			for (int i=0; i < numeroLocos; i++){
				do{		
					posicionX = rand.nextInt(Ventana.getAnchoVentana()-Ventana.getDiametroImagen());
					posicionY = rand.nextInt(Ventana.getAltoVentana()-Ventana.getDiametroImagen());
				}while(!posLibre(posicionX, posicionY));
				listaPersonajes.add(new Personaje((new LocoNieve()), this, this.vent, posicionX, posicionY));
			}
		}
		else if(this.tipoJuego.getClass() == new JuegoCueva().getClass() ){
			Heroe = new Personaje((new HeroeCueva()), this, this.vent, posXInicio, posYInicio);
			for (int i=0 ;  i  < numeroEnemigos; i++){
				do{
					posicionX = rand.nextInt(Ventana.getAnchoVentana()-Ventana.getDiametroImagen());
					posicionY = rand.nextInt(Ventana.getAltoVentana()-Ventana.getDiametroImagen());
				}while(!posLibre(posicionX, posicionY));
				listaPersonajes.add(new Personaje((new EnemigoCueva()), this, this.vent, posicionX, posicionY));
			}
			for (int i=0; i < numeroLocos; i++){
				do{		
					posicionX = rand.nextInt(Ventana.getAnchoVentana()-Ventana.getDiametroImagen());
					posicionY = rand.nextInt(Ventana.getAltoVentana()-Ventana.getDiametroImagen());
				}while(!posLibre(posicionX, posicionY));
				listaPersonajes.add(new Personaje((new LocoCueva()), this, this.vent, posicionX, posicionY));
			}
		}		
	}
}
