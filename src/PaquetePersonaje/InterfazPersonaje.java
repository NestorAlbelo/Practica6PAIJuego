package PaquetePersonaje;

import java.io.IOException;

public interface InterfazPersonaje {
	static final int PODER_ENEMIGO_NIEVE = 15;
	static final int PODER_ENEMIGO_CUEVA = 20;
	static final int PODER_HEROE_NIEVE = 10;
	static final int PODER_HEROE_CUEVA = 15;
	static final int PODER_LOCO_NIEVE = 0;
	static final int PODER_LOCO_CUEVA = 0;
	
	public void cambiarPoder(Personaje p);
	public void cambiarImagenes(Personaje p) throws IOException;
	public void mover(Personaje p);
}
