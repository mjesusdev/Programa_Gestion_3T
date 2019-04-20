package es.studium.programagestion;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Image;
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
	
	static JTable tablaClientes = new JTable();
	
	Button btnVolver = new Button("Volver");
	
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
		Toolkit mipantalla = Toolkit.getDefaultToolkit();
		Image miIcono = mipantalla.getImage("src//farmacia.png");
		setIconImage(miIcono);
		setTitle("Consulta Clientes");
		setLayout(new FlowLayout());
		add(new JScrollPane(tablaClientes), BorderLayout.CENTER);
		add(btnVolver);
		setSize(290,150);
		setLocationRelativeTo(null);
		addWindowListener(this);
		btnVolver.addActionListener(this);
		setResizable(false);
		setVisible(true);
	}
	
	public static void rellenarTabla() {
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
			rs.next();
			
			String dniCliente = rs.getString("dniCliente");
			String nombreCliente = rs.getString("nombreCliente");
			String apellidosCliente = rs.getString("apellidosCliente");
			
			String [] nombreColumnas= {"dniCliente","nombreCliente","apellidosCliente"};
			
			Object [][] datosFila={
					{dniCliente, nombreCliente, apellidosCliente},
			};
			tablaClientes(datosFila, nombreColumnas);
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
	}

	private static void tablaClientes(Object[][] datosFila, String[] nombreColumnas) {
		// TODO Auto-generated method stub
		
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