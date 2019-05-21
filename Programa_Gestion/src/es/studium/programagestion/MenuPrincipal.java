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

public class MenuPrincipal extends WindowAdapter implements ActionListener{

	Frame miMenu = new Frame("Men� Principal");
	
	// Crear barra de Men�
	MenuBar barraMenu = new MenuBar();

	// Crear men�s
	Menu menuClientes = new Menu("Clientes");
	Menu menuEmpleados = new Menu("Empleados");
	Menu menuProductos = new Menu("Productos");
	Menu menuCompra = new Menu("Compra");
	Menu menuAyuda = new Menu("Ayuda");

	// Crear Items dentro del men� Clientes
	MenuItem mnimenuClientesAlta = new MenuItem("Alta");
	MenuItem mnimenuClientesMod = new MenuItem("Modificaci�n");
	MenuItem mnimenuClientesBaja = new MenuItem("Baja");
	MenuItem mnimenuClientesConsulta = new MenuItem("Consulta");

	// Crear Items dentro del men� Empleados
	MenuItem mnimenuEmpleadosAlta = new MenuItem("Alta");
	MenuItem mnimenuEmpleadosMod = new MenuItem("Modificaci�n");
	MenuItem mnimenuEmpleadosBaja = new MenuItem("Baja");
	MenuItem mnimenuEmpleadosConsulta = new MenuItem("Consulta");

	// Crear Items dentro del men� Productos
	MenuItem mnimenuProductosAlta = new MenuItem("Alta");
	MenuItem mnimenuProductosMod = new MenuItem("Modificaci�n");
	MenuItem mnimenuProductosBaja = new MenuItem("Baja");
	MenuItem mnimenuProductosConsulta = new MenuItem("Consulta");
	
	// Crear Items dentro del men� Compra
	MenuItem mnimenuCompraAlta = new MenuItem("Alta");
	MenuItem mnimenuCompraConsulta = new MenuItem("Consulta");

	// Crear Item dentro del men� Ayuda
	MenuItem mnimenuAyudaVer = new MenuItem("Ver ayuda");
	
	MenuPrincipal()
	{
		colocarIcono();
		// A�adir la barra de Men�
		miMenu.setMenuBar(barraMenu);
		barraMenu.add(menuClientes);
		barraMenu.add(menuEmpleados);
		barraMenu.add(menuProductos);
		barraMenu.add(menuCompra);
		barraMenu.add(menuAyuda);

		// A�adir al men� Clientes los item para que se vean
		menuClientes.add(mnimenuClientesAlta);
		menuClientes.add(mnimenuClientesMod);
		menuClientes.add(mnimenuClientesBaja);
		menuClientes.add(mnimenuClientesConsulta);

		// A�adir al men� Empleados los item para que se vean
		menuEmpleados.add(mnimenuEmpleadosAlta);
		menuEmpleados.add(mnimenuEmpleadosMod);
		menuEmpleados.add(mnimenuEmpleadosBaja);
		menuEmpleados.add(mnimenuEmpleadosConsulta);

		// A�adir al men� Productos los item para que se vean
		menuProductos.add(mnimenuProductosAlta);
		menuProductos.add(mnimenuProductosMod);
		menuProductos.add(mnimenuProductosBaja);
		menuProductos.add(mnimenuProductosConsulta);

		// A�adir al men� Compra los item para que se vean
		menuCompra.add(mnimenuCompraAlta);
		menuCompra.add(mnimenuCompraConsulta);
		
		// A�adir al men� Ayuda los item para que se vean
		menuAyuda.add(mnimenuAyudaVer);
		
		// Action Listeners para los MenuITEM de Clientes
		mnimenuClientesAlta.addActionListener(this);
		mnimenuClientesMod.addActionListener(this);
		mnimenuClientesBaja.addActionListener(this);
		mnimenuClientesConsulta.addActionListener(this);
		
		// Listeners de Empleados
		mnimenuEmpleadosAlta.addActionListener(this);
		mnimenuEmpleadosMod.addActionListener(this);
		mnimenuEmpleadosBaja.addActionListener(this);
		mnimenuEmpleadosConsulta.addActionListener(this);
		
		// Listeners de Productos
		mnimenuProductosAlta.addActionListener(this);
		mnimenuProductosMod.addActionListener(this);
		mnimenuProductosBaja.addActionListener(this);
		mnimenuProductosConsulta.addActionListener(this);
		
		// Listeners de Compra
		mnimenuCompraAlta.addActionListener(this);
		mnimenuCompraConsulta.addActionListener(this);
		
		// Listeners de Ayuda
		mnimenuAyudaVer.addActionListener(this);
		
		// Establecer tama�o a la ventana y localizaci�n en pantalla
		miMenu.setSize(500,300);
		// No permitir maximizarlo, cambiar tama�o
		miMenu.setResizable(false);
		
		// Establecer al medio la ventana, dependiendo de cual sea la resoluci�n.
		miMenu.setLocationRelativeTo(null);
		miMenu.addWindowListener(this);
		// No hacer visible el men�
		miMenu.setVisible(true);
	}

