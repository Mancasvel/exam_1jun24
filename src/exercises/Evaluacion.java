package exercises;

import utiles.Checkers;

public record Evaluacion(String hamburgueseria, Integer presentacion, Integer puntoCarne, Integer calidadIngredientes, Integer calidadPan) {
	
	public Evaluacion{
		Double evaluacionFinal;
		
		Checkers.check("El valor de la valoracion de la presentacion debe estar comprendido entre 0 y 10", (presentacion>=0 && presentacion<=10));
		Checkers.check("El valor de la valoracion del punto de la carne debe estar comprendido entre 0 y 10", (puntoCarne>=0 && puntoCarne<=10));
		Checkers.check("El valor de la valoracion de la calidad de los ingredientes debe estar comprendido entre 0 y 10", (calidadIngredientes>=0 && calidadIngredientes<=10));
		Checkers.check("El valor de la valoracion de la calidad del pan debe estar comprendido entre 0 y 10", (calidadPan>=0 && calidadPan<=10));
		
		
	}
	public Double evaluacionFinal() {
		return (double) ((presentacion + puntoCarne + calidadIngredientes + calidadPan)/4);
	}

	public String hamburgueseria() {
		return hamburgueseria;
	}

	public Integer presentacion() {
		return presentacion;
	}

	public Integer puntoCarne() {
		return puntoCarne;
	}

	public Integer calidadIngredientes() {
		return calidadIngredientes;
	}

	public Integer calidadPan() {
		return calidadPan;
	}

	
}
