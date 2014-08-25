/**
 * Clase para leer el archivo con el listado de maquinas. 
 * El archivo debe de estar situado en la ruta c:\\usuario\Gestion\lista.txt si no estara lanzara un 
 * FileNotFoundException.
 * Esta clase es multithread, se ejecutara mientras espera los datos de usuario.
 * 
 * El fichero debera estar en un formato nombre de maquina + intro
 * lpapp501
 * lpapp502
 */

package com.opermerc.comandos;
import java.io.*;
import java.util.Vector;

public class LeerArchivo implements Runnable {
	
	private Thread t;
	private File archivo;
	private BufferedReader br;
	private Vector<String> al;
	
	/**
	 * Constructor de la clase. Busca el archivo y crea el hilo de ejecucion para su lectura. 
	 */
	
	public LeerArchivo(){
		al = new Vector<String>();
		String ruta = "C:\\";
		ruta += System.getProperty("user.name") + "\\Gestion\\lista.txt";
		archivo = new File(ruta);
		t = new Thread(this, "hilo de hosts");
		try {
			br = new BufferedReader(new FileReader(archivo));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		t.start();
	}
	
	public LeerArchivo(String ruta){
		al = new Vector<String>();
		archivo = new File(ruta);
		t = new Thread(this, "hilo de hosts");
		try {
			br = new BufferedReader(new FileReader(archivo));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		t.start();
	}
	
	/**
	 * Poliformizo el constructor para que pille solo el archivo
	 */
	
	public LeerArchivo(File archivo){
		al = new Vector<String>();
		t = new Thread(this, "hilo de hosts");
		try {
			br = new BufferedReader(new FileReader(archivo));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		t.start();
	}

	/**
	 * Sobreescritura del metodo run para iniciar el hilo con la lectura del fichero
	 */
	public void run(){
		try {
			String linea;
			while((linea = br.readLine()) != null){
				al.add(linea);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Metodo para obtener una lista objeto Vector con todos los nombres de la maquina
	 * @return Vector con el nombre de las maquinas
	 */
	
	public Vector<String> getHost(){
		return al;
	}
	/**
	 * Metodo que devuelve el ultimo nombre de maquina.
	 * @return String con el nombre maquina. 
	 */
	
	public String getUltimaMaqiuna(){
		return al.lastElement();
	}
	
}


