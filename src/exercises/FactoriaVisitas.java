package exercises;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FactoriaVisitas {
	
	public Visita parseaVisita(String lineaCsv) {
		
		String[] partes = lineaCsv.split(";");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm");
		
		String email = partes[0].trim();
		String ciudad = partes[1].trim();
		String codigoPostal = partes[2].trim();
		LocalDateTime entrada = LocalDateTime.parse(partes[3].trim(), formatter);
		Double temperatura = Double.parseDouble(partes[4].trim());
		LocalDateTime salida = LocalDateTime.parse(partes[5].trim(), formatter);
		List<Evaluacion> evaluaciones = parseaEvaluaciones(partes[6].trim());
		
	}

	private List<Evaluacion> parseaEvaluaciones(String trim) {


		return null;
	}

}
