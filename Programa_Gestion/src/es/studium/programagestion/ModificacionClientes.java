package es.studium.programagestion;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GridLayout;
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
	Choice seleccionarCliente = new Choice();
	Button btnModificacion = new Button("Modificación");

	// Panel para el Choice y el botón
	Panel pnlChoice = new Panel();
	Panel pnlBoton = new Panel();

	// Crear diálogo
	Dialog DialogoMod = new Dialog(this, true);

	// Crear componentes
	Label lblNombre = new Label("Nombre:");
	TextField txtNombre = new TextField();
	Label lblApellidos = new Label("Apellidos");
	TextField txtApellidos = new TextField();
	Label lblDNI = new Label("DNI:");
	TextField txtDNI = new TextField();
	Button btnRealizarModificacion = new Button("Modificar Cliente");
	Button btnLimpiar = new Button("Limpiar");

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
		// Título e icono
		setTitle("Modificación Clientes");
		colocarIcono();

		// Añadir elementos
		pnlChoice.add(seleccionarCliente);
		pnlBoton.add(btnModificacion);
		seleccionarCliente.add("Seleccione cliente a modificar");
		// Llamar al método que selecciona los Clientes
		introducirClientes(seleccionarCliente);
		add(pnlChoice, "North");
		add(pnlBoton, "Center");
		// Añadir Windowlistener
		addWindowListener(this);
		// Añadir un Actionlistener al botón de Modificación 
		btnModificacion.addActionListener(this);

		// Diálogo donde se realiza la modificación
		DialogoMod.setTitle("Modificación Clientes");
		DialogoMod.setLayout(new GridLayout(4,2));
		DialogoMod.add(lblNombre);
		DialogoMod.add(txtNombre);
		DialogoMod.add(lblApellidos);
		DialogoMod.add(txtApellidos);
		DialogoMod.add(lblDNI);
		DialogoMod.add(txtDNI);
		DialogoMod.add(btnRealizarModificacion);
		DialogoMod.add(btnLimpiar);
		DialogoMod.setSize(300,250);
		DialogoMod.setResizable(false);
		DialogoMod.setLocationRelativeTo(null);
		btnRealizarModificacion.addActionListener(this);
		btnLimpiar.addActionListener(this);
		DialogoMod.addWindowListener(this);
		DialogoMod.setVisible(false);

		setSize(300,150);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}

	public void colocarIcono() {
		Toolkit mipantalla = Toolkit.getDefaultToolkit();
		Image miIcono = mipantalla.getImage("src//farmacia.png");
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
			System.out.println("Se produjo un error al cargar el Driver");
		}

		catch(SQLException e) {
			System.out.println("Se produjo un error al conectar con la BD");
		}

		desconectar();
	}

	public static void desconectar() {
		try {
			if (connection!=null) {
				connection.close();
			}	
		}catch(SQLException sqle) {
			System.out.println("No se puede cerrar la conexión con la Base de Datos");
		}
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (btnModificacion.equals(ae.getSource())) {
			this.setVisible(false);
			DialogoMod.setVisible(true);
		}

		else if (btnRealizarModificacion.equals(ae.getSource())) {
			if (txtNombre.getText().equals("") | txtApellidos.getText().equals("") | txtDNI.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Error", "Tiene que especificar los datos", JOptionPane.INFORMATION_MESSAGE);
			}else{
				String [] escogerdniCliente = seleccionarCliente.getSelectedItem().split(" ");
				String dniCliente = escogerdniCliente[3];

				try{
					Class.forName(driver);				
					connection = DriverManager.getConnection(url, login, password);
					statement = connection.createStatement();
					sentencia = "UPDATE clientes SET dniCliente= '"+txtDNI.getText()+"', nombreCliente= '"+txtNombre.getText()+"', "
							+ "apellidosCliente='"+txtApellidos.getText()+"' WHERE dniCliente = '"+dniCliente+"';";
					System.out.println(sentencia);
					statement.executeUpdate(sentencia);
				} 

				catch (ClassNotFoundException e) {
					System.out.println("Se produjo un error al cargar el Driver");
				}

				catch(SQLException e) {
					System.out.println("Se produjo un error al conectar con la BD");
				}

				desconectar();
				JOptionPane.showMessageDialog(null, "Modificación Correcta", "Éxito en la Modificación", JOptionPane.INFORMATION_MESSAGE);

				Guardar_Movimientos f = new Guardar_Movimientos();
				try {
					f.registrar("administrador]" + "["+sentencia+"");
				} catch (IOException e) {
					e.printStackTrace();
				}
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
			new MenuPrincipal(null);
		}
		else if(DialogoMod.isActive()) {
			DialogoMod.setVisible(false);
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