package exercises;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import utiles.Checkers;

public class Visita implements Comparable<Visita>{

	private String email;
	private String ciudad;
	private String codigoPostal;
	private LocalDateTime entrada;
	private LocalDateTime salida;
	private Double temperatura;
	private List<Evaluacion> evaluaciones;

	
	
	public Visita(String email, String ciudad, String codigoPostal, LocalDateTime entrada, LocalDateTime salida,
			Double temperatura, List<Evaluacion> evaluaciones) {
		
		Checkers.check("El email debe contener el caracter @", email.contains("@"));
		this.email = email;
		this.ciudad = ciudad;
		this.codigoPostal = codigoPostal;
		Checkers.check("El momento de salida debe ser posterior al momento de entrada", entrada.isAfter(salida));
		Checkers.check("La lista de evaluaciones debe contener al menos un elemento", !evaluaciones.isEmpty());
		this.entrada = entrada;
		this.salida = salida;
		this.temperatura = temperatura;
		Checkers.check("La lista de evaluaciones no puede estar vacia", !evaluaciones.isEmpty());
		this.evaluaciones = evaluaciones;


	}

	public Integer getNumEvaluaciones() {
		return evaluaciones.size();
	}
	
	public Duration getTiempoTranscurrido() {
		return Duration.between(entrada, salida);
	}
	
	public Paladar getPaladar() {
		
		Paladar res = Paladar.BAJO;
		Double numeroEvaluacion =  evaluaciones.stream()
				.mapToDouble(evaluacion -> evaluacion.evaluacionFinal())
				.average()
				.orElse(0.0);
		
		if(numeroEvaluacion<= 6) {
			res = Paladar.ALTO;
			
		}
		if(numeroEvaluacion > 6  && numeroEvaluacion < 9) {
			res =  Paladar.MEDIO;
		}
		return res;
	}
	
	
	public LocalDateTime getSalida() {
		return salida;
	}


	public void setSalida(LocalDateTime salida) {
		Checkers.check("El momento de salida debe ser posterior al momento de entrada", entrada.isAfter(salida));
		Checkers.check("La lista de evaluaciones debe contener al menos un elemento", !evaluaciones.isEmpty());
		this.salida = salida;
	}

	public void setTemperatura(Double temperatura) {
		this.temperatura = temperatura;
	}


	public Double getTemperatura() {
		return temperatura;
	}




	public String getEmail() {

		return email;
	}


	public String getCiudad() {
		return ciudad;
	}


	public String getCodigoPostal() {
		return codigoPostal;
	}


	public LocalDateTime getEntrada() {
		return entrada;
	}

	public List<Evaluacion> getEvaluaciones() {
		return new ArrayList<>(evaluaciones);
	}


	@Override
	public int hashCode() {
		return Objects.hash(email, entrada, salida);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Visita other = (Visita) obj;
		return Objects.equals(email, other.email) && Objects.equals(entrada, other.entrada)
				&& Objects.equals(salida, other.salida);
	}






	@Override
	public int compareTo(Visita o) {
		int resultadoEntrada = entrada.compareTo(o.entrada);
		if(resultadoEntrada != 0) {
			return resultadoEntrada;
		}
		
		return salida.compareTo(o.salida);
	}

}
