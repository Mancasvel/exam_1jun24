package exercises;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import utiles.Checkers;

public class FactoriaVisitas {
	
	public static Visita parseaVisita(String lineaCsv) {
		
		String[] partes = lineaCsv.split(";");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm");
		
		Checkers.check("Debe haber 7 partes", partes.length == 7);
		String email = partes[0].trim();
		String ciudad = partes[1].trim();
		String codigoPostal = partes[2].trim();
		LocalDateTime entrada = LocalDateTime.parse(partes[3].trim(), formatter);
		Double temperatura = Double.parseDouble(partes[4].trim());
		LocalDateTime salida = LocalDateTime.parse(partes[5].trim(), formatter);
		List<Evaluacion> evaluaciones = parseaEvaluaciones(partes[6].trim());
		
		return new Visita(email, ciudad, codigoPostal, entrada, salida, temperatura, evaluaciones);
		
	}

	private static List<Evaluacion> parseaEvaluaciones(String trim) {


		return null;
	}

}
