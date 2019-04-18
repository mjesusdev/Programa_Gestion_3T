package es.studium.programagestion;

import java.awt.Frame;
import java.awt.Image;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class AyudaUsuario implements WindowListener{

	Frame ayuda = new Frame("Ayuda");

	Toolkit mipantalla = Toolkit.getDefaultToolkit();
	
	TextArea txtAyuda = new TextArea("Lorem ipsum dolor sit amet, consectetur adipiscing elit. "
			+ "\n" + "Duis efficitur iaculis risus, sed pellentesque felis. "
			+ "\n" + "Donec quis arcu leo."
			+ "\n" + "Pellentesque molestie in risus sit amet vestibulum. "
			+ "\n" + "Maecenas volutpat malesuada turpis.");

	AyudaUsuario()
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
		if (ayuda.isActive()) {
			ayuda.dispose();
			new MenuPrincipalUsuario();
		}
	}

	@Override
	public void windowActivated(WindowEvent arg0) {}
	@Override
	public void windowClosed(WindowEvent arg0) {}
	@Override
	public void windowDeactivated(WindowEvent arg0) {}
	@Override
	public void windowDeiconified(WindowEvent arg0) {}
	@Override
	public void windowIconified(WindowEvent arg0) {}
	@Override
	public void windowOpened(WindowEvent arg0) {}
}