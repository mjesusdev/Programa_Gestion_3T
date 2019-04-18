package es.studium.programagestion;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Guardar_Movimientos 
{
	// método para guardar la fecha, el usuario de la clase Programa_Gestion
	public void registrar(String texto) throws IOException 
	{
		Date date = new Date();
		
		//Obtenerhora y la fecha y la hora y la saca por pantalla con formato:
		DateFormat hourdateFormat = new SimpleDateFormat("[dd/MM/yyyy HH:mm:ss]");
		
		// Comunicación con el fichero 
		FileWriter fw = new FileWriter("movimientos.log", true);
		// Paso intermedio, se usa para escribir
		BufferedWriter bw = new BufferedWriter(fw);
		// Último Paso cuya función es escribir en el fichero
		PrintWriter salida = new PrintWriter(bw);
		// Guardar en el fichero la hora y la fecha
		salida.print("\n" + hourdateFormat.format(date)+ "["+texto+"]");
		// Cerrar objetos, es imprescindible ya que se puede perder paquetes a la hora de pasar información.
		salida.close();
		bw.close();
		fw.close();
	}
}