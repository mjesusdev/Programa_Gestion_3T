package es.studium.programagestion;

import java.awt.Frame;
import java.awt.Image;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Ayuda extends WindowAdapter{

	Frame ayuda = new Frame("Ayuda");

	Toolkit mipantalla = Toolkit.getDefaultToolkit();
	
	TextArea txtAyuda = new TextArea("Lorem ipsum dolor sit amet, consectetur adipiscing elit. "
			+ "\n" + "Duis efficitur iaculis risus, sed pellentesque felis. "
			+ "\n" + "Donec quis arcu leo."
			+ "\n" + "Pellentesque molestie in risus sit amet vestibulum. "
			+ "\n" + "Maecenas volutpat malesuada turpis.");
	
	Ayuda()
	{
		ayuda.add(txtAyuda);
		txtAyuda.setEditable(false);
		ayuda.setSize(400,200);
		Image miIcono = mipantalla.getImage("src//farmacia.png");
		// Colocar Icono
		ayuda.setIconImage(miIcono);
		ayuda.setLocationRelativeTo(null);
		ayuda.addWindowListener(this);
		ayuda.setResizable(false);
		ayuda.setVisible(true);
	}

	@Override
	public void windowClosing(WindowEvent e) {
		ayuda.setVisible(false);
		new MenuPrincipal(null);
	}
}