/**
 * Clase para obtener los datos de usuario, en concreto son las password del usuario logado en la sesion 
 * y del usuario oper. Esta clase es multithread, se ejecutara mientras esta leyendo el fichero con la lista de maquinas
 */


package com.opermerc.comandos;

import java.io.*;

public class LeeDatosUsuarios implements Runnable{
	
	private Thread t;
	private BufferedReader br;
	private String  usuario, operuser;
	private String usuariopass, operpass;
	private Console consola;
	
	/**
	 * Constructor de la clase, pedira los datos por consola, cmd windows en el que la clave estara invisible. 
	 */
	
	public LeeDatosUsuarios(){
		usuario = System.getProperty("user.name");
		operuser = "oper";
		br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Tu clave");
		try {
			usuariopass = br.readLine();
			System.out.println("clave de oper");
			operpass = br.readLine();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Sobreescritura del metodo run para iniciar el hilo.
	 */
	public void run(){
		t.start();
	}
	
	/**
	 * Metodo para devolver el password de usuario
	 * @return String password de usuario
	 */
	public String getPassUsuario(){
		return usuariopass;
	}
	
	/**
	 * Metodo para devolver el pasword de oper
	 * @return String password de oper
	 */
	
	public String getOperPass(){
		return operpass;
	}

}
