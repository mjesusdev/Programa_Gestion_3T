package es.studium.programagestion;

import java.awt.BorderLayout;
import java.awt.Button;
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

public class AltaProductosUsuario extends Frame implements ActionListener, WindowListener{

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
	TextField txtPrecio = new TextField(15);
	TextField txtContenidoTotal = new TextField(10);
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

	AltaProductosUsuario()
	{
		setTitle("Alta Productos");
		colocarIcono();
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

		setSize(280,270);
		setLocationRelativeTo(null);
		btnAlta.addActionListener(this);
		btnLimpiar.addActionListener(this);
		addWindowListener(this);
		setResizable(false);
		setVisible(true);
	}

	public void colocarIcono() {
		Toolkit mipantalla = Toolkit.getDefaultToolkit();
		Image miIcono = mipantalla.getImage("src//farmacia.png");
		setIconImage(miIcono);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {		
		String Nombre = txtNombre.getText();
		String Marca = txtMarca.getText();
		String Precio = txtPrecio.getText();	
		String ContenidoTotal = txtContenidoTotal.getText();
		String FechaCaducidad = txtFechaCaducidad.getText();

		String quitarbarra [] = FechaCaducidad.split("/");

		String fechacaducidadamericana = quitarbarra[2] + "-" + quitarbarra[1] + "-" + quitarbarra[0];

		if (btnAlta.equals(arg0.getSource())) {
			if (Nombre.equals("") | Marca.equals("") | Precio.equals("") | ContenidoTotal.equals("") | FechaCaducidad.equals("")) {
				JOptionPane.showMessageDialog(null, "Error, por favor corrija los errores", "Error", JOptionPane.ERROR_MESSAGE);
			}else{
				try
				{
					Class.forName(driver);
					connection = DriverManager.getConnection(url, login, password);
					//Crear una sentencia
					statement = connection.createStatement();
					sentencia = "INSERT INTO productos VALUES(NULL, '"+ContenidoTotal+"', '"+Nombre+"', '"+Marca+"', '"+Precio+"', '"+fechacaducidadamericana+"');";
					// Ejecutar la sentencia
					statement.executeUpdate(sentencia);

					JOptionPane.showMessageDialog(null, "El Alta se ha realizado", "Alta Correcta", JOptionPane.INFORMATION_MESSAGE);

					Guardar_Movimientos gm = new Guardar_Movimientos();
					try {
						gm.registrar("usuario]" + "["+sentencia+"");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				catch (ClassNotFoundException cnfe){
					System.out.println("Error 1: "+cnfe.getMessage());
				}
				catch (SQLException sqle){
					JOptionPane.showMessageDialog(null, "Error en el Alta", "Error", JOptionPane.ERROR_MESSAGE);
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
			new MenuPrincipalUsuario();
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