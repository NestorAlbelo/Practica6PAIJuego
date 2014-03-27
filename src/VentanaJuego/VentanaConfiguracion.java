package VentanaJuego;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import PaqueteJuego.JuegoCueva;
import PaqueteJuego.JuegoNieve;
 
public class VentanaConfiguracion implements ActionListener{
 
	private static JButton Aceptar;
    private static JTextField obstaculos, enemigos, ancho, alto, diametro, locos;
    private static JRadioButton tipoNieve, tipoCueva;
    private JPanel contentPane;
	private Ventana vent;
	private Ventana vent2;
	private static VentanaConfiguracion ventanaConfig;

	
    public VentanaConfiguracion() {
    	JFrame frame = new JFrame("Botones con Java");
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);
		

		//Generamos el boton
		Aceptar = new JButton("Comenzar Juego");
		Aceptar.setBounds(285, 376, 134, 29);
		contentPane.add(Aceptar);
		
		//Generamos los TextField
	    ancho = new JTextField();
	    ancho.setBounds(285, 86, 134, 28);
	    contentPane.add(ancho);
	    ancho.setColumns(10);
        
	    alto = new JTextField();
	    alto.setColumns(10);
	    alto.setBounds(285, 46, 134, 28);
	    contentPane.add(alto);
        
	    diametro = new JTextField();
	    diametro.setColumns(10);
	    diametro.setBounds(285, 126, 134, 28);
	    contentPane.add(diametro);
        
	    enemigos = new JTextField();
	    enemigos.setColumns(10);
	    enemigos.setBounds(285, 166, 134, 28);
	    contentPane.add(enemigos);
        
	    obstaculos = new JTextField();
	    obstaculos.setColumns(10);
	    obstaculos.setBounds(285, 208, 134, 28);
	    contentPane.add(obstaculos);
	    
	    locos = new JTextField();
	    locos.setColumns(10);
	    locos.setBounds(285, 250, 134, 28);
	    contentPane.add(locos);
	    
	    //Generamos los JRadioButton
	    tipoNieve = new JRadioButton("Tipo Nieve");
	    tipoNieve.setBounds(285, 292, 134, 28);
		contentPane.add(tipoNieve);
		
		tipoCueva = new JRadioButton("Tipo Cueva");
		tipoCueva.setBounds(285, 334, 134, 28);
		contentPane.add(tipoCueva);
		
		//Agrupamos los JRadioButton
		ButtonGroup group = new ButtonGroup();
	    group.add(tipoNieve);
	    group.add(tipoCueva);
	    
	    
	    //Generamos las JLabel
	    JLabel IntroducirDatos = new JLabel("Introduzca los siguientes valores:");
        IntroducirDatos.setBounds(19, 17, 272, 16);
        contentPane.add(IntroducirDatos);
        
        JLabel AnchoVentana = new JLabel("- Ancho de la Ventana");
        AnchoVentana.setBounds(61, 52, 150, 16);
        contentPane.add(AnchoVentana);
        
        JLabel AltoVentana = new JLabel("- Alto de la Ventana");
        AltoVentana.setBounds(61, 92, 150, 16);
        contentPane.add(AltoVentana);
        
        JLabel DiametroImagen = new JLabel("- Diametro de las imagenes");
        DiametroImagen.setBounds(61, 132, 175, 16);
        contentPane.add(DiametroImagen);
        
        JLabel NumeroEnemigos = new JLabel("- Numero de Enemigos");
        NumeroEnemigos.setBounds(61, 172, 175, 16);
        contentPane.add(NumeroEnemigos);
        
        JLabel NumeroObjetivos = new JLabel("- Numero de Obstaculos");
        NumeroObjetivos.setBounds(61, 214, 175, 16);
        contentPane.add(NumeroObjetivos);
        
        JLabel NumeroLocos = new JLabel("- Numero de Locos");
        NumeroLocos.setBounds(61, 256, 175, 16);
        contentPane.add(NumeroLocos);
        
        JLabel TipoEscenario = new JLabel("- Seleccione tipo escenario");
        TipoEscenario.setBounds(61, 298, 175, 16);
        contentPane.add(TipoEscenario);
		
        
	 
		
        Aceptar.addActionListener(this);
        frame.setSize(490,420);
		frame.setVisible(true);
	}

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Comenzar Juego")){
        		if(tipoNieve.isSelected()){
        			try {
        				vent = new Ventana(new JuegoNieve(), Integer.parseInt(ancho.getText()), Integer.parseInt(alto.getText()),Integer.parseInt(diametro.getText()),Integer.parseInt(enemigos.getText()),Integer.parseInt(obstaculos.getText()),Integer.parseInt(locos.getText()));        			
        			} catch (IOException e1) {
        				e1.printStackTrace();
        			}
        			Movimiento move = new Movimiento();
        			move.start();
                }
        		if(tipoCueva.isSelected()){
        			try {
        				vent2 = new Ventana(new JuegoCueva(), Integer.parseInt(ancho.getText()), Integer.parseInt(alto.getText()),Integer.parseInt(diametro.getText()),Integer.parseInt(enemigos.getText()),Integer.parseInt(obstaculos.getText()),Integer.parseInt(locos.getText()));
        			} catch (IOException e1) {
        				e1.printStackTrace();
        			}
        			Movimiento move = new Movimiento();
        			move.start();
        		}
        }
        		
    }
    
    public static void main(String[] args) {
        ventanaConfig = new VentanaConfiguracion();
    }
}