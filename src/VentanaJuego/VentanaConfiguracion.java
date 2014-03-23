package VentanaJuego;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
 
public class VentanaConfiguracion implements ActionListener{
 
    private static JButton Aceptar;
    private static JTextField obstaculos, enemigos, ancho, alto, diametro;
 
    public VentanaConfiguracion(){
 
        JFrame frame = new JFrame("Botones con Java");
       // frame.setLayout(new FlowLayout());
        Dimension d = new Dimension();
        
        
        
        
        
        
        Container container=frame.getContentPane();
        JTextField text=new JTextField("Text field",15);
        JLabel label=new JLabel("Label: ");
        JButton ok= new JButton("OK");
        JButton cancel= new JButton("Cancel");
        // Top Row
        Column topLevel=column(row(none,center,label,text),
               	        row(center,none,ok,cancel ));
        // Create the layout
        topLevel.createLayout(container);

        frame.pack();
        frame.show();
        
        
        
        
        
        
        
        
        
        
        
        
 
        Aceptar = new JButton("Comenzar Juego");
        obstaculos = new JTextField();
        obstaculos.setSize(50, 60);
        
        
        
        frame.add(new JLabel("Numero Obstaculos"));
        frame.add(obstaculos);
        frame.add(Aceptar);
        
        
        Aceptar.addActionListener(this);
 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);     
        frame.setLocation((int) ((d.getWidth()/2)+290), 50);
        frame.setSize(400, 250);
        frame.setVisible(true);
    }
 
    public static void main(String[] args) {
        VentanaConfiguracion ventanaConfig = new VentanaConfiguracion();
    }
 
    @Override
    public void actionPerformed(ActionEvent e) {
 
        if(e.getActionCommand().equals("Comenzar Juego")){
        		try {
					Ventana vent = new Ventana(null, 0, 0, 0, 0);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
            Movimiento move = new Movimiento();
            move.start();
        }
    }
}

