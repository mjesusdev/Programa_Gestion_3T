package es.studium.programagestion;

import java.awt.BorderLayout;
import java.awt.Button;
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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class Programa_Gestion implements WindowListener, ActionListener{

	// Crear una ventana
	Frame miPrograma = new Frame("Farmacia");
	// Crear un par de etiquetas una para el usuario y otra para la clave
	Label lblUsuario = new Label("   Usuario:");
	// Crear dos Campos de Texto para poder escribir el usuario y la clave
	TextField txtUsuario = new TextField(18);
	Label lblClave = new Label("   Clave:    ");
	TextField txtClave = new TextField(18);
	// Tres botones
	Button btnLogin = new Button("Login");
	Button btnLimpiar = new Button("Limpiar");
	Button btnOlvidar = new Button("Olvidé la clave");

	// Panel para olvidar la Clave
	Panel pnlOlvidar = new Panel();

	// Diálogo para la recuperación de la cuenta
	Dialog recuperacion = new Dialog(miPrograma, true);

	// Diálogo para cuando ya se ha puesto el correo para la recuperación
	Dialog recuperacion2 = new Dialog(miPrograma, true);

	// Diálogo para cuando ya se ha puesto el correo para la recuperación
	Dialog recuperacionIncorrecta = new Dialog(miPrograma, true);

	// Componentes para el login incorrecto
	Label lblIncorrecto = new Label("Datos de Login incorrectos");
	Button btnAceptarIn = new Button("Aceptar");

	// Paneles para el Frame Principal
	Panel pnlSuperior1 = new Panel();
	Panel pnlCentral1 = new Panel();

	// SubPanel Central 
	Panel pnlCentralSub = new Panel();
	Panel pnlInferior1 = new Panel();
	Panel pnlInferior2 = new Panel();

	// Paneles para el Diálogo
	Panel pnlSuperior = new Panel();
	Panel pnlCentral = new Panel();
	Panel pnlInferior = new Panel();

	// Componentes para el diálogo
	Label lblIndicar = new Label("Indicar tu correo electrónico");
	TextField txtCorreo = new TextField(18);
	Button btnAceptar = new Button("Aceptar");

	// Componentes para el Segundo Diálogo
	Label lblCorreo = new Label("Se ha enviado un correo electrónico a su cuenta");
	Button btnAceptar2 = new Button("Aceptar");

	// Paneles para el Segundo Diálogo
	Panel pnlSuperior2 = new Panel();
	Panel pnlCentral2 = new Panel();

	// Componentes para el diálogo RECUPERACIÓN INCORRECTA
	Label lblNoExiste = new Label("No existe una cuenta con ese correo electrónico");
	Button btnAceptarRI = new Button("Aceptar");

	// Base De datos
	String driver = "com.mysql.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/farmaciapr2?autoReconnect=true&useSSL=false";
	String login = "admin";
	String password = "Studium2018;";
	String sentencia = null;
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;

	Programa_Gestion()
	{
		// Almacenamos en mipantalla el sistema nativo de pantallas, el tamaño por defecto de la pantalla, nos servirá para poner el icono
		Toolkit mipantalla = Toolkit.getDefaultToolkit();

		// Establecer Layout para 2 filas y 4 columnas
		miPrograma.setLayout(new GridLayout(4,2));
		// Añadir etiquetas, campos de texto, etc.
		pnlSuperior1.add(lblUsuario);
		pnlSuperior1.add(txtUsuario);
		pnlCentralSub.add(lblClave);
		pnlCentralSub.add(txtClave);
		pnlInferior1.add(btnLogin);
		pnlInferior1.add(btnLimpiar);
		pnlInferior2.add(btnOlvidar);
		// Localización Paneles
		miPrograma.add(pnlSuperior1, BorderLayout.CENTER);
		miPrograma.add(pnlCentral1, BorderLayout.CENTER);
		miPrograma.add(pnlInferior1, BorderLayout.CENTER);
		miPrograma.add(pnlInferior2, BorderLayout.CENTER);
		pnlCentral1.add(pnlCentralSub, BorderLayout.CENTER);

		// Reemplazar contenido de txtClave por asterísco:
		txtClave.setEchoChar('*');
		// Establecer Valores para posicionar el frame en la pantalla
		miPrograma.setBounds(800,400,400,200);
		// Establecer al medio la ventana, dependiendo de cual sea la resolución.
		miPrograma.setLocationRelativeTo(null);
		// Impedir cambios en la ventana, dejarla por defecto como lo ha puesto el programador
		miPrograma.setResizable(false);
		// Añadir el WindowListener
		miPrograma.addWindowListener(this);
		// Título al diálogo
		recuperacion.setTitle("Olvidé la clave");
		// Título al diálogo2
		recuperacion2.setTitle("Correcto");
		// Título al diálogo Recuperación Incorrecta
		recuperacionIncorrecta.setTitle("Incorrecto");
		recuperacionIncorrecta.setLayout(new FlowLayout());
		recuperacionIncorrecta.setSize(300,100);
		recuperacionIncorrecta.setLocationRelativeTo(null);
		recuperacionIncorrecta.setResizable(false);
		recuperacionIncorrecta.addWindowListener(this);
		recuperacionIncorrecta.setVisible(false);

		// Añadir componentes Recuperación Incorrecta cuando tienes que especificar el correo
		recuperacionIncorrecta.add(lblNoExiste);
		recuperacionIncorrecta.add(btnAceptarRI);

		// Tamaño al diálogo, localización y listener
		recuperacion.setSize(200,150);
		recuperacion.setResizable(false);
		recuperacion.setLocationRelativeTo(null);
		recuperacion.addWindowListener(this);
		// Tamaño al diálogo 2
		recuperacion2.setSize(300,110);
		recuperacion2.setResizable(false);
		recuperacion2.setLocationRelativeTo(null);
		recuperacion2.addWindowListener(this);
		// Ubicación de los paneles
		recuperacion.add(pnlSuperior, "North");
		recuperacion.add(pnlCentral, "Center");
		recuperacion.add(pnlInferior, "South");

		// Añadirle al diálogo RECUPERACION los siguientes componentes
		pnlSuperior.add(lblIndicar);
		pnlCentral.add(txtCorreo);
		pnlInferior.add(btnAceptar);
		// Ubicación paneles en el segundo diálogo
		recuperacion2.add(pnlSuperior2, "North");
		recuperacion2.add(pnlCentral2, "Center");

		// Añadirle al segundo diálogo los siguientes componentes
		pnlSuperior2.add(lblCorreo);
		pnlCentral2.add(btnAceptar2);

		// Establecer un icono a la aplicación
		Image miIcono = mipantalla.getImage("farmacia.png");
		// Colocar Icono
		miPrograma.setIconImage(miIcono);

		// Añadir Listeners a los botones
		btnLogin.addActionListener(this);
		btnLimpiar.addActionListener(this);
		btnOlvidar.addActionListener(this);
		btnAceptar.addActionListener(this);
		btnAceptar2.addActionListener(this);
		btnAceptarRI.addActionListener(this);
		// Hacer visible el frame principal (INICIO DE SESIÓN)
		miPrograma.setVisible(true);
	}

	public static void main(String[] args) {
		new Programa_Gestion();
	}

	public void windowActivated(WindowEvent arg0) {}
	public void windowClosed(WindowEvent arg0) {}

	@Override
	public void windowClosing(WindowEvent arg0) {
		// Cuando el diálogo de recuperación del clave
		if(recuperacion.isActive()){
			recuperacion.setVisible(false);
			miPrograma.setVisible(true);
		}else if(recuperacion2.isActive()) {
			recuperacion2.setVisible(false);
			miPrograma.setVisible(true);
		}else if(recuperacionIncorrecta.isActive()) {
			recuperacionIncorrecta.setVisible(false);
			recuperacion.setVisible(true);
		}else if(miPrograma.isActive()) {
			System.exit(0);
		}
	}

	public void windowDeactivated(WindowEvent arg0) {}
	public void windowDeiconified(WindowEvent arg0) {}
	public void windowIconified(WindowEvent arg0) {}
	public void windowOpened(WindowEvent arg0) {}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (btnLogin.equals(arg0.getSource())){

			String usuario = txtUsuario.getText();

			if (usuario.equals("administrador")) {
				sentencia = "SELECT * FROM usuarios WHERE nombreUsuario = '"+txtUsuario.getText()+"' AND claveUsuario = MD5('" + txtClave.getText()+"');";

				//Cargar los controladores para el acceso a la BD
				try{
					Class.forName(driver);				
					//Establecer la conexión con la BD Empresa
					connection = DriverManager.getConnection(url, login, password);
					//Crear una sentencia
					statement = connection.createStatement();
					//Crear un objeto ResultSet para guardar lo obtenido y ejecutar la sentencia SQL
					rs = statement.executeQuery(sentencia);
					if(rs.next()){
						new MenuPrincipal();
						miPrograma.setVisible(false);
					}

					else{
						JOptionPane.showMessageDialog(null, "Se ha esquivocado en el usuario o la contraseña", "Error", JOptionPane.ERROR_MESSAGE);
					}
				} 

				catch (ClassNotFoundException e) {
					JOptionPane.showMessageDialog(null, "No se ha cargado el driver debido a que no está disponible", "Error", JOptionPane.ERROR_MESSAGE);
				}

				catch(SQLException e) {
					JOptionPane.showMessageDialog(null, "Se ha producido un error al conectarse con la BD", "Error", JOptionPane.ERROR_MESSAGE);
				}

				// Bloque para intentar cerrar la conexión la BD
				try {
					if (connection!=null) {
						connection.close();
					}	
				}catch(SQLException sqle) {
					JOptionPane.showMessageDialog(null, "Se ha producido un problema al acceder a la Base de Datos o similar", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}

			else {
				sentencia = "SELECT * FROM usuarios WHERE nombreUsuario = '"+txtUsuario.getText()+"' AND claveUsuario = MD5('" + txtClave.getText()+"');";

				//Cargar los controladores para el acceso a la BD
				try{
					Class.forName(driver);				
					//Establecer la conexión con la BD Empresa
					connection = DriverManager.getConnection(url, login, password);
					//Crear una sentencia
					statement = connection.createStatement();
					//Crear un objeto ResultSet para guardar lo obtenido y ejecutar la sentencia SQL
					rs = statement.executeQuery(sentencia);
					if(rs.next()){
						new MenuPrincipalUsuario();
						miPrograma.setVisible(false);
					}

					else{
						JOptionPane.showMessageDialog(null, "Se ha esquivocado en el usuario o la contraseña", "Error", JOptionPane.ERROR_MESSAGE);
					}
				} 

				catch (ClassNotFoundException e) {
					System.out.println("Se produjo un error al cargar el Driver");
				}

				catch(SQLException e) {
					JOptionPane.showMessageDialog(null, "Se ha producido un error al conectarse con la BD", "Error", JOptionPane.ERROR_MESSAGE);
				}

				// Bloque para intentar cerrar la conexión la BD
				try {
					if (connection!=null) {
						connection.close();
					}	
				}catch(SQLException sqle) {
					JOptionPane.showMessageDialog(null, "No se puede cerrar la conexión con la BD", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}

			// Crear objeto de la clase Fecha_Y_Hora para poder trabajar con el para guardar lo que se pone en el usuario (texto)
			Guardar_Movimientos f = new Guardar_Movimientos();
			try {
				// Llamando al método de fecha y mandarle lo que se escribe en el texto de inicio de sesión 
				f.registrar(txtUsuario.getText()+"]"+"[Login");
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (btnLimpiar.equals(arg0.getSource())){
			// Seleccionar contenido del textField Usuario y limpiarlo
			txtUsuario.selectAll();
			txtUsuario.setText("");

			// Seleccionar contenido del textField Clave y limpiarlo
			txtClave.selectAll();
			txtClave.setText("");
		}

		if (btnOlvidar.equals(arg0.getSource())) {
			// Hacer visible el diálogo
			recuperacion.setVisible(true);
		}

		if (btnAceptar.equals(arg0.getSource())){
			String Correo = txtCorreo.getText();

			if (Correo.contains("@")) {
				recuperacion.setVisible(false);
				recuperacion2.setVisible(true);
			}else{
				recuperacion.setVisible(false);
				recuperacionIncorrecta.setVisible(true);
			}
		}

		if (btnAceptar2.equals(arg0.getSource())){
			recuperacion2.setVisible(false);
			miPrograma.setVisible(true);
		}

		if (btnAceptarRI.equals(arg0.getSource())) {
			recuperacionIncorrecta.setVisible(false);
			recuperacion.setVisible(true);
		}
	}
}