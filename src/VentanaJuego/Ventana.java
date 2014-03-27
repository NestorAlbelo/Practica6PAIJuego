package VentanaJuego;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;

import PaqueteJuego.*;

public class Ventana extends JComponent {
	private static int ANCHO_VENTANA;
	private static int ALTO_VENTANA;
	private static int DIAMETRO_IMAGEN;
	private static int NUMEROENEMIGOS;
	private static int NUMEROBSTACULOS;
	private static int NUMEROLOCOS;
	private static InterfazJuego INTERFAZJUEGO;
	private static int GAMEOVER = -1;
	private static int WIN = 1;
	private static int CONTINUAR = 0;
	static boolean up, down, left, right,  pulsacion;	//Direcciones de las Teclas
	private static float dt = (float)(652000.0 / 1000000000);
	private static Juego game;
	
	public Ventana(InterfazJuego tipoJuego, int AnchoVentana, int AltoVentana, int DiametroImagen, int NumeroEnemigos, int NumeroObstaculos, int NumeroLocos) throws IOException{
		//Creamos la ventana principal
		JFrame jf = new JFrame("Ventana Juego");
        jf.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
        });
        jf.setResizable(false);	

		INTERFAZJUEGO = tipoJuego;
		ANCHO_VENTANA = AnchoVentana;
		ALTO_VENTANA = AltoVentana;
		DIAMETRO_IMAGEN = DiametroImagen;
		NUMEROENEMIGOS = NumeroEnemigos;
		NUMEROBSTACULOS = NumeroObstaculos;
		NUMEROLOCOS = NumeroLocos;
		setPreferredSize(new Dimension(ANCHO_VENTANA,ALTO_VENTANA));
		game = new Juego(this, INTERFAZJUEGO);
		game.generarObjetosYPersonajes(NUMEROENEMIGOS,NUMEROBSTACULOS,NUMEROLOCOS);
		

		//Creamos el Listener de las teclas 
		
		pulsacion=false;
		addKeyListener(new KeyAdapter(){
			  public void keyPressed(KeyEvent e) {
				  pulsacion = true;
                  try {
					actualiza(e.getKeyCode(), true);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
              }

              public void keyReleased(KeyEvent e) {
            	      pulsacion = false;
                  try {
					actualiza(e.getKeyCode(), false);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
              }

              private void actualiza(int keyCode, boolean pressed) throws IOException {
                  switch (keyCode) {
                      case KeyEvent.VK_UP:
                          up = pressed;
                          break;
                      case KeyEvent.VK_DOWN:
                          down = pressed;
                          break;
                      case KeyEvent.VK_LEFT:
                          left = pressed;
                          break;
                      case KeyEvent.VK_RIGHT:
                          right = pressed;
                          break;
                      case KeyEvent.VK_ENTER:
                    	  	  game.cambiarTipo(new JuegoNieve());
                    	  	  break;
                      case KeyEvent.VK_ALT:
                	  	  	  game.cambiarTipo(new JuegoCueva());
                	  	      break;
                  }
              }
		});
		setFocusable(true);
		jf.setResizable(true);
        jf.getContentPane().add(this);
        jf.pack();
        jf.setVisible(true);
	}
	
	public void paint(Graphics g) {			
		//Dibujamos la imagen de fondo
		for(int i=0; i <= ANCHO_VENTANA/DIAMETRO_IMAGEN; i++)
 			for(int j=0; j <= ALTO_VENTANA/DIAMETRO_IMAGEN; j++)
 				g.drawImage(game.getImagen(), Math.round(i)*DIAMETRO_IMAGEN,Math.round(j)*DIAMETRO_IMAGEN, DIAMETRO_IMAGEN, DIAMETRO_IMAGEN, null );
	 	//Dibujamos todos los objetos encima del fondo
	 	for( int i = 0; i < game.getObjetos().size(); i++)
	 		g.drawImage(game.getObjetos().get(i).getImagen(), Math.round(game.getObjetos().get(i).getPosX()),Math.round(game.getObjetos().get(i).getPosY()), DIAMETRO_IMAGEN, DIAMETRO_IMAGEN, null );
	 	
	 	//Dibujamos a todos los personajes
	 	for( int i = 0; i < game.getPersonajes().size(); i++){
	 		g.drawImage(game.getPersonajes().get(i).getImagen(), Math.round(game.getPersonajes().get(i).getPosX()),Math.round(game.getPersonajes().get(i).getPosY()), DIAMETRO_IMAGEN, DIAMETRO_IMAGEN, null );
	 	}
	    //Dibujamos a el Heroe
        g.drawImage(game.getHeroe().getImagen(), Math.round(game.getHeroe().getPosX()), Math.round(game.getHeroe().getPosY()), DIAMETRO_IMAGEN, DIAMETRO_IMAGEN, null);
        
        if(game.finalJuego() != CONTINUAR){	
        		perderTiempo();
        		if(game.finalJuego() == WIN){	
        			System.out.println("Ganaste");
        			g.setColor(Color.WHITE);
        			g.fillRect(0, 0, ANCHO_VENTANA, ALTO_VENTANA);
        			g.setColor(Color.BLACK);
        			g.drawString("GANASTE",ANCHO_VENTANA/2-36,ALTO_VENTANA/2);		
        		}
        		else if(game.finalJuego() == GAMEOVER){	
        			System.out.println("Perdiste");
        			g.setColor(Color.WHITE);
        			g.fillRect(0, 0, ANCHO_VENTANA, ALTO_VENTANA);
        			g.setColor(Color.BLACK);
        			g.drawString("GAME OVER",ANCHO_VENTANA/2-45,ALTO_VENTANA/2);			
        		}
        		perderTiempo();
        		System.exit(0);
        }
			
	}
	
	private void perderTiempo(){
		long time = System.currentTimeMillis()+1000;
		while(System.currentTimeMillis() < time){
			//NO HACER NADA
		}
	}
	
	//SETTER
	public void setNumeroObstaculos(int nObstaculos){
		
	}
	 
	 //GETTER
 	public static float getDT(){
		 return dt;
	 }	 
	public static int getAnchoVentana() {
		return ANCHO_VENTANA;
	}
	public static int getAltoVentana() {
		return ALTO_VENTANA;
	}
	public static int getDiametroImagen() {
		return DIAMETRO_IMAGEN;
	}
	public static boolean isUp() {
		return up;
	}
	public static boolean isDown() {
		return down;
	}
	public static boolean isLeft() {
		return left;
	}
	public static boolean isRight() {
		return right;
	}
	public static Juego getGame(){
		return game;
	}
	public static boolean getPulsacion(){
		return pulsacion;
	}
	 
}