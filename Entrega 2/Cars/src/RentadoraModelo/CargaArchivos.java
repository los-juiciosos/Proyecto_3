package RentadoraModelo;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Map.Entry;

import javax.swing.JTable;

import java.util.Properties;
import java.util.Set;

public class CargaArchivos {

	public ArrayList<String> cargarSedes(){
		
		Properties pSedes = new Properties();
		try {
		pSedes.load(new FileInputStream(new File("./RentadoraStorage/Sedes.txt")));
		ArrayList<String> sedes = new ArrayList<>();
		
		for (Object sede: pSedes.keySet()) {
			sedes.add((String)sede);
			
		}
		
		return sedes;
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Object[][] cargarAutos(String sede){
			
		Properties pCarros = new Properties();
		try {
			pCarros.load(new FileInputStream(new File("./RentadoraStorage/Vehiculo.txt")));
			ArrayList<String[]> carros = new ArrayList<>();
			
			Set<Entry<Object, Object>> entrySet = pCarros.entrySet();
	        for (Entry<Object, Object> entry : entrySet) {
	            Object placa = entry.getKey();
	            String valores = (String) entry.getValue();
	            
	            String[] carro = valores.split(";");
	            
	            if (carro[9].equals(sede)) {
	            	carros.add(carro);
	            }
	        }
		
		return carros.toArray(new Object[0][0]);
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
		
	public boolean login(String user, String password) {
		Properties pLogin = new Properties();
		try {
			pLogin.load(new FileInputStream(new File("./RentadoraStorage/Login.txt")));
			Set<Object> llavesLogin = pLogin.keySet();
			
			if (llavesLogin.contains(user)) {
				System.out.println("Ingrese su contraseña");
				String infoLogin = (String) pLogin.get(user);
				String[] listaInfoLogin = infoLogin.split(";");
				
				if (password.equals(listaInfoLogin[0])) {
					return true;
//						String rolUsuario = (String) listaInfoLogin[listaInfoLogin.length-1];
//						menuUsuario(rolUsuario, listaInfoLogin,user);
				}
				
				else {
					return false;
					//Contraseña Incorrecta, no tiene acceso 
				}
			}
			
			else {
				return false;
				//No está en nuestro sistema, por favor registrese
			}
			
			
			}
		catch (Exception e) {
			e.printStackTrace();
		}
	return false;
	}

}