package PaquetePersonaje;

import java.awt.Image;
import java.io.IOException;
import java.util.Vector;

import PaqueteJuego.Juego;
import VentanaJuego.Ventana;


public class Personaje {
	private int vida;
	private float posX;
	private float posY;
	boolean escudo;
	boolean espada;
	boolean gema;
	private int ataque;
	private float vx, vy;	
	private Juego game;	
	private Image imagenVivo;
	private Image imagenMuerto;
	private final int VIDA_HEROE = 100;
	private final int VIDA_ENEMIGO = 50;
	private final int VELOCIDAD_MOV = 50000/Ventana.getDiametroImagen();
	private static final int ARRIBA = 0;
	private static final int ABAJO= 1;
	private static final int IZQ = 2;
	private static final int DER = 3;
	private InterfazPersonaje tipoPersonaje;
	  

	public Personaje(InterfazPersonaje tipo, Juego g, Ventana window, int x, int y) throws IOException{
		this.posX = x;
		this.posY = y;
		this.game = g;
		this.tipoPersonaje = tipo;		
		this.tipoPersonaje.cambiarPoder(this);
		this.tipoPersonaje.cambiarImagenes(this);
		this.escudo = this.espada = this.gema = false;
		if(tipo.getClass() == (new HeroeNieve().getClass()) || tipo.getClass() == (new HeroeCueva().getClass()))
			this.vida = VIDA_HEROE;
		else if(tipo.getClass() == (new EnemigoNieve().getClass()) || tipo.getClass() == (new EnemigoCueva().getClass()))
			this.vida = VIDA_ENEMIGO;
	}
	
	public void getDamage(int herida){
		if(this.escudo==true)
			vida-=(int)(0.5*herida);
		else
			vida-=herida;
	}
	
	public void cambiarTipo(InterfazPersonaje tipo) throws IOException{
		//Si el objeto actual es un heroe y el tipo entrante tambien se cambia
		if((tipo.getClass() == (new HeroeNieve().getClass()) || tipo.getClass() == (new HeroeCueva().getClass())) && ((this.tipoPersonaje.getClass() == (new HeroeNieve().getClass())) || (this.tipoPersonaje.getClass() == (new HeroeCueva().getClass()))))
			this.tipoPersonaje = tipo;
		//Si el objeto actual es un enemigo y el tipo entrante tambien se cambia
		else if((tipo.getClass() == (new EnemigoNieve().getClass()) || tipo.getClass() == (new EnemigoCueva().getClass())) && ((this.tipoPersonaje.getClass() == (new EnemigoNieve().getClass())) || (this.tipoPersonaje.getClass() == (new EnemigoCueva().getClass()))))
			this.tipoPersonaje = tipo;	
		
		this.tipoPersonaje.cambiarImagenes(this);
	}
	
	public void atacar(){
		//Si es tipo Heroe
		if(this.tipoPersonaje.getClass() == (new HeroeNieve().getClass()) || this.tipoPersonaje.getClass() == (new HeroeCueva()).getClass()){
			for (int i=0; i< game.getPersonajes().size(); i++){
				if( ( !game.getPersonajes().get(i).muerto() ) && (  game.getPersonajes().get(i).getTipoPersonaje().getClass() != this.tipoPersonaje.getClass() )  && (  personajeCerca(game.getPersonajes().get(i)) )  )					// Si el tipo del personaje actual es distinto al nuestro le atacamos y esta cerca
					if(this.espada == true)
						game.getPersonajes().get(i).getDamage(this.ataque*10);
					else 
						game.getPersonajes().get(i).getDamage(this.ataque);
			}
		}
		//Si es tipo Enemigo
		else if(this.tipoPersonaje.getClass() == (new EnemigoNieve().getClass()) || this.tipoPersonaje.getClass() == (new EnemigoCueva()).getClass()){
			if(!game.getHeroe().muerto() && personajeCerca(game.getHeroe())){
				if(this.espada == true)
					game.getHeroe().getDamage(this.ataque*10);
				else 
					game.getHeroe().getDamage(this.ataque);
			}
		}	
	}

