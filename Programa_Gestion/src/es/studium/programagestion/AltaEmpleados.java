package es.studium.programagestion;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

public class AltaEmpleados extends Frame implements ActionListener, WindowListener{

	private static final long serialVersionUID = 1L;
	
	// Crear componentes 
	Label lblNombre = new Label("Nombre:");
	TextField txt = new TextField();
	Label lblApellidos = new Label("Apellidos:");
	TextField txtApellidos = new TextField();
	Button btnAlta = new Button("Alta");
	Button btnLimpiar = new Button("Limpiar");
	
	// Alta Correcta
	Dialog AltaCorrecta = new Dialog(this, true);
	
	// Alta Incorrecta
	Dialog AltaInCorrecta = new Dialog(this, true);
	
	// Componentes Diálogo CORRECTO
	Label CAlta = new Label("Alta Correcta");
	Button btnAceptar = new Button("Aceptar");
	
	// Componentes Diálogo Incorrecto
	Label AltaIn = new Label("Se ha producido un error en el Alta");
	Button btnAceptar1 = new Button("Aceptar");
	
	AltaEmpleados()
	{
		// Almacenamos en mipantalla el sistema nativo de pantallas, el tamaño por defecto de la pantalla, nos servira para poner el icono
		Toolkit mipantalla = Toolkit.getDefaultToolkit();
		
		setTitle("Alta Empleados");
		setLayout(new GridLayout(3,2));
		add(lblNombre);
		add(txt);
		add(lblApellidos);
		add(txtApellidos);
		add(btnAlta);
		add(btnLimpiar);
		
		// Diálogo Correcto
		AltaCorrecta.setTitle("Alta Empleados");
		AltaCorrecta.setLayout(new FlowLayout());
		AltaCorrecta.add(CAlta);
		AltaCorrecta.add(btnAceptar);
		AltaCorrecta.setSize(140,100);
		AltaCorrecta.setLocationRelativeTo(null);
		AltaCorrecta.setResizable(false);
		AltaCorrecta.setVisible(false);
		
		// Diálogo Incorrecto
		AltaInCorrecta.setTitle("Alta Empleados");
		AltaInCorrecta.setLayout(new FlowLayout());
		AltaInCorrecta.add(AltaIn);
		AltaInCorrecta.add(btnAceptar1);
		AltaInCorrecta.setSize(250,100);
		AltaInCorrecta.setLocationRelativeTo(null);
		AltaInCorrecta.setResizable(false);
		AltaInCorrecta.setVisible(false);
		
		// Establecer un icono a la aplicación
		Image miIcono = mipantalla.getImage("src//farmacia.png");
		// Colocar Icono
		setIconImage(miIcono);
		setSize(300,300);
		setLocationRelativeTo(null);
		btnAlta.addActionListener(this);
		btnLimpiar.addActionListener(this);
		btnAceptar.addActionListener(this);
		btnAceptar1.addActionListener(this);
		AltaCorrecta.addWindowListener(this);
		AltaInCorrecta.addWindowListener(this);
		addWindowListener(this);
		setResizable(false);
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		String Nombre = txt.getText();
		String Apellidos = txtApellidos.getText();

		if (btnAlta.equals(arg0.getSource())) {
			// Si el contenido de los tres TEXT FIELD están vacíos que lanze la ventana de ALTA INCORRECTA
			if (Nombre.equals("") &&  (Apellidos.equals(""))){
				AltaInCorrecta.setVisible(true);
				this.setVisible(false);
			}else{
				AltaCorrecta.setVisible(true);
				this.setVisible(false);
			}
		}
		
		if (btnLimpiar.equals(arg0.getSource())){
			// Seleccionar contenido del textField Nombre y limpiarlo
			txt.selectAll();
			txt.setText("");

			// Seleccionar contenido del textField APELLIDOS Y limpiarlo
			txtApellidos.selectAll();
			txtApellidos.setText("");
		}
		
		if (btnAceptar.equals(arg0.getSource())) {
			AltaCorrecta.setVisible(false);
			new AltaEmpleados();
			
			Guardar_Movimientos gm = new Guardar_Movimientos();
			try {
				gm.registrar("admin]" + "[Alta Empleados");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		
		if (btnAceptar1.equals(arg0.getSource())) {
			AltaInCorrecta.setVisible(false);
			new AltaEmpleados();
		}
	}
	
	@Override
	public void windowClosing(WindowEvent arg0) {
		if (this.isActive()) {
			this.setVisible(false);
			new MenuPrincipal(null);
		}else if(AltaCorrecta.isActive()){
			AltaCorrecta.setVisible(false);
			new AltaEmpleados();
		}else if(AltaInCorrecta.isActive()) {
			AltaInCorrecta.setVisible(false);
			new AltaEmpleados();
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