package exercises;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Competicion {

    private final SortedSet<Visita> visitas;

    public Competicion(Stream<Visita> streamVisitas) {
        Comparator<Visita> comparador = Comparator
                .comparing(Visita::getCodigoPostal)
                .thenComparing(Comparator.naturalOrder());

        this.visitas = streamVisitas
                .collect(Collectors.toCollection(() -> new TreeSet<>(comparador)));
    }

    public SortedSet<Visita> getVisitas() {
        // Devolvemos unset inmutable para evitar modificaciones externas
        return Collections.unmodifiableSortedSet(visitas);
    }

    @Override
    public String toString() {
        return visitas.stream()
                .map(Visita::toString)
                .collect(Collectors.joining("\n"));
    }

    @Override
    public int hashCode() {
        return Objects.hash(visitas);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Competicion)) return false;
        Competicion other = (Competicion) obj;
        return Objects.equals(this.visitas, other.visitas);
    }
    
    
    
	public SortedSet<String> getEmailsOrdenados(Duration d){
    	Double temperaturaMedia = visitas.stream()
    			.mapToDouble(visita -> visita.getTemperatura())
    			.average()
    			.orElse(0.0);
    	
    	return (SortedSet<String>) visitas.stream()
    			.filter(visita -> visita.getTemperatura() < temperaturaMedia)
    			.filter(visita -> visita.getTiempoTranscurrido().minus(d).compareTo(Duration.ZERO)> 0)
    			.map(visita -> visita.getEmail())
    			.sorted()
    			.collect(Collectors.toSet());
    	
    }
	
	public Integer getTotalVisitasComilones() {
		
		Double mediaEvaluaciones = visitas.stream()
				.mapToInt(visita -> visita.getNumEvaluaciones())
				.average()
				.orElse(0.0);
		
		return (int) visitas.stream()
				.filter(visita -> visita.getNumEvaluaciones()> mediaEvaluaciones)
				.count();

	}
	
	public String getPeorHamburgueseriaPorCalidadIngredientes() {
		
		Map<String, Double> hamburgueseriaNotasIngredientes = visitas.stream()
				.flatMap(visita -> visita.getEvaluaciones().stream())
				.collect(Collectors.groupingBy( 
						evaluacion -> evaluacion.hamburgueseria(),
						() -> new HashMap<>(),
						Collectors.averagingDouble(evaluacion -> evaluacion.calidadIngredientes())));
		
		return hamburgueseriaNotasIngredientes.entrySet().stream()
				.min(Comparator.comparingDouble(par -> par.getValue()))
				.map(par -> par.getKey())
				.orElse(null);
	}
}

