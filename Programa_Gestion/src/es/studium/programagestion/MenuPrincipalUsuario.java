package es.studium.programagestion;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuPrincipalUsuario extends WindowAdapter implements ActionListener{

	JFrame MenuUsuario = new JFrame("Men� Principal");

	// Crear barra de Men�
	JMenuBar barraMenu = new JMenuBar();

	// Crear varios Men�s
	JMenu menuClientes = new JMenu("Clientes");
	JMenu menuEmpleados = new JMenu("Empleados");
	JMenu menuProductos = new JMenu("Productos");
	JMenu menuCompra = new JMenu("Compra");
	JMenu menuAyuda = new JMenu("Ayuda");

	// Crear Men� Item
	JMenuItem mnimenuClientesAlta = new JMenuItem("Alta");
	JMenuItem mnimenuEmpleadosAlta = new JMenuItem("Alta");
	JMenuItem mnimenuProductosAlta = new JMenuItem("Alta");
	JMenuItem mnimenuAyudaVer = new JMenuItem("Ver ayuda");
	JMenuItem mnimenuCompraAlta = new JMenuItem("Alta");

	MenuPrincipalUsuario()
	{
		colocarIcono();
		// A�adir la barra de Men�
		MenuUsuario.setJMenuBar(barraMenu);
		barraMenu.add(menuClientes);
		barraMenu.add(menuEmpleados);
		barraMenu.add(menuProductos);
		barraMenu.add(menuCompra);
		barraMenu.add(menuAyuda);

		// A�adir a los men�s los submen�s (MenuItem)
		menuClientes.add(mnimenuClientesAlta);
		menuEmpleados.add(mnimenuEmpleadosAlta);
		menuProductos.add(mnimenuProductosAlta);
		menuCompra.add(mnimenuCompraAlta);
		menuAyuda.add(mnimenuAyudaVer);

		// Listeners a las opciones del men�
		mnimenuClientesAlta.addActionListener(this);
		mnimenuEmpleadosAlta.addActionListener(this);
		mnimenuProductosAlta.addActionListener(this);
		mnimenuCompraAlta.addActionListener(this);
		mnimenuAyudaVer.addActionListener(this);

		// Establecer tama�o a la ventana y localizaci�n en pantalla
		MenuUsuario.setSize(450,250);
		// Establecer al medio la ventana, dependiendo de cual sea la resoluci�n.
		MenuUsuario.setLocationRelativeTo(null);
		MenuUsuario.addWindowListener(this);
		MenuUsuario.setResizable(false);
		MenuUsuario.setVisible(true);
	}

	public void colocarIcono() {
		Toolkit mipantalla = Toolkit.getDefaultToolkit();
		Image miIcono = mipantalla.getImage("farmacia.png");
		MenuUsuario.setIconImage(miIcono);
	}

	@Override
	public void windowClosing(WindowEvent e) {
		Guardar_Movimientos f = new Guardar_Movimientos();
		try {
			f.registrar("usuario]" + "[Logout");
		} catch (IOException ie) {
			ie.printStackTrace();
		}

		System.exit(0);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (mnimenuClientesAlta.equals(arg0.getSource())) {
			// Llamar a la clase AltaClientesUsuario
			new AltaClientesUsuario();
			// No hacer visible el Men�
			MenuUsuario.setVisible(false);
		}

		else if(mnimenuEmpleadosAlta.equals(arg0.getSource())) {
			new AltaEmpleadosUsuario();
			MenuUsuario.setVisible(false);
		}

		else if(mnimenuProductosAlta.equals(arg0.getSource())) {
			new AltaProductosUsuario();
			MenuUsuario.setVisible(false);
		}

		else if(mnimenuCompraAlta.equals(arg0.getSource())) {
			new AltaCompraUsuario();
			MenuUsuario.setVisible(false);
		}

		else if(mnimenuAyudaVer.equals(arg0.getSource())) {
			Guardar_Movimientos f = new Guardar_Movimientos();
			try {
				f.registrar("usuario]" + "[Ayuda");
			} catch (IOException e) {
				e.printStackTrace();
			}		

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
	}
}