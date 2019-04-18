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

public class ModificacionProductos extends Frame implements ActionListener, WindowListener{

	private static final long serialVersionUID = 1L;

	// Crear componentes
	Choice choseleccion = new Choice();
	Button btnModificar = new Button("Modificar");
	// Panel para el Choice y el bot�n
	Panel pnlChoice = new Panel();
	Panel pnlBoton = new Panel();
	
	// Crear di�logo DONDE SE REALIZA LA MODIFICACI�N
	Dialog DialogoMod = new Dialog(this, true);
	
	// Di�logo Modificaci�n Correcta
	Dialog DialogoCorrecto = new Dialog(this, true);
	
	// Di�logo Modificaci�n Incorrecta
	Dialog DialogoIncorrecto = new Dialog(this, true);
	
	// Componentes para el DI�LOGO CORRECTO
	Label lblCorrecto = new Label("Modificaci�n Correcta");
	Button btnAceptar = new Button("Aceptar");
	
	// Componentes Di�logo Incorrecto
	Label lblModIn = new Label("Se ha producido un error en la Modificaci�n");
	Button btnAceptarIn = new Button("Aceptar");
	
	// Crear componentes (DIALOGOMOD)
	Label lblNombre = new Label("Nombre:");
	TextField txt = new TextField();
	Label lblPrecio = new Label("Precio");
	TextField txtPrecio = new TextField();
	Label lblFC = new Label("Fecha Caducidad:");
	TextField txtFC = new TextField();
	Label lblCT = new Label("Contenido Total:");
	TextField txtCT = new TextField();
	Button btnModificacion = new Button("Modificaci�n");
	Button btnLimpiar = new Button("Limpiar");
	
	
	ModificacionProductos()
	{
		// Almacenamos en mipantalla el sistema nativo de pantallas el tama�o por defecto de la pantalla, nos servir� para poner el icono
		Toolkit mipantalla = Toolkit.getDefaultToolkit();
		
		setTitle("Modificaci�n Productos");
		// Posici�n a los dos paneles el panel del Choice al NORTE y el del bot�n al CENTRO
		add(pnlChoice, "North");
		add(pnlBoton, "Center");
		// A�adir elementos al choice (lista)
		choseleccion.add("Paracetamol");
		choseleccion.add("Ibuprofeno");
		// A�adir la lista
		pnlChoice.add(choseleccion);
		// A�adir el bot�n al panel
		pnlBoton.add(btnModificar);
		// A�adir Windowlistener
		addWindowListener(this);
		// A�adir un Actionlistener al bot�n de Modificaci�n 
		btnModificar.addActionListener(this);
		
		// Di�logo donde se realiza la modificaci�n
		DialogoMod.setTitle("Modificaci�n Productos");
		DialogoMod.setLayout(new GridLayout(5,2));
		DialogoMod.add(lblNombre);
		DialogoMod.add(txt);
		DialogoMod.add(lblPrecio);
		DialogoMod.add(txtPrecio);
		DialogoMod.add(lblFC);
		DialogoMod.add(txtFC);
		DialogoMod.add(lblCT);
		DialogoMod.add(txtCT);
		DialogoMod.add(btnModificacion);
		DialogoMod.add(btnLimpiar);
		DialogoMod.setSize(300,250);
		DialogoMod.setLocationRelativeTo(null);
		DialogoMod.addWindowListener(this);
		DialogoMod.setResizable(false);
		DialogoMod.setVisible(false);
		
		// Action Listners de DialogoMod
		btnLimpiar.addActionListener(this);
		btnModificacion.addActionListener(this);
		
		// Di�logo CORRECTO
		DialogoCorrecto.setTitle("Modificaci�n Productos");
		DialogoCorrecto.setLayout(new FlowLayout());
		DialogoCorrecto.add(lblCorrecto);
		DialogoCorrecto.add(btnAceptar);
		DialogoCorrecto.setSize(190,100);
		DialogoCorrecto.setLocationRelativeTo(null);
		DialogoCorrecto.setResizable(false);
		DialogoCorrecto.addWindowListener(this);
		DialogoCorrecto.setVisible(false);
		
		// Di�logo Incorrecto
		DialogoIncorrecto.setTitle("Modificaci�n Productos");
		DialogoIncorrecto.setLayout(new FlowLayout());
		DialogoIncorrecto.add(lblModIn);
		DialogoIncorrecto.add(btnAceptarIn);
		DialogoIncorrecto.setSize(280,100);
		DialogoIncorrecto.setLocationRelativeTo(null);
		DialogoIncorrecto.setResizable(false);
		DialogoIncorrecto.addWindowListener(this);
		DialogoIncorrecto.setVisible(false);
		
		// Action Listener para botones de los di�logos
		btnAceptar.addActionListener(this);
		btnAceptarIn.addActionListener(this);
		
		// Establecer un icono a la aplicaci�n
		Image miIcono = mipantalla.getImage("src//farmacia.png");
		// Colocar Icono
		setIconImage(miIcono);
		// Para que aparezca el frame en centro de la pantalla 
		setSize(300,100);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		String Nombre = txt.getText();
		String Precio = txtPrecio.getText();
		String CTotal = txtCT.getText();
		String Fecha = txtFC.getText();
		
		if (btnModificar.equals(arg0.getSource())) {
			this.setVisible(false);
			DialogoMod.setVisible(true);
		}
	
		if (btnModificacion.equals(arg0.getSource())) {
			// Si el contenido de los tres TEXT FIELD est�n vac�os que lanze la ventana de ALTA INCORRECTA
			if (Nombre.equals("") &&  (Precio.equals("")) && (CTotal.equals("") && (Fecha.equals("")))){
				this.setVisible(false);
				DialogoIncorrecto.setVisible(true);
			}else{
				this.setVisible(false);
				DialogoCorrecto.setVisible(true);
			}
		}
		
		if (btnLimpiar.equals(arg0.getSource())){
			// Seleccionar contenido del textField Nombre y limpiarlo
			txt.selectAll();
			txt.setText("");

			// Seleccionar contenido del textField APELLIDOS Y limpiarlo
			txtPrecio.selectAll();
			txtPrecio.setText("");
			
			// Seleccionar contendio del textField DNI Y limpiarlo
			txtFC.selectAll();
			txtFC.setText("");
			
			// Seleccionar contendio del textField DNI Y limpiarlo
			txtCT.selectAll();
			txtCT.setText("");
		}
		
		if (btnAceptar.equals(arg0.getSource())) {
			Guardar_Movimientos f = new Guardar_Movimientos();
			try {
				f.registrar("admin]" + "[Modificaci�n Productos");
			} catch (IOException e) {
				e.printStackTrace();
			}		
			
			DialogoCorrecto.setVisible(false);
			DialogoMod.setVisible(true);
		}
		
		if (btnAceptarIn.equals(arg0.getSource())) {
			DialogoIncorrecto.setVisible(false);
			DialogoMod.setVisible(true);
		}
	}
	
	@Override
	public void windowClosing(WindowEvent arg0) {
		if (this.isActive()) {
			this.setVisible(false);
			new MenuPrincipal(null);
		}
		
		else if(DialogoCorrecto.isActive()){
			DialogoCorrecto.setVisible(false);
			DialogoMod.setVisible(true);
		}
		
		else if(DialogoIncorrecto.isActive()) {
			DialogoIncorrecto.setVisible(false);
			DialogoMod.setVisible(true);
		}
		
		else if(DialogoMod.isActive()){
			DialogoMod.setVisible(false);
			new ModificacionProductos();
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