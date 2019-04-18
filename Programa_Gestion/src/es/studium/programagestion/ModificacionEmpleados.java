package es.studium.programagestion;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

public class ModificacionEmpleados extends Frame implements ActionListener, WindowListener{

	private static final long serialVersionUID = 1L;
	
	// Crear componentes
	Choice choseleccion = new Choice();
	Button btnMod = new Button("Modificaci�n");
	
	// Panel para el Choice y el bot�n
	Panel pnlChoice = new Panel();
	Panel pnlBoton = new Panel();
	
	// Crear di�logo
	Dialog DialogoMod = new Dialog(this, true);
	// Di�logo Modificaci�n Correcta
	Dialog DialogoCorrecto = new Dialog(this, true);
	// Di�logo Modificaci�n Incorrecta
	Dialog DialogoIncorrecto = new Dialog(this, true);
	
	// Componentes para el di�logo correcto
	Label lblCorrecto = new Label("Modificaci�n Correcta");
	Button btnAceptar = new Button("Aceptar");
	
	// Componentes Di�logo Incorrecto
	Label lblAltaIn = new Label("Se ha producido un error en la Modificaci�n");
	Button btnAceptarIn = new Button("Aceptar");
	
	// Crear componentes
	Label lblNombre = new Label("Nombre:");
	TextField txt = new TextField();
	Label lblApellidos = new Label("Apellidos");
	TextField txtApellidos = new TextField();
	Label lblDNI = new Label("DNI:");
	TextField txtDNI = new TextField();
	Button btnModificacion = new Button("Modificaci�n Empleados");
	Button btnLimpiar = new Button("Limpiar");
		
	ModificacionEmpleados()
	{
		// Almacenamos en mipantalla el sistema nativo de pantallas el tama�o por defecto de la pantalla, nos servir� para poner el icono
		Toolkit mipantalla = Toolkit.getDefaultToolkit();
		
		setTitle("Modificaci�n Empleados");
		// Posici�n a los dos paneles el panel del Choice al NORTE y el del bot�n al CENTRO
		add(pnlChoice, "North");
		add(pnlBoton, "Center");
		// A�adir elementos al choice (lista)
		choseleccion.add("Pepito Pepito");
		choseleccion.add("Manuel Rojas");
		choseleccion.add("Alberto Men�ndez");
		// A�adir la lista
		pnlChoice.add(choseleccion);
		// A�adir el bot�n al panel
		pnlBoton.add(btnMod);
		// A�adir Windowlistener
		addWindowListener(this);
		// A�adir un Actionlistener al bot�n de Modificaci�n 
		btnMod.addActionListener(this);
		btnLimpiar.addActionListener(this);
		btnModificacion.addActionListener(this);
		
		// Di�logo donde se realiza la modificaci�n
		DialogoMod.setTitle("Modificaci�n Empleados");
		DialogoMod.setLayout(new GridLayout(4,2));
		DialogoMod.add(lblNombre);
		DialogoMod.add(txt);
		DialogoMod.add(lblApellidos);
		DialogoMod.add(txtApellidos);
		DialogoMod.add(lblDNI);
		DialogoMod.add(txtDNI);
		DialogoMod.add(btnModificacion);
		DialogoMod.add(btnLimpiar);
		DialogoMod.setSize(300,250);
		DialogoMod.setResizable(false);
		DialogoMod.setLocationRelativeTo(null);
		DialogoMod.addWindowListener(this);
		DialogoMod.setVisible(false);
		
		DialogoCorrecto.setTitle("Modificaci�n Empleados");
		DialogoCorrecto.setLayout(new FlowLayout());
		DialogoCorrecto.add(lblCorrecto);
		DialogoCorrecto.add(btnAceptar);
		DialogoCorrecto.setSize(200,100);
		DialogoCorrecto.setLocationRelativeTo(null);
		DialogoCorrecto.setResizable(false);
		DialogoCorrecto.addWindowListener(this);
		DialogoCorrecto.setVisible(false);
		
		DialogoIncorrecto.setTitle("Modificaci�n Empleados");
		DialogoIncorrecto.setLayout(new FlowLayout());
		DialogoIncorrecto.add(lblAltaIn);
		DialogoIncorrecto.add(btnAceptarIn);
		DialogoIncorrecto.setSize(280,100);
		DialogoIncorrecto.setLocationRelativeTo(null);
		DialogoIncorrecto.setResizable(false);
		DialogoIncorrecto.addWindowListener(this);
		DialogoIncorrecto.setVisible(false);
		
		// Dem�s botones con su Listener
		btnAceptar.addActionListener(this);
		btnAceptarIn.addActionListener(this);
		
		// Establecer un icono a la aplicaci�n
		Image miIcono = mipantalla.getImage("src//farmacia.png");
		// Colocar Icono
		setIconImage(miIcono);
		// Tama�o, Resizable y Hacer Visible
		// Para que aparezca el frame en centro de la pantalla 
		setSize(300,100);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		String Nombre = txt.getText();
		String Apellidos = txtApellidos.getText();
		String DNI = txtDNI.getText();
		
		if (btnModificacion.equals(ae.getSource())) {
			// Si el contenido de los tres TEXT FIELD est�n vac�os que lanze la ventana de ALTA INCORRECTA
			if (Nombre.equals("") &&  (Apellidos.equals("")) && (DNI.equals(""))){
				this.setVisible(false);
				DialogoIncorrecto.setVisible(true);
			}else{
				this.setVisible(false);
				DialogoCorrecto.setVisible(true);
			}
		}

		if (btnLimpiar.equals(ae.getSource())) {
			// Seleccionar Nombre y limpiarlo
			txt.selectAll();
			txt.setText("");
			
			// Seleccionar Los Apellidos y limpiarlo
			txtApellidos.selectAll();
			txtApellidos.setText("");
			
			// Seleccionar DNI y Limpiarlo
			txtDNI.selectAll();
			txtDNI.setText("");
		}
		
		if (btnMod.equals(ae.getSource())) {
			this.setVisible(false);
			DialogoMod.setVisible(true);
		}
		
		if (btnAceptar.equals(ae.getSource())) {
			Guardar_Movimientos f = new Guardar_Movimientos();
			try {
				f.registrar("admin]" + "[Modificaci�n Empleados");
			} catch (IOException e) {
				e.printStackTrace();
			}		
			
			DialogoCorrecto.setVisible(false);
			DialogoMod.setVisible(true);
		}
		
		if (btnAceptarIn.equals(ae.getSource())) {
			DialogoIncorrecto.setVisible(false);
			DialogoMod.setVisible(true);
		}
	}
	
	@Override
	public void windowClosing(WindowEvent arg0) {
		if (this.isActive()) {
			setVisible(false);
			new MenuPrincipal(null);
		}
		else if(DialogoMod.isActive()) {
			DialogoMod.setVisible(false);
			new ModificacionEmpleados();
		}

		else if (DialogoCorrecto.isActive()) {
			DialogoCorrecto.setVisible(false);
			DialogoMod.setVisible(true);
		}
		
		else if (DialogoIncorrecto.isActive()) {
			DialogoIncorrecto.setVisible(false);
			DialogoMod.setVisible(true);
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