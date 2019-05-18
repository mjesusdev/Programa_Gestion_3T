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

public class ModificacionProductos extends Frame implements ActionListener, WindowListener{

	private static final long serialVersionUID = 1L;

	// Crear componentes
	Choice seleccionarProducto = new Choice();
	Button btnModificacion = new Button("Modificación");

	// Panel para el Choice y el botón
	Panel pnlChoice = new Panel();
	Panel pnlBoton = new Panel();

	// Crear diálogo
	Dialog DialogoMod = new Dialog(this, true);

	// Crear componentes (DIALOGOMOD)
	Label lblModificacion = new Label("Modificación Productos");
	Label lblNombre = new Label("Nombre:");
	Label lblMarca = new Label("Marca:   ");
	Label lblPrecio = new Label("Precio:   ");
	Label lblContenidoTotal = new Label("Contenido Total:  ");
	Label lblFechaCaducidad = new Label("Fecha Caducidad:");
	TextField txtNombre = new TextField(15);
	TextField txtMarca = new TextField(15);
	TextField txtPrecio = new TextField(15);
	TextField txtContenidoTotal = new TextField(10);
	TextField txtFechaCaducidad = new TextField(10);
	Button btnRealizarModificacion = new Button("Modificación");
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

	ModificacionProductos()
	{
		setTitle("Modificación Productos");
		colocarIcono();

		// Añadir elementos
		pnlChoice.add(seleccionarProducto);
		pnlBoton.add(btnModificacion);
		seleccionarProducto.add("Seleccione producto a modificar");
		introducirProductos(seleccionarProducto);
		add(pnlChoice, "North");
		add(pnlBoton, "Center");
		// Añadir Windowlistener
		addWindowListener(this);
		// Añadir Actionlistener al botón de Modificación 
		btnModificacion.addActionListener(this);

		// Diálogo donde se realiza la modificación
		DialogoMod.setTitle("Modificación Productos");
		pnlModificacion.add(lblModificacion);
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
		pnlBotones.add(btnRealizarModificacion);
		pnlBotones.add(btnLimpiar);
		DialogoMod.add(pnlModificacion, BorderLayout.NORTH);
		DialogoMod.add(pnlComponentes, BorderLayout.CENTER);
		DialogoMod.add(pnlBotones, BorderLayout.SOUTH);
		pnlModificacion.setFont(new java.awt.Font("Times New Roman", 1, 18));
		pnlComponentes.setFont(new java.awt.Font("Times New Roman", 0, 14));
		pnlBotones.setFont(new java.awt.Font("Times New Roman", 0, 14));
		DialogoMod.setSize(280,250);
		DialogoMod.setResizable(false);
		DialogoMod.setLocationRelativeTo(null);
		btnRealizarModificacion.addActionListener(this);
		btnLimpiar.addActionListener(this);
		DialogoMod.addWindowListener(this);
		DialogoMod.setVisible(false);

		setSize(400,220);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}

	public void colocarIcono() {
		Toolkit mipantalla = Toolkit.getDefaultToolkit();
		Image miIcono = mipantalla.getImage("src//farmacia.png");
		setIconImage(miIcono);
	}