	public void colocarIcono() {
		Toolkit mipantalla = Toolkit.getDefaultToolkit();
		Image miIcono = mipantalla.getImage("farmacia.png");
		miMenu.setIconImage(miIcono);
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		Guardar_Movimientos f = new Guardar_Movimientos();
		try {
			f.registrar("administrador]" + "[Logout");
		} catch (IOException ie) {
			ie.printStackTrace();
		}	
		
		System.exit(0);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (mnimenuClientesAlta.equals(arg0.getSource())) {
			new AltaClientes();
			miMenu.setVisible(false);
		}
		
		else if (mnimenuClientesMod.equals(arg0.getSource())) {
			new ModificacionClientes();
			miMenu.setVisible(false);
		}
		
		else if (mnimenuClientesBaja.equals(arg0.getSource())) {
			new BajaClientes();
			miMenu.setVisible(false);
		}
		
		else if (mnimenuClientesConsulta.equals(arg0.getSource())) {
			new ConsultaClientes();
			miMenu.setVisible(false);
		}
		
		else if (mnimenuEmpleadosAlta.equals(arg0.getSource())) {
			new AltaEmpleados();
			miMenu.setVisible(false);
		}
		
		else if (mnimenuEmpleadosMod.equals(arg0.getSource())) {
			new ModificacionEmpleados();
			miMenu.setVisible(false);
		}
		
		else if (mnimenuEmpleadosBaja.equals(arg0.getSource())) {
			new BajaEmpleados();
			miMenu.setVisible(false);
		}
		
		else if (mnimenuEmpleadosConsulta.equals(arg0.getSource())) {
			new ConsultaEmpleados();
			miMenu.setVisible(false);
		}
		
		else if (mnimenuProductosAlta.equals(arg0.getSource())) {
			new AltaProductos();
			miMenu.setVisible(false);
		}
		
		else if (mnimenuProductosMod.equals(arg0.getSource())) {
			new ModificacionProductos();
			miMenu.setVisible(false);
		}
		
		else if (mnimenuProductosBaja.equals(arg0.getSource())) {
			new BajaProductos();
			miMenu.setVisible(false);
		}
		
		else if (mnimenuProductosConsulta.equals(arg0.getSource())) {
			new ConsultaProductos();
			miMenu.setVisible(false);
		}
		
		else if (mnimenuCompraAlta.equals(arg0.getSource())) {
			new AltaCompra();
			miMenu.setVisible(false);
		}
		
		else if (mnimenuCompraConsulta.equals(arg0.getSource())) {
			new ConsultaCompra();
			miMenu.setVisible(false);
		}
		
		else if (mnimenuAyudaVer.equals(arg0.getSource())) {
			Guardar_Movimientos f = new Guardar_Movimientos();
			try {
				f.registrar("administrador]" + "[Ayuda");
			} catch (IOException e) {
				e.printStackTrace();
			}		
			
			new Ayuda();
			miMenu.setVisible(false);
		}	
	}
}