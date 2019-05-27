package es.studium.programagestion;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Choice;
import java.awt.Frame;
import java.awt.GridLayout;
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

public class AltaCompraUsuario extends Frame implements WindowListener, ActionListener{

	private static final long serialVersionUID = 1L;

	Label lblCompra = new Label("Alta Compra");
	Label lblDNICliente = new Label("Cliente:");
	Label lblProducto = new Label("Producto:");

	Choice elegirCliente = new Choice();
	Choice elegirProducto = new Choice();

	Button btnAlta = new Button("Alta");

	Panel pnlEtiqueta = new Panel();
	Panel pnlClientes = new Panel();
	Panel pnlProductos = new Panel();
	Panel pnlAlta = new Panel();

	static String driver = "com.mysql.jdbc.Driver";
	static String url = "jdbc:mysql://localhost:3306/farmaciapr2?autoReconnect=true&useSSL=false";
	static String login = "admin";
	static String password = "Studium2018;";
	static String sentencia = null;
	static Connection connection = null;
	static Statement statement = null;
	static ResultSet rs = null;

	AltaCompraUsuario()
	{
		setTitle("Alta Compra");
		colocarIcono();
		setLayout(new GridLayout(4,2));
		rellenarDetallesClientes(elegirCliente);
		rellenarDetallesProductos(elegirProducto);
		pnlEtiqueta.add(lblCompra);
		pnlClientes.add(lblDNICliente);
		pnlClientes.add(elegirCliente);
		pnlProductos.add(lblProducto);
		pnlProductos.add(elegirProducto);
		pnlAlta.add(btnAlta);
		lblCompra.setFont(new java.awt.Font("Times New Roman", 1, 18));
		lblDNICliente.setFont(new java.awt.Font("Times New Roman", 0, 14));
		lblProducto.setFont(new java.awt.Font("Times New Roman", 0, 14));
		btnAlta.setFont(new java.awt.Font("Times New Roman", 0, 14));
		add(pnlEtiqueta, BorderLayout.NORTH);
		add(pnlClientes, BorderLayout.CENTER);
		add(pnlProductos, BorderLayout.CENTER);
		add(pnlAlta, BorderLayout.SOUTH);
		btnAlta.addActionListener(this);
		setSize(600,440);
		setLocationRelativeTo(null);
		setResizable(false);
		addWindowListener(this);
		setVisible(true);
	}

	public void colocarIcono() {
		Toolkit mipantalla = Toolkit.getDefaultToolkit();
		Image miIcono = mipantalla.getImage("farmacia.png");
		setIconImage(miIcono);
	}

	public static void rellenarDetallesClientes(Choice elegirCliente) {
		try
		{
			Class.forName(driver);
			connection = DriverManager.getConnection(url, login, password);
			//Crear una sentencia
			statement = connection.createStatement();
			sentencia = "SELECT * FROM clientes;";
			// Ejecutar la sentencia
			rs = statement.executeQuery(sentencia);
			while(rs.next()) 
			{
				String dniCliente = rs.getString("dniCliente");
				String nombreCliente = rs.getString("nombreCliente");
				String apellidosCliente = rs.getString("apellidosCliente");
				elegirCliente.add(dniCliente + " " + nombreCliente + " " + apellidosCliente);
			}
		}

		catch (ClassNotFoundException cnfe)
		{
			JOptionPane.showMessageDialog(null, "Hay un problema al cargar el driver", "Error", JOptionPane.ERROR_MESSAGE);
		}
		catch (SQLException sqle)
		{
			JOptionPane.showMessageDialog(null, "Error en el Alta", "Error", JOptionPane.ERROR_MESSAGE);
		}
		desconectar();
	}

	public static void rellenarDetallesProductos(Choice elegirProducto) {
		try
		{
			Class.forName(driver);
			connection = DriverManager.getConnection(url, login, password);
			//Crear una sentencia
			statement = connection.createStatement();
			sentencia = "SELECT * FROM productos;";
			// Ejecutar la sentencia
			rs = statement.executeQuery(sentencia);

			while(rs.next()) 
			{
				int idProducto = rs.getInt("idProducto");
				int contenidototalProducto = rs.getInt("contenidototalProducto");
				String nombreProducto = rs.getString("nombreProducto");
				String marcaProducto = rs.getString("marcaProducto");
				float precioProducto = rs.getFloat("precioProducto");
				String fechacaducidadProducto = rs.getString("fechacaducidadProducto");
				elegirProducto.add(idProducto + " " + contenidototalProducto + " " + nombreProducto + " " + marcaProducto + " " + precioProducto + " " + fechacaducidadProducto);
			}
		}

		catch (ClassNotFoundException cnfe)
		{
			JOptionPane.showMessageDialog(null, "Hay un problema al cargar el driver", "Error", JOptionPane.ERROR_MESSAGE);
		}
		catch (SQLException sqle)
		{
			JOptionPane.showMessageDialog(null, "Error en el Alta", "Error", JOptionPane.ERROR_MESSAGE);
		}
		desconectar();
	}

	public static void desconectar(){
		try
		{
			if(connection!=null)
			{
				connection.close();
			}
		}
		catch (SQLException se)
		{
			JOptionPane.showMessageDialog(null, "No se puede cerrar la conexión con la BD", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (btnAlta.equals(arg0.getSource())) {
			String [] elegir= elegirCliente.getSelectedItem().split(" ");
			String escogerCliente = elegir[0];
			String [] elegir2 = elegirProducto.getSelectedItem().split(" ");
			String escogerProducto = elegir2[0];

			try
			{
				Class.forName(driver);
				connection = DriverManager.getConnection(url, login, password);
				statement = connection.createStatement();
				sentencia = "INSERT INTO compra VALUES(NULL, '"+escogerProducto+"', '"+escogerCliente+"');";
				statement.executeUpdate(sentencia);
				JOptionPane.showMessageDialog(null, "Se ha realizado el Alta de Compra con éxito", "Éxito en el Alta", JOptionPane.INFORMATION_MESSAGE);
				Guardar_Movimientos gm = new Guardar_Movimientos();
				try {
					gm.registrar("usuario]" + "["+sentencia+"");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			catch (ClassNotFoundException cnfe)
			{
				JOptionPane.showMessageDialog(null, "Hay un problema al cargar el driver", "Error", JOptionPane.ERROR_MESSAGE);
			}
			catch (SQLException sqle)
			{
				JOptionPane.showMessageDialog(null, "Error, en el Alta", "Error", JOptionPane.ERROR_MESSAGE);
			}
			desconectar();
		}
	}
	public void windowActivated(WindowEvent arg0) {}
	public void windowClosed(WindowEvent arg0) {}

	@Override
	public void windowClosing(WindowEvent arg0) {
		if (this.isActive()) {
			this.setVisible(false);
			new MenuPrincipalUsuario();
		}	
	}

	public void windowDeactivated(WindowEvent arg0) {}
	public void windowDeiconified(WindowEvent arg0) {}
	public void windowIconified(WindowEvent arg0) {}
	public void windowOpened(WindowEvent arg0) {}
}