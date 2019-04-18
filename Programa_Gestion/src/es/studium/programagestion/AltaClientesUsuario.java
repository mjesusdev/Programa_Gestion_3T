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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AltaClientesUsuario extends Frame implements ActionListener, WindowListener{

	private static final long serialVersionUID = 1L;

	// Crear componentes 
	Label lblNombre = new Label("Nombre:");
	TextField txt = new TextField();
	Label lblApellidos = new Label("Apellidos:");
	TextField txtApellidos = new TextField();
	Label lblDNI = new Label("DNI:");
	TextField txtDNI = new TextField();
	Button btnAlta = new Button("Alta");
	Button btnLimpiar = new Button("Limpiar");

	// Diálogo Alta Correcta y AltaIncorrecta
	Dialog AltClientes = new Dialog(this, true);
	Dialog AltIn = new Dialog(this, true);

	// Componentes Diálogo Correcto
	Label lblAltCorrecta = new Label("Alta Correcta");
	Button btnAceptar = new Button("Aceptar");

	// Componentes Diálogo Incorrecto
	Label lblAltIncorrecta = new Label("Se ha producido un error en el Alta");
	Button btnAceptar1 = new Button("Aceptar");

	// Driver MySQL
	String driver = "com.mysql.jdbc.Driver";
	// URL donde está la base de datos
	String url = "jdbc:mysql://localhost:3306/empresa?autoReconnect=true&useSSL=false";
	// Usuario
	String login = "root";
	// Password
	String password = "Studium2018;";
	// Sentencia a ejecutar
	String sentencia = "INSERT";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;

	AltaClientesUsuario()
	{
		// Almacenamos en mipantalla el sistema nativo de pantallas, el tamaño por defecto de la pantalla, nos servira para poner el icono
		Toolkit mipantalla = Toolkit.getDefaultToolkit();

		setTitle("Alta Clientes");
		setLayout(new GridLayout(4,2));
		add(lblNombre);
		add(txt);
		add(lblApellidos);
		add(txtApellidos);
		add(lblDNI);
		add(txtDNI);
		add(btnAlta);
		add(btnLimpiar);

		// Diálogo AltaClientes CORRECTA
		AltClientes.setTitle("Alta Clientes");
		// Distribución centrada
		AltClientes.setLayout(new FlowLayout());
		AltClientes.add(lblAltCorrecta);
		AltClientes.add(btnAceptar);
		AltClientes.setSize(150,100);
		AltClientes.setLocationRelativeTo(null);
		AltClientes.setResizable(false);
		AltClientes.setVisible(false);

		// Diálogo AltaClientes INCORRECTA
		AltIn.setTitle("Alta Clientes");
		// Distribución centrada
		AltIn.setLayout(new FlowLayout());
		AltIn.add(lblAltIncorrecta);
		AltIn.add(btnAceptar1);
		AltIn.setSize(250,100);
		AltIn.setLocationRelativeTo(null);
		AltIn.setResizable(false);
		AltIn.setVisible(false);

		// Añadir los Listeners (WINDOWLISTENER) a las ventanas, frame y diálogos
		addWindowListener(this);
		AltClientes.addWindowListener(this);
		AltIn.addWindowListener(this);

		// Action Listeners a los botones del Frame de la clase Principal (ALTACLIENTES)
		btnAlta.addActionListener(this);
		btnLimpiar.addActionListener(this);
		// BTN ALTACLIENTES Correcta Y ALTACLIENTES Incorrecta
		btnAceptar.addActionListener(this);
		btnAceptar1.addActionListener(this);

		// Establecer un icono a la aplicación
		Image miIcono = mipantalla.getImage("src//farmacia.png");
		// Colocar Icono
		setIconImage(miIcono);
		// Tamaño al frame principal AltaClientes
		setSize(300,300);
		// Localización al centro de la pantalla
		setLocationRelativeTo(null);
		setResizable(false);
		
		try 
		{
			//Cargar los controladores para el acceso a la BD
			Class.forName(driver);
			//Establecer la conexión con la BD Empresa
			connection = DriverManager.getConnection(url, login, password);
			//Crear una sentencia
			statement = connection.createStatement();
			//Crear un objeto ResultSet para guardar lo obtenido y ejecutar la sentencia SQL
			rs = statement.executeQuery(sentencia);
			while (rs.next())
			{
				System.out.println(rs.getInt("idEmpleado") + "-" +
						rs.getString("nombreEmpleado"));
			} 
		}
			catch (ClassNotFoundException cnfe)
			{
				System.out.println("Error 1: "+cnfe.getMessage());
			}

			catch (SQLException sqle)
			{
				System.out.println("Error 2: "+sqle.getMessage());
			}

			finally
			{
				try
				{
					if(connection!=null)
					{
						connection.close();
					}
				}
				catch (SQLException e)
				{
					System.out.println("Error 3: "+e.getMessage());
				}
			}
			
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String Nombre = txt.getText();
		String Apellidos = txtApellidos.getText();
		String DNI = txtDNI.getText();

		if (btnAlta.equals(arg0.getSource())) {
			// Si el contenido de los tres TEXT FIELD están vacíos que lanze la ventana de ALTA INCORRECTA
			if (Nombre.equals("") &&  (Apellidos.equals("")) && (DNI.equals(""))){
				AltIn.setVisible(true);
				this.setVisible(false);
			}else{
				AltClientes.setVisible(true);
				this.setVisible(false);
			}
		}

		if(btnLimpiar.equals(arg0.getSource())) {
			// Seleccionar los text field y limpiarlos
			txt.selectAll();
			txt.setText("");

			// Apellidos
			txtApellidos.selectAll();
			txtApellidos.setText("");

			// DNI
			txtDNI.selectAll();
			txtDNI.setText("");
		}

		if(btnAceptar.equals(arg0.getSource())) {
			Guardar_Movimientos gm = new Guardar_Movimientos();
			try {
				gm.registrar("Usuario]" + "[Alta Clientes");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			AltClientes.setVisible(false);
			new AltaClientesUsuario();

		}

		if(btnAceptar1.equals(arg0.getSource())) {
			AltIn.setVisible(false);
			new AltaClientesUsuario();
		}
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		if(AltClientes.isActive()) {
			// Hacer visible la pantalla principal (AltaClientes) y que desaparezca el diálogo (AltClientes)
			AltClientes.setVisible(false);
			new AltaClientesUsuario();
		}

		else if(this.isActive()) {
			this.setVisible(false);
			new MenuPrincipalUsuario();
		}

		else if(AltIn.isActive()) {
			// Hacer visible la pantalla principal (AltaClientes) y que desaparezca el diálogo (AltIn) "Alta Incorrecta"
			AltIn.setVisible(false);
			new AltaClientesUsuario();
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