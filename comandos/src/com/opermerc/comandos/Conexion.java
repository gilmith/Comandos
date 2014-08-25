/**
 * @author xe02761
 * Clase para establecer la conexion, heredable para ser usado con una clase especifica con las operaciones a realizar
 */


package com.opermerc.comandos;
import java.io.*;

import com.jcraft.jsch.*;



public abstract class Conexion {

	protected static JSch conecta;
	protected static Session sesion;
	protected static Channel canal;
	protected static ChannelSftp c;
	protected String khfile;

	/**
	 * Constructor, estable la conexion y crea el archivo de know hosts de cada maquina.
	 * @throws InterruptedException en el caso de no poder realizar la conexion con StrictHostKeyChecking no
	 */

	public Conexion()throws InterruptedException{
		conecta = new JSch();
		conecta.setConfig("StrictHostKeyChecking", "no");
		khfile = "c:\\" + System.getProperty("user.name") + "\\Gestion\\know";
		try {
			conecta.setKnownHosts(khfile);
		} catch (JSchException e1) { 
			e1.printStackTrace();
			System.out.println("EEEEEEEEEEEEERRRRRRRRRRRRRRROOOOOOOOOOOOORRRRRRRR");
			Thread.sleep(1000);
		}
	}

	/**
	 * Metodo para volcar una excepcion de autentificacion en un fichero log + maquina.txt en la ruta c:\\usuario\Gestion\Log
	 * @param maquina
	 */
	
	private static void cambioOutput(String maquina){
		try {
			System.setOut(new PrintStream("C:\\" + System.getProperty("user.name") + "\\Gestion\\Log\\" + "log" + maquina + ".txt" ));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo para establecer la conexion con la maquina
	 * @param conecta objeto de conexion de la clase Jsch
	 * @param maquina nombre de la maquina
	 * @param password password de usuario actual, lo determina por el nombre del usuario de la sesion
	 * @return Objeto Session con la que establecer la shell
	 */
	
	protected static Session setSession(JSch conecta, String maquina, String password){
		Session ses = null;
		try {
			ses = conecta.getSession(maquina);
		} catch (JSchException e) {
			e.printStackTrace();
		}
		ses.setPassword(password);
		ses.setConfig("PreferredAuthentications", "publickey,keyboard-interactive,password");
		return ses;
	}

	/**
	 * Metodo que devuelve el prompt en el que podemos dar los comandos requeridos
	 * @param maquina nombre de la maquina
	 * @param password password de usuario actual, lo determina por el nombre del usuario de la sesion
	 * @param operpass password del usuario oper
	 * @return prompt de comandos, sera una objeto PrintStream
	 * @throws IOException Lanza una exception si no consigue establecer el canal-shell
	 */
	
	
	protected static PrintStream abrirConexionShell(String maquina, String password, String operpass) throws IOException, JSchException{
		cambioOutput(maquina);
		PrintStream commander = null;

			sesion = setSession(conecta, maquina, password);
			sesion.connect();
			canal = sesion.openChannel("shell");
			canal.setInputStream(null);
			InputStream in =  canal.getInputStream();
			OutputStream inputstream_for_the_channel = canal.getOutputStream();
			commander =	new PrintStream(inputstream_for_the_channel, true);
			in = canal.getInputStream();
			canal.setInputStream(in , true);
			canal.setOutputStream(System.out);
			canal.connect();
			return commander;
	
	}
	
	protected void abrirConexionSftp(String maquina, String password, File file) throws IOException{
		try {
			sesion = setSession(conecta, maquina, password);
			sesion.connect();
			canal = sesion.openChannel("sftp");
			canal.connect();
			c = (ChannelSftp) canal;
			String destino = "/tmp";
			c.put(file.getAbsolutePath(), destino);
		} catch (JSchException | SftpException e) {
			e.printStackTrace(new PrintStream("C:\\" + System.getProperty("user.name") + "\\Gestion\\Log\\" + "logerror" + maquina + ".txt" ));
		}
	}

	/**
	 * Metodo de cierre de conexion, cierra el canal y la sesion. 
	 */
	
	protected static void cerrarConexion(){
		canal.disconnect();
		sesion.disconnect();
	}
}

