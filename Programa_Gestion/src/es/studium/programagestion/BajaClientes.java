package es.studium.programagestion;

import java.awt.BorderLayout;
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

import javax.swing.JOptionPane;

public class BajaClientes extends Frame implements ActionListener, WindowListener{

	private static final long serialVersionUID = 1L;

	// Crear componentes 
	Choice chcSeleccion = new Choice();
	Button btnBaja = new Button("Baja");

	// Paneles para BAJA CLIENTES
	Panel pnlChoice = new Panel(); 
	Panel pnlBaja = new Panel();

	// Dialogo Informativo 
	Dialog diainformativo = new Dialog(this, true);
	// Componentes
	Label lblConfirmacion = new Label("¿Estás seguro de realizar la Baja?");
	Button btnSi = new Button("Sí");
	Button btnNo = new Button("No");

	// Diálogo Baja Correcta
	Dialog bajaCorrecta = new Dialog(this, true);
	//Componentes Baja Correcta
	Label lblBajaCorrecta = new Label("Baja Correcta");
	Button btnAceptar = new Button("Aceptar");

	// Base de Datos
	String driver = "com.mysql.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/farmaciapr2?autoReconnect=true&useSSL=false";
	String login = "admin";
	String password = "Studium2018;";
	String sentencia = null;
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;

	BajaClientes()
	{
		// Almacenamos en mipantalla el sistema nativo de pantallas, el tamaño por defecto de la pantalla, nos servira para poner el icono
		Toolkit mipantalla = Toolkit.getDefaultToolkit();

		setTitle("Baja Clientes");
		chcSeleccion.add("Elige uno...");
		insertarClientes();
		pnlChoice.add(chcSeleccion);
		pnlBaja.add(btnBaja);
		add(pnlChoice, "North");
		add(pnlBaja, BorderLayout.CENTER);

		// Componentes diálogo informativo
		diainformativo.setTitle("Comprobación");
		diainformativo.setLayout(new FlowLayout());
		diainformativo.add(lblConfirmacion);
		diainformativo.add(btnSi);
		diainformativo.add(btnNo);
		diainformativo.setSize(230,100);
		diainformativo.addWindowListener(this);
		btnBaja.addActionListener(this);
		btnSi.addActionListener(this);
		btnNo.addActionListener(this);
		diainformativo.setLocationRelativeTo(null);
		diainformativo.setResizable(false);
		diainformativo.setVisible(false);

		// Componentes diálogo Baja Correcta
		bajaCorrecta.setTitle("Baja Correcta");
		bajaCorrecta.setLayout(new FlowLayout());
		bajaCorrecta.add(lblBajaCorrecta);
		bajaCorrecta.add(btnAceptar);
		btnAceptar.addActionListener(this);
		bajaCorrecta.setSize(150,110);
		bajaCorrecta.addWindowListener(this);
		bajaCorrecta.setLocationRelativeTo(null);
		bajaCorrecta.setResizable(false);
		bajaCorrecta.setVisible(false);		

		// Establecer un icono a la aplicación
		Image miIcono = mipantalla.getImage("src//farmacia.png");
		// Colocar Icono
		setIconImage(miIcono);

		setSize(350,200);
		setLocationRelativeTo(null);
		addWindowListener(this);
		setResizable(false);
		setVisible(true);
	}

	public void insertarClientes() {
		sentencia = "SELECT * FROM clientes ORDER BY 1;";

		try
		{
			Class.forName(driver);
			connection = DriverManager.getConnection(url, login, password);
			//Crear una sentencia
			statement = connection.createStatement();
			// Ejecutar la sentencia
			rs = statement.executeQuery(sentencia);
			while (rs.next()) {
				String dniCliente = rs.getString("dniCliente");
				String nombreCliente = rs.getString("nombreCliente");
				String apellidosCliente = rs.getString("apellidosCliente");
				chcSeleccion.add(nombreCliente + " " + apellidosCliente + " " + dniCliente);
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
			bajaCorrecta.setVisible(true);
		}

		else if (btnNo.equals(arg0.getSource())) {
			diainformativo.setVisible(false);
			setVisible(true);
		}

		else if (btnAceptar.equals(arg0.getSource())) {
			
			String [] eliminarespacios = chcSeleccion.getSelectedItem().split(" ");
			
			String dniCliente = eliminarespacios[2];
			
			try {
				Class.forName(driver);
				connection = DriverManager.getConnection(url, login, password);
				//Crear una sentencia
				sentencia = "DELETE FROM clientes WHERE dniCliente = '"+dniCliente+"';";
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
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
				gm.registrar("admin]" + "["+sentencia+"");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			
			bajaCorrecta.setVisible(false);
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
		}else if(bajaCorrecta.isActive()){
			bajaCorrecta.setVisible(false);
			diainformativo.setVisible(false);
			setVisible(true);
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