package es.studium.programagestion;

import java.awt.Frame;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class AyudaUsuario extends WindowAdapter{

	Frame ayudaPrograma = new Frame("Ayuda");

	AyudaUsuario()
	{
		ayudaPrograma.setSize(400,200);
		ayudaPrograma.setLocationRelativeTo(null);
		colocarIcono();
		cargarAyudaPrograma();
		ayudaPrograma.addWindowListener(this);
		ayudaPrograma.setResizable(false);
		ayudaPrograma.setVisible(true);
	}

	public void colocarIcono() {
		Toolkit mipantalla = Toolkit.getDefaultToolkit();
		Image miIcono = mipantalla.getImage("farmacia.png");
		ayudaPrograma.setIconImage(miIcono);
	}

	public void cargarAyudaPrograma() {
		// Iniciar ayuda
		try 
		{
			Runtime.getRuntime().exec("hh.exe ayuda.chm");
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	@Override
	public void windowClosing(WindowEvent e) {
		ayudaPrograma.setVisible(false);
		new MenuPrincipalUsuario();
	}
}