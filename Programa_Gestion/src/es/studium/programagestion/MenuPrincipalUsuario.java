package es.studium.programagestion;

import java.awt.Frame;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class MenuPrincipalUsuario extends WindowAdapter implements ActionListener{

	Frame MenuUsuario = new Frame("Menú Principal");
	
	// Crear barra de Menú
	MenuBar barraMenu = new MenuBar();

	// Crear menús
	Menu menuClientes = new Menu("Clientes");
	Menu menuEmpleados = new Menu("Empleados");
	Menu menuProductos = new Menu("Productos");
	Menu menuAyuda = new Menu("Ayuda");

	// Crear menú item dentro del menú Clientes
	MenuItem mnimenuClientesAlta = new MenuItem("Alta");
	// Crear menú item dentro del menú Clientes
	MenuItem mnimenuEmpleadosAlta = new MenuItem("Alta");
	// Crear menú item dentro del menú Clientes
	MenuItem mnimenuProductosAlta = new MenuItem("Alta");
	// Crear menú item dentro del menú Ayuda
	MenuItem mnimenuAyudaVer = new MenuItem("Ver ayuda");
	
	MenuPrincipalUsuario()
	{
		// Almacenamos en mipantalla el sistema nativo de pantallas, el tamaño por defecto de la pantalla
		Toolkit mipantalla = Toolkit.getDefaultToolkit();
		
		// Añadir la barra de Menú
		MenuUsuario.setMenuBar(barraMenu);
		barraMenu.add(menuClientes);
		barraMenu.add(menuEmpleados);
		barraMenu.add(menuProductos);
		barraMenu.add(menuAyuda);

		// Añadir al menú Clientes los item para que se vean
		menuClientes.add(mnimenuClientesAlta);
		// Añadir al menú Empleados los item para que se vean
		menuEmpleados.add(mnimenuEmpleadosAlta);
		// Añadir al menú Productos los item para que se vean
		menuProductos.add(mnimenuProductosAlta);
		// Añadir al menú Productos los item para que se vean
		menuAyuda.add(mnimenuAyudaVer);
		
		// Listeners a las opciones del menú
		mnimenuClientesAlta.addActionListener(this);
		mnimenuEmpleadosAlta.addActionListener(this);
		mnimenuProductosAlta.addActionListener(this);
		mnimenuAyudaVer.addActionListener(this);
		
		// Establecer tamaño a la ventana y localización en pantalla
		MenuUsuario.setSize(450,250);
		// Establecer al medio la ventana, dependiendo de cual sea la resolución.
		MenuUsuario.setLocationRelativeTo(null);
		MenuUsuario.addWindowListener(this);
		
		// Establecer un icono a la aplicación
		Image miIcono = mipantalla.getImage("src//farmacia.png");
		// Colocar Icono
		MenuUsuario.setIconImage(miIcono);
		MenuUsuario.setResizable(false);
		
		// No hacer visible el menú ya que se tiene iniciar el la otra clase de Programa_Gestion (Inicio de sesión)
		MenuUsuario.setVisible(true);
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
			// No hacer visible el Menú
			MenuUsuario.setVisible(false);
		}else if(mnimenuEmpleadosAlta.equals(arg0.getSource())) {
			// Llamar a la clase AltaEmpleadosUsuario
			new AltaEmpleadosUsuario();
			// No hacer visible el Menú
			MenuUsuario.setVisible(false);
		}else if(mnimenuProductosAlta.equals(arg0.getSource())) {
			// Llamar a la clase AltaProductosUsuario
			new AltaProductosUsuario();
			// No hacer visible el Menú
			MenuUsuario.setVisible(false);
		}else if(mnimenuAyudaVer.equals(arg0.getSource())) {
			Guardar_Movimientos f = new Guardar_Movimientos();
			try {
				f.registrar("Usuario]" + "[Ayuda");
			} catch (IOException e) {
				e.printStackTrace();
			}		
			
			// Llamar a la clase Ayuda
			new AyudaUsuario();
			// No hacer visible el Menú
			MenuUsuario.setVisible(false);
		}
	}
}