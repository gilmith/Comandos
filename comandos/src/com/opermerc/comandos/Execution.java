package com.opermerc.comandos;

import java.io.*;
import java.util.Vector;

import com.jcraft.jsch.JSchException;

public class Execution extends Conexion {
	
	public Execution() throws InterruptedException {
		// TODO Auto-generated constructor stub
		super();
	}	
	


	public void exec(String maquina, String usuariopass, String operpass,
			Vector<String> vectorComandos) throws IOException, InterruptedException, JSchException {
		PrintStream commander = null;
		commander = abrirConexionShell(maquina, usuariopass, operpass);
		//comandos compilados por seguridad
		commander.println("su - oper");
		Thread.sleep(1000);
		commander.println(operpass);
		for(String com: vectorComandos){
			commander.println(com);
		}
		Thread.sleep(1000);
		Thread.sleep(1000);
		cerrarConexion();	
	}

}
