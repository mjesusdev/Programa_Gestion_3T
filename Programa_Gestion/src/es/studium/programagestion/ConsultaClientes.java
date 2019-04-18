package es.studium.programagestion;

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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConsultaClientes extends Frame implements ActionListener, WindowListener{

	private static final long serialVersionUID = 1L;
	
	// Crear componentes 
	Choice choseleccion = new Choice();
	Button btnConsultar = new Button("Consulta");
	Button btnVolver = new Button("Volver");
	
	// Paneles
	Panel pnlChoice = new Panel();
	Panel pnlCentral = new Panel();
	
	// Diálogo donde aparecen los campos
	Dialog diaClientes = new Dialog(this,true);
	
	// Componentes para el diálogo
	Label lblNombre = new Label("Nombre:");
	Label Nombre = new Label("");
	Label lblApellidos = new Label("Apellidos:");
	Label Apellidos = new Label("");
	Label lblDNI = new Label("DNI:");
	Label DNI = new Label("");
	Button Volver = new Button("Volver");
	
	// Driver MySQL
	String driver = "com.mysql.jdbc.Driver";
	// URL donde está la base de datos
	String url = "jdbc:mysql://localhost:3306/farmaciapr?autoReconnect=true&useSSL=false";
	// Usuario
	String login = "root";
	// Password
	String password = "Studium2018;";
	// Sentencia a ejecutar
	String sentencia1ercliente = "SELECT * FROM clientes LIMIT 1";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;
	
	ConsultaClientes()
	{
		// Almacenamos en mipantalla el sistema nativo de pantallas el tamaño por defecto de la pantalla, nos servirá para poner el icono
		Toolkit mipantalla = Toolkit.getDefaultToolkit();
		
		setTitle("Consulta Clientes");
		// Añadir componentes del choice
		choseleccion.add("1. Cliente");
		choseleccion.add("2. Cliente");
		choseleccion.add("3. Cliente");
		pnlChoice.add(choseleccion);
		pnlCentral.add(btnConsultar);
		pnlCentral.add(btnVolver);
		// Añadir los paneles
		add(pnlChoice, "North");
		add(pnlCentral, "Center");

		// Diálogo
		diaClientes.setTitle("Consulta Clientes");
		diaClientes.setLayout(new FlowLayout());
		diaClientes.add(lblNombre);
		diaClientes.add(Nombre);
		diaClientes.add(lblApellidos);
		diaClientes.add(Apellidos);
		diaClientes.add(lblDNI);
		diaClientes.add(DNI);
		diaClientes.add(Volver);
		diaClientes.setSize(170,230);
		diaClientes.setLocationRelativeTo(null);
		diaClientes.addWindowListener(this);
		Volver.addActionListener(this);
		diaClientes.setResizable(false);
		diaClientes.setVisible(false);		
		
		// Establecer un icono a la aplicación
		Image miIcono = mipantalla.getImage("src//farmacia.png");
		// Colocar Icono
		setIconImage(miIcono);
		
		// Ventana aplicarle tamaño, listener, etc
		setSize(290,150);
		setLocationRelativeTo(null);
		addWindowListener(this);
		btnConsultar.addActionListener(this);
		btnVolver.addActionListener(this);
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
			rs = statement.executeQuery(sentencia1ercliente);
			
			while (rs.next())
			{
				if ("1. Cliente".equals(choseleccion.getSelectedItem())) 
				{
					DNI.setText(rs.getString("dniCliente"));
					Nombre.setText(rs.getString("nombreCliente"));
					Apellidos.setText(rs.getString("apellidosCliente"));
				}
				
				if ("2. Cliente".equals(choseleccion.getSelectedItem())) 
				{
					DNI.setText(rs.getString("dniCliente"));
					Nombre.setText(rs.getString("nombreCliente"));
					Apellidos.setText(rs.getString("apellidosCliente"));
				}
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
		if (btnConsultar.equals(arg0.getSource())) {
			Guardar_Movimientos f = new Guardar_Movimientos();
			try {
				f.registrar("admin]" + "[Consulta Clientes");
			} catch (IOException e) {
				e.printStackTrace();
			}		
		
			this.setVisible(false);
			diaClientes.setVisible(true);

		}	
		else if(btnVolver.equals(arg0.getSource())){
			this.setVisible(false);
			new MenuPrincipal(null);
		}
		else if(Volver.equals(arg0.getSource())) {
			diaClientes.setVisible(false);
			setVisible(true);
		}
	}
	
	@Override
	public void windowClosing(WindowEvent arg0) {
		if (diaClientes.isActive()) {
			diaClientes.setVisible(false);
			setVisible(true);
		}
		
		else if(this.isActive()){
			this.setVisible(false);
			new MenuPrincipal(null);
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