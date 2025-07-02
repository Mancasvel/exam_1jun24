package exercises;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import utiles.Checkers;

public class FactoriaVisitas {
	
	public  static Visita parseaVisita(String lineaCsv) {
		
		String[] partes = lineaCsv.split(";");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"); //HH para el formato de 24h y hh para el formato AM y PM
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

	private static List<Evaluacion> parseaEvaluaciones(String csv) {
		
		String limpio = csv.replace("[", "").replace("]", "");
		String[] partes = limpio.split("-");
		List<Evaluacion> res = new ArrayList<>();
		
		for(String parte: partes) {
			String[] partes2 = parte.split(":");
			Checkers.check("debe haber 2 partes", partes2.length==2);
			
			String hamburgueseria = partes2[0].trim();
			String limpio1 = partes2[1].replace("(", "").replace(")", "");
			
			String[] notas = limpio1.split(",");
			Checkers.check("debe haber 4 evaluaciones", notas.length==4);
			Integer presentacion = Integer.parseInt(notas[0].trim());
			Integer puntoCarne = Integer.parseInt(notas[1].trim());
			Integer calidadIngredientes = Integer.parseInt(notas[2].trim());
			Integer calidadPan = Integer.parseInt(notas[3].trim());
			
			
			res.add(new Evaluacion(hamburgueseria, presentacion, puntoCarne, calidadIngredientes, calidadPan));
		}
		return res;
		
	}
	
	public static Competicion leeVisitas(String rutaCsv) {
	    try {
	        Stream<Visita> streamVisitas = Files.lines(Paths.get(rutaCsv))
	                .skip(1)
	                .map(FactoriaVisitas::parseaVisita);

	        return new Competicion(streamVisitas);

	    } catch (IOException e) {
	        throw new RuntimeException("Error leyendo el csv", e);
	    }
	}




}
