package tests;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;

import exercises.Competicion;
import exercises.FactoriaVisitas;
import exercises.Visita;

public class Test1 {
	
	public static void main(String[] args) {

		Competicion visitas = new FactoriaVisitas().leeVisitas("./data/visitas.csv");
		

		System.out.println("EJ1===========================================================");
		testEmailsOrdenados(visitas, Duration.ofMinutes(240));
		testEmailsOrdenados(visitas, Duration.ofMinutes(300));
		
		System.out.println("EJ2============================================================");
		testTotalVisitasComilones(visitas);
		
		System.out.println("EJ3============================================================");
		testPeorHamburgueseriaPorCalidadIngrediente(visitas);
		
		System.out.println("EJ4============================================================");
		testgetTopComilonPorCp(visitas, LocalDate.of(2024, 6, 2));
		
		System.out.println("EJ5============================================================");
		testHamburguesaGanadora(visitas);
		
		
	}

	private static void testHamburguesaGanadora(Competicion visitas) {
		String msg = "La hamburgueseria ganadora del campeonato es:";
		String res = visitas.getHamburgueseriaGanadora();
		
		System.out.println(msg + "\n" + res);
		
	}

	private static void testgetTopComilonPorCp(Competicion visitas, LocalDate of) {
		String msg = "En el dia "+ of + "la persona que mas comio de cada CP es:";
		
		Map<String, String> res = visitas.getTopComilonPorCPEnDia(of);
		
		System.out.println(msg + "\n" + res);
		
	}

	private static void testPeorHamburgueseriaPorCalidadIngrediente(Competicion visitas) {
		
		String msg = "Si se tiene en cuenta solo la calidad de ingredientes, la peor hamburgueseria es: ";
		String res2 = visitas.getPeorHamburgueseriaPorCalidadIngredientes();
		
		System.out.println(msg + "\n" + res2);
	}

	private static void testTotalVisitasComilones(Competicion visitas) {
		String msg = "El total de visitas con un numero de evaluaciones mayor a la media es: ";
		Integer res1 = visitas.getTotalVisitasComilones();
		
		System.out.println(msg + "\n" + res1);
		
	}

	private static void testEmailsOrdenados(Competicion visitas, Duration duracion) {
		
		String msg = "Los emails de las visitas con duracion mayor a " + duracion.toMinutes() + " minutos son:";
		SortedSet<String> res = visitas.getEmailsOrdenados(duracion);
		
		System.out.println(msg + res);
	}

}
