package es.studium.programagestion;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.*;

import javax.swing.JOptionPane;

public class AltaEmpleados extends Frame implements ActionListener, WindowListener{

	private static final long serialVersionUID = 1L;
	
	// Crear componentes
	Label lblAlta = new Label("Alta Empleados");
	Label lblNombre = new Label("Nombre:  ");
	Label lblApellidos = new Label("Apellidos:");
	TextField txtNombre = new TextField(15);
	TextField txtApellidos = new TextField(15);
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
	
	AltaEmpleados()
	{
		// Almacenamos en mipantalla el sistema nativo de pantallas, el tamaño por defecto de la pantalla, nos servira para poner el icono
		Toolkit mipantalla = Toolkit.getDefaultToolkit();
		
		setTitle("Alta Empleados");
		pnlSuperior.add(lblAlta);
		// Aplicarle una fuente a la etiqueta y tamaño
		lblAlta.setFont(new java.awt.Font("Times New Roman", 1, 18)); 
		pnlComponentes.add(lblNombre);
		lblNombre.setFont(new java.awt.Font("Times New Roman", 0, 14));
		pnlComponentes.add(txtNombre);
		pnlComponentes.add(lblApellidos);
		lblApellidos.setFont(new java.awt.Font("Times New Roman", 0, 14));
		pnlComponentes.add(txtApellidos);
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
		setSize(270,180);
		setLocationRelativeTo(null);
		// Añadir Listeners
		btnAlta.addActionListener(this);
		btnLimpiar.addActionListener(this);
		addWindowListener(this);
		setResizable(false);
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		String NombreEmpleado = txtNombre.getText();
		String ApellidosEmpleado = txtApellidos.getText();

		if (btnAlta.equals(arg0.getSource())) {
			// Si están vacíos los campos
			if (NombreEmpleado.equals("") | ApellidosEmpleado.equals("")) {
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
					sentencia = "INSERT INTO empleados VALUES(NULL, '"+NombreEmpleado+"', '"+ApellidosEmpleado+"');";
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
		
		if (btnLimpiar.equals(arg0.getSource())){
			// Seleccionar contenido del textField Nombre y limpiarlo
			txtNombre.selectAll();
			txtNombre.setText("");

			// Seleccionar contenido del textField APELLIDOS Y limpiarlo
			txtApellidos.selectAll();
			txtApellidos.setText("");
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