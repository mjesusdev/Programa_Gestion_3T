package es.studium.programagestion;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Choice;
import java.awt.Dialog;
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

public class ModificacionClientes extends Frame implements ActionListener, WindowListener{

	private static final long serialVersionUID = 1L;

	// Crear componentes
	Label lblInstrucciones = new Label("Escoja cliente a modificar");
	Choice seleccionarCliente = new Choice();
	Button btnModificacion = new Button("Modificación");

	// Panel para el Choice y el botón
	Panel pnlInstrucciones = new Panel();
	Panel pnlChoice = new Panel();
	Panel pnlBoton = new Panel();

	// Crear diálogo
	Dialog DialogoMod = new Dialog(this, true);

	// Crear componentes para el diálogo que va después del Frame Principal
	Label lblModificacion = new Label("Modificación Clientes");
	Label lblNombre = new Label("Nombre:  ");
	Label lblApellidos = new Label("Apellidos:");
	Label lblDNI = new Label(" DNI:        ");
	TextField txtNombre = new TextField(15);
	TextField txtApellidos = new TextField(15);
	TextField txtDNI = new TextField(15);
	Button btnRealizarModificacion = new Button("Modificar Cliente");
	Button btnLimpiar = new Button("Limpiar");

	// Paneles para el Díalogo Mod
	Panel pnlModificacion = new Panel();
	Panel pnlComponentes = new Panel();
	Panel pnlBotones = new Panel();

	// Base De datos
	static String driver = "com.mysql.jdbc.Driver";
	static String url = "jdbc:mysql://localhost:3306/farmaciapr2?autoReconnect=true&useSSL=false";
	static String login = "admin";
	static String password = "Studium2018;";
	static String sentencia = null;
	static Connection connection = null;
	static Statement statement = null;
	static ResultSet rs = null;

	ModificacionClientes()
	{
		setTitle("Modificación Clientes");
		colocarIcono();
		pnlInstrucciones.add(lblInstrucciones);
		lblInstrucciones.setFont(new java.awt.Font("Times New Roman", 1, 18));
		pnlChoice.add(seleccionarCliente);
		pnlBoton.add(btnModificacion);
		seleccionarCliente.add("Seleccione cliente a modificar");
		// Llamar al método que selecciona los Clientes
		introducirClientes(seleccionarCliente);
		add(pnlInstrucciones, BorderLayout.NORTH);
		add(pnlChoice, BorderLayout.CENTER);
		add(pnlBoton, BorderLayout.SOUTH);
		// Añadir Windowlistener
		addWindowListener(this);
		btnModificacion.addActionListener(this);

		// Diálogo donde se realiza la modificación
		DialogoMod.setTitle("Modificación Clientes");
		pnlModificacion.add(lblModificacion);
		pnlComponentes.add(lblNombre);
		pnlComponentes.add(txtNombre);
		pnlComponentes.add(lblApellidos);
		pnlComponentes.add(txtApellidos);
		pnlComponentes.add(lblDNI);
		pnlComponentes.add(txtDNI);
		pnlBotones.add(btnRealizarModificacion);
		pnlBotones.add(btnLimpiar);
		DialogoMod.add(pnlModificacion, BorderLayout.NORTH);
		DialogoMod.add(pnlComponentes, BorderLayout.CENTER);
		DialogoMod.add(pnlBotones, BorderLayout.SOUTH);
		pnlModificacion.setFont(new java.awt.Font("Times New Roman", 1, 18));
		pnlComponentes.setFont(new java.awt.Font("Times New Roman", 0, 14));
		pnlBotones.setFont(new java.awt.Font("Times New Roman", 0, 14));
		DialogoMod.setSize(290,240);
		DialogoMod.setResizable(false);
		DialogoMod.setLocationRelativeTo(null);
		btnRealizarModificacion.addActionListener(this);
		btnLimpiar.addActionListener(this);
		DialogoMod.addWindowListener(this);
		DialogoMod.setVisible(false);

		setSize(350,250);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}

	public void colocarIcono() {
		Toolkit mipantalla = Toolkit.getDefaultToolkit();
		Image miIcono = mipantalla.getImage("farmacia.png");
		setIconImage(miIcono);
	}

