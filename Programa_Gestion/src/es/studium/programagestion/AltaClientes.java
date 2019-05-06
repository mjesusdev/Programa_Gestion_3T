package es.studium.programagestion;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Frame;
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

public class AltaClientes extends Frame implements ActionListener, WindowListener{

	private static final long serialVersionUID = 1L;

	// Crear componentes 
	Label lblAlta = new Label("Alta Clientes");
	Label lblNombre = new Label("Nombre:  ");
	Label lblApellidos = new Label("Apellidos:");
	Label lblDNI = new Label("DNI:        ");
	TextField txtNombre = new TextField(15);
	TextField txtApellidos = new TextField(15);
	TextField txtDNI = new TextField(15         );
	Button btnAlta = new Button("Alta");
	Button btnLimpiar = new Button("Limpiar");
	
	// Paneles
	Panel pnlSuperior = new Panel();
	Panel pnlComponentes = new Panel();
	Panel pnlBotones = new Panel();
	
	// Necesario para conectar con la BD
	String driver = "com.mysql.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/farmaciapr2?autoReconnect=true&useSSL=false";
	String login = "admin";
	String password = "Studium2018;";
	String sentencia = null;
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;
	
	AltaClientes()
	{
		// Almacenamos en mipantalla el sistema nativo de pantallas, el tamaño por defecto de la pantalla, nos servira para poner el icono
		Toolkit mipantalla = Toolkit.getDefaultToolkit();
		
		setTitle("Alta Clientes");
		pnlSuperior.add(lblAlta);
		lblAlta.setFont(new java.awt.Font("Times New Roman", 1, 18)); 
		pnlComponentes.add(lblNombre);
		lblNombre.setFont(new java.awt.Font("Times New Roman", 0, 14)); 
		pnlComponentes.add(txtNombre);
		pnlComponentes.add(lblApellidos);
		lblApellidos.setFont(new java.awt.Font("Times New Roman", 0, 14)); 
		pnlComponentes.add(txtApellidos);
		pnlComponentes.add(lblDNI);
		lblDNI.setFont(new java.awt.Font("Times New Roman", 0, 14)); 
		pnlComponentes.add(txtDNI);
		pnlBotones.add(btnAlta);
		btnAlta.setFont(new java.awt.Font("Times New Roman", 0, 14)); 
		pnlBotones.add(btnLimpiar);
		btnLimpiar.setFont(new java.awt.Font("Times New Roman", 0, 14)); 

		// Añadir los paneles
		add(pnlSuperior, BorderLayout.NORTH);
		add(pnlComponentes, BorderLayout.CENTER);
		add(pnlBotones, BorderLayout.SOUTH);
		
		// Establecer un icono a la aplicación
		Image miIcono = mipantalla.getImage("src//farmacia.png");
		// Colocar Icono
		setIconImage(miIcono);
		
		// Tamaño al frame principal AltaClientes
		setSize(250,210);
		// Localización al centro de la pantalla
		setLocationRelativeTo(null);
		// Añadir los Listeners 
		addWindowListener(this);
		btnAlta.addActionListener(this);
		btnLimpiar.addActionListener(this);
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
				JOptionPane.showMessageDialog(null, "Error, en el Alta por favor corrija los errores", "Error", JOptionPane.ERROR_MESSAGE);
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
			new MenuPrincipal(null);
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