package es.studium.programagestion;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Programa_Gestion implements WindowListener, ActionListener, KeyListener{

	// Crear una ventana
	JFrame miPrograma = new JFrame("Farmacia");
	// Crear un par de etiquetas una para el usuario y otra para la clave
	JLabel lblUsuario = new JLabel("   Usuario:");
	// Crear dos Campos de Texto para poder escribir el usuario y la clave
	TextField txtUsuario = new TextField(18);
	JLabel lblClave = new JLabel("   Clave:    ");
	TextField txtClave = new TextField(18);
	// Tres botones
	JButton btnLogin = new JButton("Login");
	JButton btnLimpiar = new JButton("Limpiar");
	JButton btnOlvidar = new JButton("Olvid� la clave");

	// Panel para olvidar la Clave
	JPanel pnlOlvidar = new JPanel();

	// Di�logo para la recuperaci�n de la cuenta
	JDialog recuperacion = new JDialog(miPrograma, true);

	// Di�logo para cuando ya se ha puesto el correo para la recuperaci�n
	JDialog recuperacion2 = new JDialog(miPrograma, true);

	// Di�logo para cuando ya se ha puesto el correo para la recuperaci�n
	JDialog recuperacionIncorrecta = new JDialog(miPrograma, true);

	// Componentes para el login incorrecto
	JLabel lblIncorrecto = new JLabel("Datos de Login incorrectos");
	JButton btnAceptarIn = new JButton("Aceptar");

	// Paneles para el Frame Principal
	JPanel pnlSuperior1 = new JPanel();
	JPanel pnlCentral1 = new JPanel();

	// SubPanel Central 
	JPanel pnlCentralSub = new JPanel();
	JPanel pnlInferior1 = new JPanel();
	JPanel pnlInferior2 = new JPanel();

	// Paneles para el Di�logo
	JPanel pnlSuperior = new JPanel();
	JPanel pnlCentral = new JPanel();
	JPanel pnlInferior = new JPanel();

	// Componentes para el di�logo
	JLabel lblIndicar = new JLabel("Indicar tu correo electr�nico");
	JTextField txtCorreo = new JTextField(18);
	JButton btnAceptar = new JButton("Aceptar");

	// Componentes para el Segundo Di�logo
	JLabel lblCorreo = new JLabel("Se ha enviado un correo electr�nico a su cuenta");
	JButton btnAceptar2 = new JButton("Aceptar");

	// Paneles para el Segundo Di�logo
	JPanel pnlSuperior2 = new JPanel();
	JPanel pnlCentral2 = new JPanel();

	// Componentes para el di�logo RECUPERACI�N INCORRECTA
	JLabel lblNoExiste = new JLabel("No existe una cuenta con ese correo electr�nico");
	JButton btnAceptarRI = new JButton("Aceptar");

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
		// Almacenamos en mipantalla el sistema nativo de pantallas, el tama�o por defecto de la pantalla, nos servir� para poner el icono
		Toolkit mipantalla = Toolkit.getDefaultToolkit();

		// Establecer Layout para 2 filas y 4 columnas
		miPrograma.setLayout(new GridLayout(4,2));
		// A�adir etiquetas, campos de texto, etc.
		pnlSuperior1.add(lblUsuario);
		pnlSuperior1.add(txtUsuario);
		pnlCentralSub.add(lblClave);
		pnlCentralSub.add(txtClave);
		pnlInferior1.add(btnLogin);
		pnlInferior1.add(btnLimpiar);
		pnlInferior2.add(btnOlvidar);
		// Localizaci�n Paneles
		miPrograma.add(pnlSuperior1, BorderLayout.CENTER);
		miPrograma.add(pnlCentral1, BorderLayout.CENTER);
		miPrograma.add(pnlInferior1, BorderLayout.CENTER);
		miPrograma.add(pnlInferior2, BorderLayout.CENTER);
		pnlCentral1.add(pnlCentralSub, BorderLayout.CENTER);

		// Reemplazar contenido de txtClave por aster�sco:
		txtClave.setEchoChar('*');
		// Establecer Valores para posicionar el frame en la pantalla
		miPrograma.setBounds(800,400,400,200);
		// Establecer al medio la ventana, dependiendo de cual sea la resoluci�n.
		miPrograma.setLocationRelativeTo(null);
		// Impedir cambios en la ventana, dejarla por defecto como lo ha puesto el programador
		miPrograma.setResizable(false);
		// A�adir el WindowListener
		miPrograma.addWindowListener(this);
		// T�tulo al di�logo
		recuperacion.setTitle("Olvid� la clave");
		// A�adirle al di�logo RECUPERACION los siguientes componentes
		pnlSuperior.add(lblIndicar);
		pnlCentral.add(txtCorreo);
		txtCorreo.setText("prueba@prueba.com");
		pnlInferior.add(btnAceptar);
		// Ubicaci�n de los paneles
		recuperacion.add(pnlSuperior, "North");
		recuperacion.add(pnlCentral, "Center");
		recuperacion.add(pnlInferior, "South");

		// Tama�o al di�logo, localizaci�n y listener
		recuperacion.setSize(270,160);
		recuperacion.setResizable(false);
		recuperacion.setLocationRelativeTo(null);
		recuperacion.addWindowListener(this);
		
		// Di�logo2
		recuperacion2.setTitle("Correcto");
		// T�tulo al di�logo Recuperaci�n Incorrecta
		recuperacionIncorrecta.setTitle("Incorrecto");
		recuperacionIncorrecta.setLayout(new FlowLayout());
		recuperacionIncorrecta.setSize(300,100);
		recuperacionIncorrecta.setLocationRelativeTo(null);
		recuperacionIncorrecta.setResizable(false);
		recuperacionIncorrecta.addWindowListener(this);
		recuperacionIncorrecta.setVisible(false);

		// A�adir componentes Recuperaci�n Incorrecta cuando tienes que especificar el correo
		recuperacionIncorrecta.add(lblNoExiste);
		recuperacionIncorrecta.add(btnAceptarRI);

		// Tama�o al di�logo 2
		recuperacion2.setSize(300,110);
		recuperacion2.setResizable(false);
		recuperacion2.setLocationRelativeTo(null);
		recuperacion2.addWindowListener(this);
		// Ubicaci�n paneles en el segundo di�logo
		recuperacion2.add(pnlSuperior2, "North");
		recuperacion2.add(pnlCentral2, "Center");

		// A�adirle al segundo di�logo los siguientes componentes
		pnlSuperior2.add(lblCorreo);
		pnlCentral2.add(btnAceptar2);

		// Establecer un icono a la aplicaci�n
		Image miIcono = mipantalla.getImage("farmacia.png");
		// Colocar Icono
		miPrograma.setIconImage(miIcono);

		// A�adir keyListener al TextField en el que se pone la clave 
		txtClave.addKeyListener(this);
		// A�adir Listeners a los botones
		btnLogin.addActionListener(this);
		btnLimpiar.addActionListener(this);
		btnOlvidar.addActionListener(this);
		btnAceptar.addActionListener(this);
		btnAceptar2.addActionListener(this);
		btnAceptarRI.addActionListener(this);
		// Hacer visible el frame principal (INICIO DE SESI�N)
		miPrograma.setVisible(true);
	}

	public static void main(String[] args) {
		new Programa_Gestion();
	}

	public void windowActivated(WindowEvent arg0) {}
	public void windowClosed(WindowEvent arg0) {}

	@Override
	public void windowClosing(WindowEvent arg0) {
		// Cuando el di�logo de recuperaci�n del clave
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

			sentencia = "SELECT tipoUsuario FROM usuarios WHERE nombreUsuario = '"+txtUsuario.getText()+"' AND claveUsuario = MD5('" + txtClave.getText()+"');";

			try{
				Class.forName(driver);				
				//Establecer la conexi�n con la BD Empresa
				connection = DriverManager.getConnection(url, login, password);
				//Crear una sentencia
				statement = connection.createStatement();
				//Crear un objeto ResultSet para guardar lo obtenido y ejecutar la sentencia SQL
				rs = statement.executeQuery(sentencia);
				rs.next();
				int tipoUsuario = rs.getInt("tipoUsuario");
				if (tipoUsuario==0) {
					miPrograma.setVisible(false);
					new MenuPrincipal();
				}else{
					miPrograma.setVisible(false);
					new MenuPrincipalUsuario();
				}
			} 

			catch (ClassNotFoundException e) {
				JOptionPane.showMessageDialog(null, "No se ha cargado el driver debido a que no est� disponible", "Error", JOptionPane.ERROR_MESSAGE);
			}

			catch(SQLException e) {
				JOptionPane.showMessageDialog(null, "Se ha producido un error al conectarse con la BD", "Error", JOptionPane.ERROR_MESSAGE);
			}

			// Bloque para intentar cerrar la conexi�n la BD
			try {
				if (connection!=null) {
					connection.close();
				}	
			}catch(SQLException sqle) {
				JOptionPane.showMessageDialog(null, "Se ha producido un problema al acceder a la Base de Datos o similar", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}

		// Crear objeto de la clase Fecha_Y_Hora para poder trabajar con el para guardar lo que se pone en el usuario (texto)
		Guardar_Movimientos f = new Guardar_Movimientos();
		try {
			// Llamando al m�todo de fecha y mandarle lo que se escribe en el texto de inicio de sesi�n 
			f.registrar(txtUsuario.getText()+"]"+"[Login");
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			// Hacer visible el di�logo
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

	@Override
	public void keyPressed(KeyEvent e) {
		int codigo = e.getKeyCode();
		
		if (codigo==10) {
			btnLogin.doClick();
		}
	}

	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
}