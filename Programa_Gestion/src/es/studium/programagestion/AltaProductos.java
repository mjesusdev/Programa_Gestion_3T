package es.studium.programagestion;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JOptionPane;

public class AltaProductos extends Frame implements ActionListener, WindowListener{

	private static final long serialVersionUID = 1L;

	// Crear Componentes
	Label lblAlta = new Label("Alta Productos");
	Label lblNombre = new Label("Nombre:");
	Label lblMarca = new Label("Marca:   ");
	Label lblPrecio = new Label("Precio:   ");
	Label lblContenidoTotal = new Label("Contenido Total:  ");
	Label lblFechaCaducidad = new Label("Fecha Caducidad:");
	TextField txtNombre = new TextField(15);
	TextField txtMarca = new TextField(15);
	TextField txtContenidoTotal = new TextField(10);
	TextField txtPrecio = new TextField(15);
	TextField txtFechaCaducidad = new TextField(10);
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

	AltaProductos()
	{
		// Almacenamos en mipantalla el sistema nativo de pantallas, el tamaño por defecto de la pantalla, nos servira para poner el icono
		Toolkit mipantalla = Toolkit.getDefaultToolkit();

		setTitle("Alta Productos");
		pnlSuperior.add(lblAlta);
		pnlComponentes.add(lblNombre);
		pnlComponentes.add(txtNombre);
		pnlComponentes.add(lblMarca);
		pnlComponentes.add(txtMarca);
		pnlComponentes.add(lblPrecio);
		pnlComponentes.add(txtPrecio);
		pnlComponentes.add(lblContenidoTotal);
		pnlComponentes.add(txtContenidoTotal);
		pnlComponentes.add(lblFechaCaducidad);
		pnlComponentes.add(txtFechaCaducidad);
		pnlBotones.add(btnAlta);
		pnlBotones.add(btnLimpiar);

		// Aplicarle fuente y tamaño
		lblAlta.setFont(new java.awt.Font("Times New Roman", 1, 18));
		lblNombre.setFont(new java.awt.Font("Times New Roman", 0, 14));
		lblMarca.setFont(new java.awt.Font("Times New Roman", 0, 14));
		lblContenidoTotal.setFont(new java.awt.Font("Times New Roman", 0, 14));
		lblPrecio.setFont(new java.awt.Font("Times New Roman", 0, 14));
		lblFechaCaducidad.setFont(new java.awt.Font("Times New Roman", 0, 14));
		btnAlta.setFont(new java.awt.Font("Times New Roman", 0, 14));
		btnLimpiar.setFont(new java.awt.Font("Times New Roman", 0, 14));

		// Añadir los paneles
		add(pnlSuperior, BorderLayout.NORTH);
		add(pnlComponentes, BorderLayout.CENTER);
		add(pnlBotones, BorderLayout.SOUTH);
		
		// Establecer un icono a la aplicación
		Image miIcono = mipantalla.getImage("src//farmacia.png");
		// Colocar Icono
		setIconImage(miIcono);

		setSize(280,300);
		setLocationRelativeTo(null);
		btnAlta.addActionListener(this);
		btnLimpiar.addActionListener(this);
		addWindowListener(this);
		setResizable(false);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {		
		String Nombre = txtNombre.getText();
		String Marca = txtMarca.getText();
		String Precio = txtPrecio.getText();	
		String ContenidoTotal = txtContenidoTotal.getText();
		String FechaCaducidad = txtFechaCaducidad.getText();

		if (btnAlta.equals(arg0.getSource())) {
			try
			{
				Class.forName(driver);
				connection = DriverManager.getConnection(url, login, password);
				//Crear una sentencia
				statement = connection.createStatement();
				sentencia = "INSERT INTO productos VALUES(NULL, '"+ContenidoTotal+"', '"+Nombre+"', '"+Marca+"', '"+Precio+"', '"+FechaCaducidad+"');";
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

		if (btnLimpiar.equals(arg0.getSource())){
			// Seleccionar contenido de los textField y limpiarlos
			txtNombre.selectAll();
			txtNombre.setText("");
			txtPrecio.selectAll();
			txtPrecio.setText("");
			txtFechaCaducidad.selectAll();
			txtFechaCaducidad.setText("");
			txtContenidoTotal.selectAll();
			txtContenidoTotal.setText("");
		}
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		if (this.isActive()) {
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