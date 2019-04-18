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

public class ConsultaEmpleados extends Frame implements ActionListener, WindowListener{

	private static final long serialVersionUID = 1L;
	
	// Crear componentes 
	Choice choseleccion = new Choice();
	Button btnConsultar = new Button("Consulta");
	Button btnVolver = new Button("Volver");
	
	// Paneles
	Panel pnlChoice = new Panel();
	Panel pnlCentral = new Panel();
	
	// Diálogo donde aparecen los campos
	Dialog diaEmpleados = new Dialog(this,true);
	
	// Componentes para el diálogo
	Label lblNombre = new Label("Nombre:");
	Label Nombre = new Label("Aquí aparece el Nombre");
	Label lblApellidos = new Label("Apellidos:");
	Label Apellidos = new Label("Aquí aparece los Apellidos");
	Button Volver = new Button("Volver");
	
	ConsultaEmpleados()
	{
		// Almacenamos en mipantalla el sistema nativo de pantallas, el tamaño por defecto de la pantalla, nos servira para poner el icono
		Toolkit mipantalla = Toolkit.getDefaultToolkit();
		
		setTitle("Consulta Empleados");
		choseleccion.add("1. Empleado");
		choseleccion.add("2. Empleado");
		choseleccion.add("3. Empleado");
		pnlChoice.add(choseleccion);
		pnlCentral.add(btnConsultar);
		pnlCentral.add(btnVolver);
		// Añadir los paneles
		add(pnlChoice, "North");
		add(pnlCentral, "Center");

		// Diálogo
		diaEmpleados.setTitle("Consulta Empleados");
		diaEmpleados.setLayout(new FlowLayout());
		diaEmpleados.add(lblNombre);
		diaEmpleados.add(Nombre);
		diaEmpleados.add(lblApellidos);
		diaEmpleados.add(Apellidos);
		diaEmpleados.add(Volver);
		diaEmpleados.setSize(185,220);
		diaEmpleados.setLocationRelativeTo(null);
		diaEmpleados.addWindowListener(this);
		Volver.addActionListener(this);
		diaEmpleados.setResizable(false);
		diaEmpleados.setVisible(false);		
		
		// Establecer un icono a la aplicación
		Image miIcono = mipantalla.getImage("src//farmacia.png");
		// Colocar Icono
		setIconImage(miIcono);
		setSize(300,170);
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
				f.registrar("admin]" + "[Consulta Empleados");
			} catch (IOException e) {
				e.printStackTrace();
			}		
			
			this.setVisible(false);
			diaEmpleados.setVisible(true);
		}
		else if(btnVolver.equals(arg0.getSource())){
			this.setVisible(false);
			new MenuPrincipal(null);
		}
		else if(Volver.equals(arg0.getSource())) {
			diaEmpleados.setVisible(false);
			setVisible(true);
		}
	}
	
	@Override
	public void windowClosing(WindowEvent arg0) {
		if (diaEmpleados.isActive()) {
			diaEmpleados.setVisible(false);
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