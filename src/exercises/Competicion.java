package exercises;

import java.time.Duration;
import java.time.LocalDate;
import java.util.*;
import java.util.Map.Entry;
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
    	
    	return visitas.stream()
    			.filter(visita -> visita.getTemperatura()< temperaturaMedia && visita.getTiempoTranscurrido().compareTo(d) > 0)
    			.sorted(Comparator.comparing(visita -> visita.getEmail()))
    			.map(visita -> visita.getEmail())
    			.collect(Collectors.toCollection(()-> new TreeSet<>()));

    }
    
    
    public Integer getTotalVisitasComilones() {
    	
    	Double mediaEvaluaciones = visitas.stream()
    			.mapToInt(visita -> visita.getNumEvaluaciones())
    			.average()
    			.orElse(0.0);
    	
    	return (int) visitas.stream()
    			.filter(visita -> visita.getNumEvaluaciones() > mediaEvaluaciones)
    			.count();
		
    }
    
    public String getPeorHamburgueseriaPorCalidadIngredientes() {
    	
    	Map<String, Double> hamburgueseriaCalidad =  visitas.stream()
    			.flatMap(visita -> visita.getEvaluaciones().stream())
    			.collect(Collectors.groupingBy(
    					evaluacion -> evaluacion.hamburgueseria(),
    					Collectors.averagingDouble(evaluacion -> evaluacion.calidadIngredientes())
    					));
    	
    	return hamburgueseriaCalidad.entrySet().stream()
    			.min(Comparator.comparing(mapa -> mapa.getValue()))
    			.map(mapa -> mapa.getKey())
    			.orElse(null);

    }
    
    public Map<String, String> getTopComilonPorCPEnDia(LocalDate d){
    	
    	return visitas.stream()
    			.filter(visita -> visita.getEntrada().toLocalDate().equals(d))
    			.collect(Collectors.groupingBy(
    					visita -> visita.getCodigoPostal(),
    					() -> new HashMap<String, String>(),
    					Collectors.collectingAndThen(
    							Collectors.maxBy(Comparator.comparing(visita -> ((Visita) visita).getNumEvaluaciones())
    									.thenComparing(Comparator.comparing(visita -> ((Visita) visita).getEntrada())))
    							, opt -> opt.map(visita -> visita.getEmail()).orElse("No hay ningun maximo comilon"))
    					));
    }

    public String getHamburgueseriaGanadora() {
    	
    	String res = null;
    	Map<String, List<Double>> hamburgueseriaNotas = new HashMap<>();
    	Map<String, Double> hamburgueseriaMedia = new HashMap<>();
    	
    	
    	
    	// hamburgueseria y notas de cada persona
    	for(Visita visita: visitas) {
    		
    		List<Evaluacion> evaluaciones = visita.getEvaluaciones();
    		for(Evaluacion evaluacion: evaluaciones) {
    			
    			Double nota = evaluacion.evaluacionFinal();
    			String hamburgueseria = evaluacion.hamburgueseria();
    			hamburgueseriaNotas.putIfAbsent(hamburgueseria, new ArrayList<>());
    			hamburgueseriaNotas.get(hamburgueseria).add(nota);
    		}
    	}
    	
    	
    	// hamburgueseria y media notas
    	for(Entry<String, List<Double>> entry: hamburgueseriaNotas.entrySet()) {
    		
    		List<Double> notas = entry.getValue();
    		String hamburgueseria = entry.getKey();
    		Double notasMedia = 0.0;
    		for(Double nota: notas) {
    			notasMedia += nota;
    		}
    		hamburgueseriaMedia.putIfAbsent(hamburgueseria, notasMedia);
    		
    	}
    	// cogemos la mejor media
    	Double mejorMedia = 0.0;
    	for(Entry<String, Double> entry: hamburgueseriaMedia.entrySet()) {
    		String hamburgueseria = entry.getKey();
    		Double media = entry.getValue();
    		if(media > mejorMedia) {
    			mejorMedia = media;
    			res = hamburgueseria;
    		}
    		
    	}
    	return res;
    	
    	
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

    
}

