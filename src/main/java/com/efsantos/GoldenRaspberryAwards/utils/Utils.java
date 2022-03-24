package com.efsantos.GoldenRaspberryAwards.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.efsantos.GoldenRaspberryAwards.model.Movie;
import com.efsantos.GoldenRaspberryAwards.model.MovieAwardInterval;
import com.efsantos.GoldenRaspberryAwards.repository.MovieRepository;

public class Utils {
	public static Movie mapMovie(String[] line) {
		String[] columns = StringUtils.join(line, ",").split(";");
		Movie mov = new Movie();
		mov.setYear(Integer.parseInt(columns[0]));
		mov.setTitle(columns[1]);
		mov.setStudios(columns[2]);
		mov.setProducers(columns[3]);
		if (columns.length > 4) {
			mov.setWinner(toBoolean(columns[4]));
		} else {
			mov.setWinner(false);
		}
		return mov;
	}

	private static boolean toBoolean(String value) {
		return "yes".equalsIgnoreCase(value);
	}

	public static LinkedHashMap<String, List<Movie>> getMoviesByProducers(MovieRepository repository,
			boolean includeOnlyWinner) {
		List<Movie> listaFilmes = repository.findByWinner("S");
		LinkedHashMap<String, List<Movie>> filmesPorProdutores = new LinkedHashMap<>();
		for (Movie movie : listaFilmes) {
			String[] listaProdutoresFilmes = movie.getProducersArray();
			for (int i = 0; i < listaProdutoresFilmes.length; i++) {
				String produtorParticipante = listaProdutoresFilmes[i];
				List<Movie> films;
				if (includeOnlyWinner) {
					films = repository.findByWinnerAndProducersContainingIgnoreCase("S", produtorParticipante);
				} else {
					films = repository.findByProducersContainingIgnoreCase(produtorParticipante);
				}
				filmesPorProdutores.put(produtorParticipante, films);
			}

		}
		return filmesPorProdutores;
	}

	public static Map<String, List<MovieAwardInterval>> getAwardInterval(MovieRepository repository) {
		Map<String, List<MovieAwardInterval>> minMaxPeriod = new HashMap<String, List<MovieAwardInterval>>();
		LinkedHashMap<String, List<Movie>> moviesPerProducer = getMoviesByProducers(repository, true);
		List<MovieAwardInterval> intervalList = new ArrayList<>();
		List<MovieAwardInterval> listIntervalsMax = new ArrayList<>();
		List<MovieAwardInterval> listIntervalsMin = new ArrayList<>();
		Integer diffTempMin = 0;
		Integer diffTempMax = 0;
		for (Map.Entry<String, List<Movie>> producerMovies : moviesPerProducer.entrySet()) {
			String produtor = producerMovies.getKey();
			List<Movie> listaFilmesPorProdutor = producerMovies.getValue();
			if (listaFilmesPorProdutor.size() >= 2) {
				for (int i = 0; i < listaFilmesPorProdutor.size() - 1; i++) {
					Integer followingWin = listaFilmesPorProdutor.get(i + 1).getYear();
					Integer previousWin = listaFilmesPorProdutor.get(i).getYear();
					Integer diferencaTempo = followingWin - previousWin;
					if (diffTempMin.equals(0) && diffTempMax.equals(0)) {
						diffTempMin = diferencaTempo;
						diffTempMin = diferencaTempo;
					} else {
						if (diferencaTempo > diffTempMax) {
							diffTempMax = diferencaTempo;
						}
						if (diferencaTempo < diffTempMin) {
							diffTempMin = diferencaTempo;
						}
					}
					intervalList.add(new MovieAwardInterval(produtor, diferencaTempo, previousWin, followingWin));
				}

			}

		}
		Collections.sort(intervalList, new Comparator<MovieAwardInterval>() {
			public int compare(MovieAwardInterval s, MovieAwardInterval s2) {
				return s.getInterval() - s2.getInterval();
			}
		});
		for (MovieAwardInterval movieAwardInterval : intervalList) {
			if (movieAwardInterval.getInterval() == diffTempMin) {
				listIntervalsMin.add(movieAwardInterval);
			} else if (movieAwardInterval.getInterval() == diffTempMax) {
				listIntervalsMax.add(movieAwardInterval);
			}
		}

		if (!intervalList.isEmpty()) {
			minMaxPeriod.put("min", listIntervalsMin);
			minMaxPeriod.put("max", listIntervalsMax);
		}
		return minMaxPeriod;
	}

	public static String[] getProducersArray(String producers) {

		if (producers == null)
			return null;
		String[] listaProdutores;
		if (producers.trim().contains(",")) {
			listaProdutores = producers.trim().split(", ");
			for (int i = 0; i < listaProdutores.length; i++) {
				String prod = listaProdutores[i];
				if (prod != null && prod.trim().toLowerCase().contains(" and ")) {
					listaProdutores = ArrayUtils.removeElement(listaProdutores, prod);
					String[] duplaProdutores = prod.trim().split(" and ");
					listaProdutores = ArrayUtils.addAll(listaProdutores, duplaProdutores);
				}
				if (prod.trim().toLowerCase().startsWith("and ")) {
					listaProdutores = ArrayUtils.removeElement(listaProdutores, prod);
					prod = prod.substring(4);
					listaProdutores = ArrayUtils.add(listaProdutores, prod);
				}
			}
		} else {
			if (producers != null && producers.trim().toLowerCase().contains(" and ")) {
				listaProdutores = producers.trim().split(" and ");
			} else {
				listaProdutores = new String[1];
				listaProdutores[0] = producers;
			}
		}

		return listaProdutores;
	}

}