	public static void introducirClientes(Choice seleccionarCliente) {
		sentencia = "SELECT * FROM clientes;";

		try{
			Class.forName(driver);				
			connection = DriverManager.getConnection(url, login, password);
			statement = connection.createStatement();
			rs = statement.executeQuery(sentencia);
			while (rs.next()) {
				String nombreCliente = rs.getString("nombreCliente");
				String apellidosCliente = rs.getString("apellidosCliente");
				String dniCliente = rs.getString("dniCliente");
				seleccionarCliente.add(nombreCliente + " " + apellidosCliente + " " + dniCliente);
			}
		} 

		catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Hay un problema al cargar el driver", "Error", JOptionPane.ERROR_MESSAGE);
		}

		catch(SQLException e) {
			JOptionPane.showMessageDialog(null, "Se ha producido un error, por favor revise lo que ha escrito", "Error", JOptionPane.ERROR_MESSAGE);
		}
		desconectar();
	}

	public static void desconectar() {
		try {
			if (connection!=null) {
				connection.close();
			}	
		}catch(SQLException sqle) {
			JOptionPane.showMessageDialog(null, "No se puede cerrar la conexión con la BD", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (btnModificacion.equals(ae.getSource())) {	
			if (seleccionarCliente.getSelectedItem().equals("Seleccione cliente a modificar")) {
				JOptionPane.showMessageDialog(null, "No puede seleccionar ese elemento, ya que es informativo", "Error", JOptionPane.INFORMATION_MESSAGE);
			}else{
				String [] escoger = seleccionarCliente.getSelectedItem().split(" ");

				try {
					String nombreCliente = escoger[0];
					txtNombre.setText(nombreCliente);
					String apellidoCliente = escoger[1];
					String apellidoCliente2 = escoger[2];
					txtApellidos.setText(apellidoCliente + " " + apellidoCliente2);
					String dniCliente = escoger[3];
					txtDNI.setText(dniCliente);

					this.setVisible(false);
					DialogoMod.setVisible(true);
				}
				catch(ArrayIndexOutOfBoundsException oe) {
					JOptionPane.showMessageDialog(null, "Arregle los errores que se producen, "
							+ "ya sea por los apellidos que deben ser 2, realice una baja de ese cliente", 
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}

		else if (btnRealizarModificacion.equals(ae.getSource())) {
			String [] escoger = seleccionarCliente.getSelectedItem().split(" ");
			String dniCliente = escoger[3];
			
			try{
				Class.forName(driver);				
				connection = DriverManager.getConnection(url, login, password);
				statement = connection.createStatement();
				sentencia = "UPDATE clientes SET dniCliente= '"+txtDNI.getText()+"', nombreCliente= '"+txtNombre.getText()+"', "
						+ "apellidosCliente='"+txtApellidos.getText()+"' WHERE dniCliente = '"+dniCliente+"';";
				statement.executeUpdate(sentencia);
				JOptionPane.showMessageDialog(null, "Modificación Correcta", "Éxito en la Modificación", JOptionPane.INFORMATION_MESSAGE);
			} 

			catch (ClassNotFoundException e) {
				JOptionPane.showMessageDialog(null, "Hay un problema al cargar el driver", "Error", JOptionPane.ERROR_MESSAGE);
			}

			catch(SQLException e) {
				JOptionPane.showMessageDialog(null, "Revise los errores que haya tenido por favor", "Error", JOptionPane.ERROR_MESSAGE);
			}

			desconectar();

			Guardar_Movimientos f = new Guardar_Movimientos();
			try {
				f.registrar("administrador]" + "["+sentencia+"");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		else if (btnLimpiar.equals(ae.getSource())) {
			// Seleccionar Nombre y limpiarlo
			txtNombre.selectAll();
			txtNombre.setText("");

			// Seleccionar Los Apellidos y limpiarlo
			txtApellidos.selectAll();
			txtApellidos.setText("");

			// Seleccionar DNI y Limpiarlo
			txtDNI.selectAll();
			txtDNI.setText("");
		}
	}	

	@Override
	public void windowClosing(WindowEvent arg0) {
		if (this.isActive()) {
			setVisible(false);
			new MenuPrincipal();
		}
		else if(DialogoMod.isActive()) {
			DialogoMod.setVisible(false);
			new ModificacionClientes();
		}
	}

	public void windowActivated(WindowEvent arg0) {}
	public void windowClosed(WindowEvent arg0) {}
	public void windowDeactivated(WindowEvent arg0) {}
	public void windowDeiconified(WindowEvent arg0) {}
	public void windowIconified(WindowEvent arg0) {}
	public void windowOpened(WindowEvent arg0) {}
}