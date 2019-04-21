package es.studium.programagestion;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JScrollPane;
import javax.swing.JTable;

public class ConsultaClientes extends Frame implements ActionListener, WindowListener{

	private static final long serialVersionUID = 1L;

	Label lblConsulta = new Label("Consulta Clientes");
	
	static JTable tablaClientes = new JTable();

	Button btnVolver = new Button("Volver");

	String [] titulos= {"DNI","Nombre","Apellidos"};

	// Base de Datos
	static String driver = "com.mysql.jdbc.Driver";
	static String url = "jdbc:mysql://localhost:3306/farmaciapr2?autoReconnect=true&useSSL=false";
	static String login = "admin";
	static String password = "Studium2018;";
	static String sentencia = "SELECT * FROM clientes;";
	static Connection connection = null;
	static Statement statement = null;
	static ResultSet rs = null;

	ConsultaClientes()
	{
		setTitle("Consulta Clientes");
		// Llamar al método que coloca el icono a la ventana
		colocarIcono();
		setLayout(new FlowLayout());
		add(lblConsulta);
		tablaClientes = new JTable(rellenarTabla(),titulos);
		add(new JScrollPane(tablaClientes), BorderLayout.CENTER);
		add(btnVolver);
		setSize(500,600);
		setLocationRelativeTo(null);
		addWindowListener(this);
		btnVolver.addActionListener(this);
		setResizable(false);
		setVisible(true);
	}

	public void colocarIcono() {
		Toolkit mipantalla = Toolkit.getDefaultToolkit();
		Image miIcono = mipantalla.getImage("src//farmacia.png");
		setIconImage(miIcono);
	}

	public Object[][] rellenarTabla() {
		try 
		{
			Class.forName(driver);
			connection = DriverManager.getConnection(url, login, password);
			statement = connection.createStatement();						
			rs = statement.executeQuery(sentencia);

			if (rs.next()) {
				String dniCliente = rs.getString("dniCliente");
				String nombreCliente = rs.getString("nombreCliente");
				String apellidosCliente = rs.getString("apellidosCliente");

				Object [][] datosFilaFinal= {
						{dniCliente, nombreCliente, apellidosCliente}
				};

				return datosFilaFinal;
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
		return null;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(btnVolver.equals(arg0.getSource())){
			this.setVisible(false);
			new MenuPrincipal(null);
		}
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		if(this.isActive()){
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