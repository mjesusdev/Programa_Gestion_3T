package es.studium.programagestion;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

public class ConsultaProductos extends Frame implements ActionListener, WindowListener{

	private static final long serialVersionUID = 1L;
	
	// Crear componentes 
	Choice choseleccion = new Choice();
	Button btnConsultar = new Button("Consulta");
	Button btnVolver = new Button("Volver");
	
	// Paneles
	Panel pnlChoice = new Panel();
	Panel pnlCentral = new Panel();
	
	// Diálogo donde aparecen los campos
	Dialog diaProductos = new Dialog(this,true);
	
	// Componentes para el diálogo
	Label lblNombre = new Label("Nombre:");
	Label Nombre = new Label("Aquí aparece el Nombre");
	Label lblPrecio = new Label("Precio:");
	Label Precio = new Label("Aquí aparece el Precio");
	Label lblFC = new Label("Fecha Caducidad:");
	Label FechaC = new Label("Aquí aparece la Fecha");
	Label lblCT = new Label("Contenido Total:");
	Label ContenidoT = new Label("Aquí aparece el Contenido");
	Button Volver = new Button("Volver");
	
	ConsultaProductos()
	{
		// Almacenamos en mipantalla el sistema nativo de pantallas, el tamaño por defecto de la pantalla, nos servira para poner el icono
		Toolkit mipantalla = Toolkit.getDefaultToolkit();
		
		setTitle("Consulta Productos");
		choseleccion.add("1. Producto");
		choseleccion.add("2. Producto");
		pnlChoice.add(choseleccion);
		pnlCentral.add(btnConsultar);
		pnlCentral.add(btnVolver);
		// Añadir los paneles
		add(pnlChoice, "North");
		add(pnlCentral, "Center");

		// Diálogo
		diaProductos.setTitle("Consulta Productos");
		diaProductos.setLayout(new FlowLayout());
		diaProductos.add(lblNombre);
		diaProductos.add(Nombre);
		diaProductos.add(lblPrecio);
		diaProductos.add(Precio);
		diaProductos.add(lblFC);
		diaProductos.add(FechaC);
		diaProductos.add(lblCT);
		diaProductos.add(ContenidoT);
		diaProductos.add(Volver);
		diaProductos.setSize(185,300);
		diaProductos.setLocationRelativeTo(null);
		diaProductos.addWindowListener(this);
		Volver.addActionListener(this);
		diaProductos.setResizable(false);
		diaProductos.setVisible(false);		
		
		// Establecer un icono a la aplicación
		Image miIcono = mipantalla.getImage("src//farmacia.png");
		// Colocar Icono
		setIconImage(miIcono);
		setSize(290,150);
		setLocationRelativeTo(null);
		addWindowListener(this);
		btnConsultar.addActionListener(this);
		btnVolver.addActionListener(this);
		setResizable(false);
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (btnConsultar.equals(arg0.getSource())) {
			Guardar_Movimientos f = new Guardar_Movimientos();
			try {
				f.registrar("admin]" + "[Consulta Productos");
			} catch (IOException e) {
				e.printStackTrace();
			}		
			
			this.setVisible(false);
			diaProductos.setVisible(true);
		}
		
		else if(btnVolver.equals(arg0.getSource())){
			this.setVisible(false);
			new MenuPrincipal(null);
		}
		
		else if(Volver.equals(arg0.getSource())) {
			diaProductos.setVisible(false);
			setVisible(true);
		}
	}
	
	@Override
	public void windowClosing(WindowEvent arg0) {
		if (diaProductos.isActive()) {
			diaProductos.setVisible(false);
			setVisible(true);
		}
		
		else if(this.isActive()){
			this.setVisible(false);
			new MenuPrincipal(null);
		}
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub	
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub	
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
	}
}