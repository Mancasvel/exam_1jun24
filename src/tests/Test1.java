package tests;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import exercises.FactoriaVisitas;
import exercises.Visita;

public class Test1 {
	
	public static void main(String[] args) {
		String ruta = "src/csv/hamburguesas.csv";
				
				try(BufferedReader br = new BufferedReader(new FileReader(ruta))){ // try lectura
					
					String linea;
					
					boolean primeraLinea = true;
					try {
						while((linea = br.readLine()) != null) {
							if(primeraLinea) {
								primeraLinea = false; // saltamos la cabecera
								continue;
							}
							System.out.println("Linea csv:" + linea);
							
							Visita visita = null;
							try {
								visita = FactoriaVisitas.parseaVisita(linea);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							System.out.println("Viaje parseado:" + visita);
							
							
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		
	}

}
