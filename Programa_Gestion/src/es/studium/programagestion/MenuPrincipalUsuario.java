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

	// Crear varios Menús
	Menu menuClientes = new Menu("Clientes");
	Menu menuEmpleados = new Menu("Empleados");
	Menu menuProductos = new Menu("Productos");
	Menu menuCompra = new Menu("Compra");
	Menu menuAyuda = new Menu("Ayuda");

	// Crear Menú Item
	MenuItem mnimenuClientesAlta = new MenuItem("Alta");
	MenuItem mnimenuEmpleadosAlta = new MenuItem("Alta");
	MenuItem mnimenuProductosAlta = new MenuItem("Alta");
	MenuItem mnimenuAyudaVer = new MenuItem("Ver ayuda");
	MenuItem mnimenuCompraAlta = new MenuItem("Alta");

	MenuPrincipalUsuario()
	{
		colocarIcono();
		// Añadir la barra de Menú
		MenuUsuario.setMenuBar(barraMenu);
		barraMenu.add(menuClientes);
		barraMenu.add(menuEmpleados);
		barraMenu.add(menuProductos);
		barraMenu.add(menuCompra);
		barraMenu.add(menuAyuda);

		// Añadir a los menús los submenús (MenuItem)
		menuClientes.add(mnimenuClientesAlta);
		menuEmpleados.add(mnimenuEmpleadosAlta);
		menuProductos.add(mnimenuProductosAlta);
		menuCompra.add(mnimenuCompraAlta);
		menuAyuda.add(mnimenuAyudaVer);

		// Listeners a las opciones del menú
		mnimenuClientesAlta.addActionListener(this);
		mnimenuEmpleadosAlta.addActionListener(this);
		mnimenuProductosAlta.addActionListener(this);
		mnimenuCompraAlta.addActionListener(this);
		mnimenuAyudaVer.addActionListener(this);

		// Establecer tamaño a la ventana y localización en pantalla
		MenuUsuario.setSize(450,250);
		// Establecer al medio la ventana, dependiendo de cual sea la resolución.
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
			// No hacer visible el Menú
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

			new AyudaUsuario();
			MenuUsuario.setVisible(false);
		}
	}
}