package es.studium.programagestion;

import java.awt.Button;
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

import javax.swing.JOptionPane;

public class AltaClientesUsuario extends Frame implements ActionListener, WindowListener{

	private static final long serialVersionUID = 1L;

	// Crear componentes 
	Label lblNombre = new Label("Nombre:");
	Label lblApellidos = new Label("Apellidos:");
	Label lblDNI = new Label("DNI:");
	
	// TextField 
	TextField txtNombre = new TextField();
	TextField txtApellidos = new TextField();
	TextField txtDNI = new TextField();

	// Botones
	Button btnAlta = new Button("Alta");
	Button btnLimpiar = new Button("Limpiar");
	
	// Necesario para conectar con la BD
	String driver = "com.mysql.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/farmaciapr2?autoReconnect=true&useSSL=false";
	String login = "usuario";
	String password = "usuario";
	String sentencia = null;
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;
	
	AltaClientesUsuario()
	{
		// Almacenamos en mipantalla el sistema nativo de pantallas, el tamaño por defecto de la pantalla, nos servirá para poner el icono
		Toolkit mipantalla = Toolkit.getDefaultToolkit();
		
		setTitle("Alta Clientes");
		setLayout(new GridLayout(4,2));
		add(lblNombre);
		add(txtNombre);
		add(lblApellidos);
		add(txtApellidos);
		add(lblDNI);
		add(txtDNI);
		add(btnAlta);
		add(btnLimpiar);

		// Añadir los Listeners (WINDOWLISTENER) al frame
		addWindowListener(this);
		
		// Action Listeners a los botones del Frame de la clase Principal (ALTACLIENTES)
		btnAlta.addActionListener(this);
		btnLimpiar.addActionListener(this);
		
		// Establecer un icono a la aplicación
		Image miIcono = mipantalla.getImage("src//farmacia.png");
		// Colocar Icono
		setIconImage(miIcono);
		
		// Tamaño al frame principal AltaClientes
		setSize(300,300);
		// Localización al centro de la pantalla
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String Nombre = txtNombre.getText();
		String Apellidos = txtApellidos.getText();
		String DNI = txtDNI.getText();

		if (btnAlta.equals(arg0.getSource())) {
			if (Nombre.equals("") | Apellidos.equals("") | DNI.equals("")) {
				JOptionPane.showMessageDialog(null, "Error, en el Alta", "Error", JOptionPane.ERROR_MESSAGE);
			}
			
			else
			{
				try
				{
					Class.forName(driver);
					connection = DriverManager.getConnection(url, login, password);
					//Crear una sentencia
					statement = connection.createStatement();
					sentencia = "INSERT INTO clientes VALUES('"+DNI+"', '"+Nombre+"', '"+Apellidos+"');";
					// Ejecutar la sentencia
					statement.executeUpdate(sentencia);
					JOptionPane.showMessageDialog(null, "El Alta se ha realizado", "Alta Correcta", JOptionPane.INFORMATION_MESSAGE);

					Guardar_Movimientos gm = new Guardar_Movimientos();
					try {
						gm.registrar("admin]" + "["+sentencia+"");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				catch (ClassNotFoundException cnfe)
				{
					System.out.println("Error 1: "+cnfe.getMessage());
				}
				catch (SQLException sqle)
				{
					JOptionPane.showMessageDialog(null, "Error, en el Alta", "Error", JOptionPane.ERROR_MESSAGE);
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
					catch (SQLException se)
					{
						System.out.println("No se puede cerrar la conexión la Base De Datos");
					}
				}
			}
		}

		if(btnLimpiar.equals(arg0.getSource())) {
			// Seleccionar los text field y limpiarlos
			txtNombre.selectAll();
			txtNombre.setText("");

			// Apellidos
			txtApellidos.selectAll();
			txtApellidos.setText("");

			// DNI
			txtDNI.selectAll();
			txtDNI.setText("");
		}
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		if(this.isActive()) {
			this.setVisible(false);
			new MenuPrincipalUsuario();
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