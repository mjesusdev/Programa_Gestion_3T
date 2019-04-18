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

public class AltaProductosUsuario extends Frame implements ActionListener, WindowListener{

	private static final long serialVersionUID = 1L;
	
	// Componentes
	Label lblNombre = new Label("Nombre:");
	TextField txt = new TextField();
	Label lblPrecio = new Label("Precio");
	TextField txtPrecio = new TextField();
	Label lblFC = new Label("Fecha Caducidad:");
	TextField txtFC = new TextField();
	Label lblCT = new Label("Contenido Total:");
	TextField txtCT = new TextField();
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
	
	AltaProductosUsuario()
	{
		// Almacenamos en mipantalla el sistema nativo de pantallas, el tamaño por defecto de la pantalla, nos servira para poner el icono
		Toolkit mipantalla = Toolkit.getDefaultToolkit();
		
		setTitle("Alta Productos");
		setLayout(new GridLayout(5,2));
		add(lblNombre);
		add(txt);
		add(lblPrecio);
		add(txtPrecio);
		add(lblFC);
		add(txtFC);
		add(lblCT);
		add(txtCT);
		add(btnAlta);
		add(btnLimpiar);
		
		// Diálogo Correcto
		AltaCorrecta.setTitle("Alta Productos");
		AltaCorrecta.setLayout(new FlowLayout());
		AltaCorrecta.add(CAlta);
		AltaCorrecta.add(btnAceptar);
		AltaCorrecta.setSize(130,100);
		AltaCorrecta.setLocationRelativeTo(null);
		AltaCorrecta.setResizable(false);
		AltaCorrecta.setVisible(false);
		
		// Diálogo Incorrecto
		AltaInCorrecta.setTitle("Alta Productos");
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
		String Precio = txtPrecio.getText();
		String Fecha = txtFC.getText();
		String Contenido = txtCT.getText();

		if (btnAlta.equals(arg0.getSource())) {
			// Si el contenido de los tres TEXT FIELD están vacíos que lanze la ventana de ALTA INCORRECTA
			if (Nombre.equals("") &&  (Precio.equals("") && (Fecha.equals("") && (Contenido.equals(""))))){
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

			// Seleccionar contenido del textField Precio Y limpiarlo
			txtPrecio.selectAll();
			txtPrecio.setText("");
			
			// Seleccionar contenido del textField Fecha Y limpiarlo
			txtFC.selectAll();
			txtFC.setText("");
			
			// Seleccionar contenido del textField Contenido Total Y limpiarlo
			txtCT.selectAll();
			txtCT.setText("");
		}
		
		if (btnAceptar.equals(arg0.getSource())) {
			Guardar_Movimientos gm = new Guardar_Movimientos();
			try {
				gm.registrar("Usuario]" + "[Alta Productos");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			AltaCorrecta.setVisible(false);
			new AltaProductosUsuario();
		}
		
		if (btnAceptar1.equals(arg0.getSource())) {
			AltaInCorrecta.setVisible(false);
			new AltaProductosUsuario();
		}
	}
	
	@Override
	public void windowClosing(WindowEvent arg0) {
		if (this.isActive()) {
			this.setVisible(false);
			new MenuPrincipalUsuario();
		}else if(AltaCorrecta.isActive()){
			AltaCorrecta.setVisible(false);
			new AltaProductosUsuario();
		}else if(AltaInCorrecta.isActive()) {
			AltaInCorrecta.setVisible(false);
			new AltaProductosUsuario();
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