package es.studium.programagestion;

import java.awt.BorderLayout;
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

public class BajaEmpleados extends Frame implements ActionListener, WindowListener{

	private static final long serialVersionUID = 1L;
	
	// Crear componentes 
	Choice chcSeleccion = new Choice();
	Button btnBaja = new Button("Baja");
	
	// Paneles para BAJA CLIENTES
	Panel pnlChoice = new Panel(); 
	Panel pnlBaja = new Panel();
	
	// Dialogo Informativo 
	Dialog diainformativo = new Dialog(this, true);
	// Componentes
	Label lblConfirmacion = new Label("¿Estás seguro de realizar la Baja?");
	Button btnSi = new Button("Sí");
	Button btnNo = new Button("No");
	
	// Diálogo Baja Correcta
	Dialog bajaCorrecta = new Dialog(this, true);
	//Componentes Baja Correcta
	Label lblBC = new Label("Baja Correcta");
	Button btnAceptar = new Button("Aceptar");
	
	BajaEmpleados()
	{
		// Almacenamos en mipantalla el sistema nativo de pantallas, el tamaño por defecto de la pantalla, nos servira para poner el icono
		Toolkit mipantalla = Toolkit.getDefaultToolkit();
		
		setTitle("Baja Empleados");
		//setLayout(new FlowLayout());
		chcSeleccion.add("Manolo Escobar");
		chcSeleccion.add("Ada Fernández");
		pnlChoice.add(chcSeleccion);
		pnlBaja.add(btnBaja);
		add(pnlChoice, "North");
		add(pnlBaja, BorderLayout.CENTER);
		
		// Componentes diálogo informativo
		diainformativo.setTitle("Comprobación");
		diainformativo.setLayout(new FlowLayout());
		diainformativo.add(lblConfirmacion);
		diainformativo.add(btnSi);
		diainformativo.add(btnNo);
		diainformativo.setSize(220,100);
		diainformativo.addWindowListener(this);
		btnBaja.addActionListener(this);
		btnSi.addActionListener(this);
		btnNo.addActionListener(this);
		diainformativo.setLocationRelativeTo(null);
		diainformativo.setResizable(false);
		diainformativo.setVisible(false);
		
		// Componentes diálogo Baja Correcta
		bajaCorrecta.setTitle("Baja Correcta");
		bajaCorrecta.setLayout(new FlowLayout());
		bajaCorrecta.add(lblBC);
		bajaCorrecta.add(btnAceptar);
		btnAceptar.addActionListener(this);
		bajaCorrecta.setSize(150,110);
		bajaCorrecta.addWindowListener(this);
		bajaCorrecta.setLocationRelativeTo(null);
		bajaCorrecta.setResizable(false);
		bajaCorrecta.setVisible(false);
		
		// Establecer un icono a la aplicación
		Image miIcono = mipantalla.getImage("src//farmacia.png");
		// Colocar Icono
		setIconImage(miIcono);
		setSize(300,130);
		addWindowListener(this);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (btnBaja.equals(arg0.getSource())){
			diainformativo.setVisible(true);
		}
		
		if (btnSi.equals(arg0.getSource())) {
			bajaCorrecta.setVisible(true);
		}
		
		if(btnNo.equals(arg0.getSource())) {
			diainformativo.setVisible(false);
			setVisible(true);
		}
		
		if (btnAceptar.equals(arg0.getSource())) {
			Guardar_Movimientos f = new Guardar_Movimientos();
			try {
				f.registrar("admin]" + "[Baja Empleados");
			} catch (IOException e) {
				e.printStackTrace();
			}		
			
			bajaCorrecta.setVisible(false);
			diainformativo.setVisible(false);
			setVisible(true);
		}
	}
	
	@Override
	public void windowClosing(WindowEvent arg0) {
		if (this.isActive()) {
			setVisible(false);
			new MenuPrincipal(null);
		}else if(diainformativo.isActive()){
			diainformativo.setVisible(false);
			this.setVisible(true);
		}else if(bajaCorrecta.isActive()){
			bajaCorrecta.setVisible(false);
			diainformativo.setVisible(false);
			setVisible(true);
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