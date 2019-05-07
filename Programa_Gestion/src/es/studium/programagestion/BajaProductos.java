package es.studium.programagestion;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Choice;
import java.awt.Color;
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

import javax.swing.JOptionPane;

public class BajaProductos extends Frame implements ActionListener, WindowListener{

	private static final long serialVersionUID = 1L;
	
	// Crear componentes 
	Label lblBaja = new Label("Baja Productos");
	Choice chcSeleccion = new Choice();
	Button btnBaja = new Button("Baja");
	
	// Paneles
	Panel pnlSuperior = new Panel();
	Panel pnlChoice = new Panel(); 
	Panel pnlBaja = new Panel();
	
	// Dialogo Informativo 
	Dialog diainformativo = new Dialog(this, true);
	// Componentes
	Label lblConfirmacion = new Label("¿Estás seguro de realizar la Baja?");
	Button btnSi = new Button("Sí");
	Button btnNo = new Button("No");
	
	// Base de Datos
	String driver = "com.mysql.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/farmaciapr2?autoReconnect=true&useSSL=false";
	String login = "admin";
	String password = "Studium2018;";
	String sentencia = null;
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;
	
	BajaProductos()
	{
		// Almacenamos en mipantalla el sistema nativo de pantallas, el tamaño por defecto de la pantalla, nos servirá para poner el icono
		Toolkit mipantalla = Toolkit.getDefaultToolkit();
		
		setTitle("Baja Productos");
		pnlSuperior.add(lblBaja);
		lblBaja.setFont(new java.awt.Font("Times New Roman", 1, 18));
		chcSeleccion.add("Elige uno...");
		insertarProductos();
		pnlChoice.add(chcSeleccion);
		pnlBaja.add(btnBaja);
		add(pnlSuperior, BorderLayout.NORTH);
		add(pnlChoice, BorderLayout.CENTER);
		add(pnlBaja, BorderLayout.SOUTH);

		// Componentes diálogo informativo
		diainformativo.setTitle("Comprobación Baja");
		diainformativo.setLayout(new FlowLayout());
		diainformativo.setBackground(Color.decode("#d9d9d9"));
		diainformativo.add(lblConfirmacion);
		lblConfirmacion.setFont(new java.awt.Font("Times New Roman", 0, 15)); 
		diainformativo.add(btnSi);
		diainformativo.add(btnNo);
		btnSi.setFont(new java.awt.Font("Times New Roman", 0, 15));
		btnNo.setFont(new java.awt.Font("Times New Roman", 0, 15)); 
		diainformativo.setSize(220,100);
		diainformativo.addWindowListener(this);
		btnBaja.addActionListener(this);
		btnSi.addActionListener(this);
		diainformativo.setLocationRelativeTo(null);
		diainformativo.setResizable(false);
		diainformativo.setVisible(false);	
		
		// Establecer un icono a la aplicación
		Image miIcono = mipantalla.getImage("src//farmacia.png");
		// Colocar Icono
		setIconImage(miIcono);
		
		setSize(300,130);
		addWindowListener(this);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}
	
	public void insertarProductos() {
		sentencia = "SELECT * FROM productos ORDER BY 1;";

		try
		{
			Class.forName(driver);
			connection = DriverManager.getConnection(url, login, password);
			//Crear una sentencia
			statement = connection.createStatement();
			// Ejecutar la sentencia
			rs = statement.executeQuery(sentencia);
			while (rs.next()) {
				int idProducto = rs.getInt("idProducto");
				int contenidototalProducto = rs.getInt("contenidototalProducto");
				String nombreProducto = rs.getString("nombreProducto");
				String marcaProducto = rs.getString("marcaProducto");
				String precioProducto = rs.getString("precioProducto");
				chcSeleccion.add(idProducto + " " + contenidototalProducto + " " + nombreProducto + " " + marcaProducto + " " + precioProducto);
			}
		}

		catch (ClassNotFoundException cnfe)
		{
			System.out.println("Error 1: "+cnfe.getMessage());
		}
		catch (SQLException sqle)
		{
			JOptionPane.showMessageDialog(null, "Error, en la Baja", "Error", JOptionPane.ERROR_MESSAGE);
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
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (btnBaja.equals(arg0.getSource())){
			diainformativo.setVisible(true);
		}
		
		else if (btnSi.equals(arg0.getSource())) {
			String [] eliminarespacios = chcSeleccion.getSelectedItem().split(" ");

			String dniCliente = eliminarespacios[3];

			try {
				Class.forName(driver);
				connection = DriverManager.getConnection(url, login, password);
				//Crear una sentencia
				sentencia = "DELETE FROM clientes WHERE dniCliente = '"+dniCliente+"';";
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				System.out.println(sentencia);
				statement.executeUpdate(sentencia);
			}

			catch (ClassNotFoundException cnfe)
			{
				System.out.println("Error 1: "+cnfe.getMessage());
			}
			catch (SQLException sqle)
			{
				JOptionPane.showMessageDialog(null, "Error, en la Baja", "Error", JOptionPane.ERROR_MESSAGE);
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

			Guardar_Movimientos gm = new Guardar_Movimientos();
			try {
				gm.registrar("administrador]" + "["+sentencia+"");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			JOptionPane.showMessageDialog(null, "Baja Correcta", "Baja Realizada", JOptionPane.OK_CANCEL_OPTION);
		}
		
		else if (btnNo.equals(arg0.getSource())) {
			diainformativo.setVisible(false);
			setVisible(true);
		}
	}
	
	@Override
	public void windowClosing(WindowEvent arg0) {
		if (this.isActive()) {
			setVisible(false);
			new MenuPrincipal(null);
		}else if(diainformativo.isActive()){
			diainformativo.setVisible(false);
			this.setVisible(true);
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