	public static void introducirProductos(Choice seleccionarProducto) {
		sentencia = "SELECT * FROM productos;";

		try{
			Class.forName(driver);				
			connection = DriverManager.getConnection(url, login, password);
			statement = connection.createStatement();
			rs = statement.executeQuery(sentencia);
			while (rs.next()) {
				int idProducto = rs.getInt("idProducto");
				String nombreProducto = rs.getString("nombreProducto");
				String marcaProducto = rs.getString("marcaProducto");
				int contenidototalProducto = rs.getInt("contenidototalProducto");
				float precioProducto = rs.getFloat("precioProducto");
				String fechacaducidadProducto = rs.getString("fechacaducidadProducto");
				String quitarbarra [] = fechacaducidadProducto.split("-");
				// Cambia el - por / además de cambiar el orden para insetarlo en la BD
				String fechacaducidadamericana = quitarbarra[2] + "/" + quitarbarra[1] + "/" + quitarbarra[0];
				seleccionarProducto.add(idProducto + " " + nombreProducto + " " + marcaProducto + " " + precioProducto
						+ " " + contenidototalProducto  + " " + fechacaducidadamericana);
			}
		} 

		catch (ClassNotFoundException e) {
			System.out.println("Se produjo un error al cargar el Driver");
		}

		catch(SQLException e) {
			JOptionPane.showMessageDialog(null, "Error", "Revise los errores que haya tenido por favor", JOptionPane.ERROR_MESSAGE);
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
	public void actionPerformed(ActionEvent arg0) {
		if (btnModificacion.equals(arg0.getSource())) {
			if (seleccionarProducto.getSelectedItem().equals("Seleccione producto a modificar")) {
				JOptionPane.showMessageDialog(null, "No puede seleccionar ese elemento, ya que es informativo", "Error", JOptionPane.INFORMATION_MESSAGE);
			}else{
				String [] escoger = seleccionarProducto.getSelectedItem().split(" ");

				try {
					String nombreProducto = escoger[1];
					String nombreProductoseguido = escoger[2];
					txtNombre.setText(nombreProducto + " " + nombreProductoseguido);
				}

				catch(ArrayIndexOutOfBoundsException oe) {
					JOptionPane.showMessageDialog(null, "Arregle los errores que se producen, "
							+ "ya sea por el nombre del Producto que debe ser compuesto, realice una baja de ese producto", 
							"Error", JOptionPane.ERROR_MESSAGE);
				}

				String marcaProducto = escoger[3];
				txtMarca.setText(marcaProducto);

				String precioProducto = escoger[4];
				txtPrecio.setText(precioProducto);

				String contenidototalProducto = escoger[5];
				txtContenidoTotal.setText(contenidototalProducto);

				try {
					String fechacaducidadProducto = escoger[6];
					txtFechaCaducidad.setText(fechacaducidadProducto);
				}
				catch(ArrayIndexOutOfBoundsException oe) {
					JOptionPane.showMessageDialog(null, "Arregle los errores que se producen, "
							+ "ya sea por el nombre del Producto que debe ser compuesto, realice una baja de ese producto", 
							"Error", JOptionPane.ERROR_MESSAGE);
				}

				this.setVisible(false);
				DialogoMod.setVisible(true);
			}
		}

		else if (btnRealizarModificacion.equals(arg0.getSource())) {
			String [] escoger = seleccionarProducto.getSelectedItem().split(" ");
			String idProducto = escoger[0];

			String fecha = txtFechaCaducidad.getText();
			String [] fechaCaducidad = fecha.split("/");

			// Cambia la / por - además de cambiar el orden para insetarlo en la BD
			String fechacaducidadamericana = fechaCaducidad[2] + "-" + fechaCaducidad[1] + "-" + fechaCaducidad[0];

			try{
				Class.forName(driver);				
				connection = DriverManager.getConnection(url, login, password);
				statement = connection.createStatement();
				sentencia = "UPDATE productos SET contenidototalProducto= '"+txtContenidoTotal.getText()+"', nombreProducto= '"+txtNombre.getText()+"', "
						+ "marcaProducto='"+txtMarca.getText()+"', precioProducto= '"+txtPrecio.getText()+"', "
						+ "fechacaducidadProducto= '"+fechacaducidadamericana+"' WHERE idProducto = '"+idProducto+"';";
				statement.executeUpdate(sentencia);
				JOptionPane.showMessageDialog(null, "Modificación Correcta", "Éxito en la Modificación", JOptionPane.INFORMATION_MESSAGE);
			} 

			catch (ClassNotFoundException e) {
				System.out.println("Se produjo un error al cargar el Driver");
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

		else if (btnLimpiar.equals(arg0.getSource())){
			txtNombre.selectAll();
			txtNombre.setText("");

			txtMarca.selectAll();
			txtMarca.setText("");

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

		else if(DialogoMod.isActive()){
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