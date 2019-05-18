package es.studium.programagestion;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class MenuPrincipalUsuario extends WindowAdapter implements ActionListener{

	Frame MenuUsuario = new Frame("Men� Principal");
	
	// Crear barra de Men�
	MenuBar barraMenu = new MenuBar();

	// Crear varios Men�s
	Menu menuClientes = new Menu("Clientes");
	Menu menuEmpleados = new Menu("Empleados");
	Menu menuProductos = new Menu("Productos");
	Menu menuAyuda = new Menu("Ayuda");

	// Crear Men� Item
	MenuItem mnimenuClientesAlta = new MenuItem("Alta");
	MenuItem mnimenuEmpleadosAlta = new MenuItem("Alta");
	MenuItem mnimenuProductosAlta = new MenuItem("Alta");
	MenuItem mnimenuAyudaVer = new MenuItem("Ver ayuda");
	
	MenuPrincipalUsuario()
	{
		// Llamar al m�todo que a�ade el icono a la aplicaci�n
		colocarIcono();
		
		// A�adir la barra de Men�
		MenuUsuario.setMenuBar(barraMenu);
		barraMenu.add(menuClientes);
		barraMenu.add(menuEmpleados);
		barraMenu.add(menuProductos);
		barraMenu.add(menuAyuda);

		// A�adir a los men�s los submen�s (MenuItem)
		menuClientes.add(mnimenuClientesAlta);
		menuEmpleados.add(mnimenuEmpleadosAlta);
		menuProductos.add(mnimenuProductosAlta);
		menuAyuda.add(mnimenuAyudaVer);
		
		// Listeners a las opciones del men�
		mnimenuClientesAlta.addActionListener(this);
		mnimenuEmpleadosAlta.addActionListener(this);
		mnimenuProductosAlta.addActionListener(this);
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
		// Cierra la ventana
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
		
		else if(mnimenuAyudaVer.equals(arg0.getSource())) {
			Guardar_Movimientos f = new Guardar_Movimientos();
			try {
				f.registrar("usuario]" + "[Ayuda");
			} catch (IOException e) {
				e.printStackTrace();
			}		
			
			try 
			{
				Runtime.getRuntime().exec("hh.exe ayuda.chm");
			}
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			MenuUsuario.setVisible(false);
		}
	}
}