	private boolean personajeCerca(Personaje p){
		int Diametro = Ventana.getDiametroImagen();
		//Si esta por la derecha o por la izquierda
		if((((this.getPosX() >= (p.getPosX() + Diametro))   &&   (this.getPosX() <= (p.getPosX() + Diametro*5/4))) || (((this.getPosX() + Diametro) <= p.getPosX())   &&   ((this.getPosX() + Diametro)  >= (p.getPosX() - Diametro/4))) )  &&   ((this.getPosY() >= p.getPosY())  &&  (  this.getPosY() <= (p.getPosY() + Diametro)))){
			return true;
		}
		//Si esta por arriba o por abajo
		else if((((this.getPosY() >= (p.getPosY() + Diametro))   &&   (this.getPosY() <= (p.getPosY() + Diametro*5/4))) || (((this.getPosY() + Diametro) <= p.getPosY())   &&   ((this.getPosY() + Diametro)  >= (p.getPosY() - Diametro/4))) )  &&   ((this.getPosX() >= p.getPosX())  &&  (  this.getPosX() <= (p.getPosX() + Diametro)))){
			return true;
		}
		return false;
	}
	
	public void fisica(int dir) {
        vx = 0;
        vy = 0;
       if(this.getTipoPersonaje().getClass() == (new HeroeNieve().getClass()) || this.getTipoPersonaje().getClass() == (new HeroeCueva().getClass()) ){
    	   		if ( Ventana.isUp() )
               vy = -VELOCIDAD_MOV;
    	   		else if ( Ventana.isDown() )
               vy = VELOCIDAD_MOV;
           if ( Ventana.isLeft() )
               vx = -VELOCIDAD_MOV;
           else if ( Ventana.isRight() )
               vx = VELOCIDAD_MOV;
       }
       else{
    	   	   if ( dir == ARRIBA )
               vy = -VELOCIDAD_MOV;
    	   	   else if ( dir ==  ABAJO )
               vy = VELOCIDAD_MOV;
           if ( dir == IZQ )
               vx = -VELOCIDAD_MOV;
           else if ( dir == DER )
               vx = VELOCIDAD_MOV;
       }
       if(!game.choqueObstaculo(Math.round(this.posX + vx * Ventana.getDT()), Math.round(this.posY + vy * Ventana.getDT()))){						//Si no hay ni obstaculos ni personajes en esa posicion
    	   		this.posX = rango(this.posX + vx * Ventana.getDT(), 0, Ventana.getAnchoVentana() - Ventana.getDiametroImagen());
            this.posY = rango(this.posY + vy * Ventana.getDT(), 0, Ventana.getAltoVentana() - Ventana.getDiametroImagen());
       }
    }
 
	private float rango(float valor, float min, float max) {
        if (valor > max)		//Limite inferior y derecho
            return max;
        if (valor < min)		//Limite superior e izquierdo
            return min;
        return valor;
    }
	
	public void moverPersonaje(Ventana vent, Juego game){
		this.tipoPersonaje.mover(this);
	}
  
	public void cogerObjeto(String object){
		if(object=="Escudo"){
			escudo=true;
		}
		else if(object == "Espada"){
			espada=true;
		}
		else if(object == "Pocion"){
			vida+=100;
		}
		else if(object == "Gema"){
			gema = true;
		}
	}
	
  	//----------------------------------------------------------------------------------------------------------------------------
	
	//Metodos Setter
	public void setImagenes(Image vivo, Image muerto){
		imagenVivo = vivo;
		imagenMuerto = muerto;
	}
	
	public void setAtaque(int attack){
		ataque = attack;
	}
	
	//----------------------------------------------------------------------------------------------------------------------------
	
	// Metodos Getter
	public Image getImagen(){
		if(muerto())
			return imagenMuerto;
		else
			return imagenVivo;
	}
	
	public InterfazPersonaje getTipoPersonaje(){
		return tipoPersonaje;
	}
	
	public float getPosX(){
		return posX;
	}
	
	public float getPosY(){
		return posY;
	}
	
	public int getVida(){
		return vida;
	}
	
	public Juego getJuego(){
		return game;
	}
	
	public boolean poseeGema(){
		return gema;
	}
	
	public boolean muerto(){
		if(vida<=0)
			return true;
		else 
			return false;
	}
}
