package VentanaJuego;

public class Movimiento extends Thread{
	   public void run(){
		   while (true) {
			   System.out.print("");
			   if(Ventana.getPulsacion() == true){
  					try {
						Ventana.getGame().movimientoPersonajes();
					} catch (Exception e) {
						e.printStackTrace();
					}
  				}
		   }
	   }
}
