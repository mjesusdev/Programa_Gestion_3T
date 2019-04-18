package es.studium.programagestion;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class Programa_Gestion_V2 implements WindowListener, ActionListener{

	// Crear una ventana
	Frame miPrograma = new Frame("Farmacia");
	// Crear un par de etiquetas una para el usuario y otra para la clave
	Label ltdUsuario = new Label("Usuario:");
	// Crear dos Campos de Texto para poder escribir el usuario y la clave
	TextField txtUsuario = new TextField(15);
	Label ltdClave = new Label("Clave:");
	TextField txtClave = new TextField(15);
	// Tres botones
	Button btnLogin = new Button("Login");
	Button btnLimpiar = new Button("Limpiar");
	Button btnOlvidar = new Button("Olvid� la clave");

	// Di�logo para la recuperaci�n de la cuenta
	Dialog recuperacion = new Dialog(miPrograma, true);

	// Di�logo para cuando ya se ha puesto el correo para la recuperaci�n
	Dialog recuperacion2 = new Dialog(miPrograma, true);

	// Di�logo para login incorrecto
	Dialog loginincorrecto = new Dialog(miPrograma, true);

	// Componentes para el login incorrecto
	Label ltdIncorrecto = new Label("Datos de Login incorrectos");
	Button btnAceptarIn = new Button("Aceptar");

	// Paneles para el Frame Principal
	Panel pnlSuperior1 = new Panel();
	Panel pnlCentral1 = new Panel();
	Panel pnlInferior1 = new Panel();
	Panel pnlInferior2 = new Panel();
	
	// Paneles para el Di�logo
	Panel pnlSuperior = new Panel();
	Panel pnlCentral = new Panel();
	Panel pnlInferior = new Panel();

	// Componentes para el Di�logo
	Label ltdIndicar = new Label("Indicar tu correo electr�nico");
	TextField txtCorreo = new TextField(18);
	Button btnAceptar = new Button("Aceptar");

	// Componentes para el Segundo Di�logo
	Label ltdCorreo = new Label("Se ha enviado un correo electr�nico a su cuenta");
	Button btnAceptar2 = new Button("Aceptar");

	// Paneles para el Segundo Di�logo
	Panel pnlSuperior2 = new Panel();
	Panel pnlCentral2 = new Panel();

	Programa_Gestion_V2()
	{
		// Establecer Layout para 2 filas y 4 columnas
		miPrograma.setLayout(new GridLayout(4,2));
		// A�adir etiquetas, campos de texto, etc.
		pnlSuperior1.add(ltdUsuario);
		pnlSuperior1.add(txtUsuario);
		pnlCentral1.add(ltdClave);
		pnlCentral1.add(txtClave);
		pnlInferior1.add(btnLogin);
		pnlInferior1.add(btnLimpiar);
		pnlInferior2.add(btnOlvidar);
		// Localizaci�n paneles
		miPrograma.add(pnlSuperior1, BorderLayout.CENTER);
		miPrograma.add(pnlCentral1, BorderLayout.CENTER);
		miPrograma.add(pnlInferior1, BorderLayout.CENTER);
		miPrograma.add(pnlInferior2, BorderLayout.CENTER);
		
		// Remplazar contenido de txtClave por aster�sco:
		txtClave.setEchoChar('*');
		// Establecer Valores para posicionar el frame en la pantalla
		miPrograma.setBounds(800,400,400,200);
		// Establecer al medio la ventana, dependiendo de cual sea la resoluci�n.
		miPrograma.setLocationRelativeTo(null);
		miPrograma.setResizable(false);
		// A�adir el WindowListener
		miPrograma.addWindowListener(this);
		// T�tulo al di�logo
		recuperacion.setTitle("Olvid� la clave");
		// T�tulo al di�logo2
		recuperacion2.setTitle("Correcto");
		// Tama�o al di�logo
		recuperacion.setSize(200,150);
		recuperacion.setLocation(200,200);
		// Tama�o al di�logo 2
		recuperacion2.setSize(300,110);
		recuperacion2.setLocationRelativeTo(null);
		// Ubicaci�n de los paneles
		recuperacion.add(pnlSuperior, "North");
		recuperacion.add(pnlCentral, "Center");
		recuperacion.add(pnlInferior, "South");
		// A�adirle al dialogo los siguientes componentes
		pnlSuperior.add(ltdIndicar);
		pnlCentral.add(txtCorreo);
		pnlInferior.add(btnAceptar);
		// Ubicaci�n paneles en el segundo di�logo
		recuperacion2.add(pnlSuperior2, "North");
		recuperacion2.add(pnlCentral2, "Center");
		// A�adirle al segundo di�logo los siguientes componentes
		pnlSuperior2.add(ltdCorreo);
		pnlCentral2.add(btnAceptar2);
		// A�adir al di�logo login incorrecto un LABEL
		loginincorrecto.setTitle("Error");
		loginincorrecto.add(ltdIncorrecto);
		loginincorrecto.add(btnAceptarIn);
		loginincorrecto.setLayout(new FlowLayout());
		loginincorrecto.setSize(200,110);
		loginincorrecto.setLocationRelativeTo(null);
		loginincorrecto.setResizable(false);
		loginincorrecto.addWindowListener(this);
		btnAceptarIn.addActionListener(this);
		// A�adir al bot�n Limpiar el ActionListener
		btnLogin.addActionListener(this);
		btnLimpiar.addActionListener(this);
		btnOlvidar.addActionListener(this);
		btnAceptar.addActionListener(this);
		btnAceptar2.addActionListener(this);
		miPrograma.setVisible(true);
	}

	public static void main(String[] args) {
		new Programa_Gestion_V2();
	}

	@Override
	public void windowActivated(WindowEvent arg0) {}

	@Override
	public void windowClosed(WindowEvent arg0) {}

	@Override
	public void windowClosing(WindowEvent arg0) {
		// Para cerrar el programa cuando pinchas en el bot�n de cerrar
		if (loginincorrecto.isActive()) {
			loginincorrecto.setVisible(false);
			new Programa_Gestion();
		}else {
			System.exit(0);
		}
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {}

	@Override
	public void windowDeiconified(WindowEvent arg0) {}

	@Override
	public void windowIconified(WindowEvent arg0) {}

	@Override
	public void windowOpened(WindowEvent arg0) {}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (btnLogin.equals(arg0.getSource())){
			/* // Crear objeto de la clase Fecha_Y_Hora para poder trabajar con el para guardar lo que se pone en el usuario (texto)
			Fecha_Y_Hora f = new Fecha_Y_Hora();
			try {
				// Llamando al m�todo de fecha y mandarle lo que se escribe en el texto de inicio de sesi�n
				f.registrar(txtUsuario.getText()+"]"+"[Login");
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 */

			// Guardar en las variables texto y clave lo que pongamos en los TEXTFIELD
			String texto = txtUsuario.getText();
			String clave = txtClave.getText();
			
			// Ejecutar lo de dentro del if cuando el usuario que hemos escrito es igual a "Administrador" si no se ejecuta el else
			if ((texto.equals("Administrador")) && (clave.equals("admin"))) {
				// Crear un objeto llamado mpframe para ejecutarlo desde esta clase, llamando a la clase Men�Principal
				MenuPrincipal mpframe = new MenuPrincipal(txtUsuario.getText());
				// Hacer visible el frame de "Men� Principal" llamando al objeto + el nombre del frame
				mpframe.miMenu.setVisible(true);
				// Ocultar la pantalla de inicio de sesi�n cuando se ha pulsado el bot�n login
				miPrograma.setVisible(false);
			}else if((texto.equals("Usuario")) && (clave.equals("usuario"))){
				// Crear un objeto llamado menuUsuario para ejecutarlo desde esta clase, llamando a la clase Men�PrincipalUsuario
				MenuPrincipalUsuario menuUsuario = new MenuPrincipalUsuario();
				// Hacer visible el frame de "Men� PrincipalUsuario" llamando al objeto + el nombre del frame
				menuUsuario.MenuUsuario.setVisible(true);
				// Ocultar la pantalla de inicio de sesi�n cuando se ha pulsado el bot�n login
				miPrograma.setVisible(false);

			}else{
				loginincorrecto.setVisible(true);
				miPrograma.setVisible(false);
			}
		}	

		if (btnLimpiar.equals(arg0.getSource())){
			// Seleccionar contenido del textField Usuario y limpiarlo
			txtUsuario.selectAll();
			txtUsuario.setText("");

			// Seleccionar contenido del textField Clave y limpiarlo
			txtClave.selectAll();
			txtClave.setText("");
		}

		if (btnOlvidar.equals(arg0.getSource())) {
			// Hacer visible el di�logo
			recuperacion.setVisible(true);
		}

		if (btnAceptar.equals(arg0.getSource())){
			recuperacion.setVisible(false);
			recuperacion2.setVisible(true);
		}

		if (btnAceptar2.equals(arg0.getSource())){
			recuperacion2.setVisible(false);
			miPrograma.setVisible(true);
		}

		if (btnAceptarIn.equals(arg0.getSource())) {
			loginincorrecto.setVisible(false);
			new Programa_Gestion();
		}
	}
}