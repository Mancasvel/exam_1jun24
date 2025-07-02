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
        Map<String, List<Double>> hamburgueseriaNota = new HashMap<>();
        Map<String, Double> aux = new HashMap<>();
        String res = null;

        // Recopilar todas las notas por hamburguesería
        for (Visita visita : visitas) {
            for (Evaluacion evaluacion : visita.getEvaluaciones()) {
                String hamburgueseria = evaluacion.hamburgueseria();
                Double notaFinal = evaluacion.evaluacionFinal();
                hamburgueseriaNota.putIfAbsent(hamburgueseria, new ArrayList<>());
                hamburgueseriaNota.get(hamburgueseria).add(notaFinal);
            }
        }

        // Calcular la media de notas para cada hamburguesería
        for (Map.Entry<String, List<Double>> entry : hamburgueseriaNota.entrySet()) {
            List<Double> notas = entry.getValue();
            double suma = 0.0;
            for (Double nota : notas) {
                suma += nota;
            }
            double media = notas.isEmpty() ? 0.0 : suma / notas.size();
            aux.put(entry.getKey(), media);
        }

        // Buscar la hamburguesería con mejor media
        double mejorMedia = 0.0;
        for (Map.Entry<String, Double> entry : aux.entrySet()) {
            if (entry.getValue() > mejorMedia) {
                mejorMedia = entry.getValue();
                res = entry.getKey();
            }
        }

        return res;
    }

    
}

