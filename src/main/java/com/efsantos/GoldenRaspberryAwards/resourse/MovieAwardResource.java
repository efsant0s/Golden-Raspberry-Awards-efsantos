package com.efsantos.GoldenRaspberryAwards.resourse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.efsantos.GoldenRaspberryAwards.model.Movie;
import com.efsantos.GoldenRaspberryAwards.model.MovieAwardInterval;
import com.efsantos.GoldenRaspberryAwards.repository.MovieRepository;
import com.efsantos.GoldenRaspberryAwards.utils.Utils;
import com.opencsv.CSVReader;

@RestController
@RequestMapping(value = "/movie-award")
public class MovieAwardResource {

	@Autowired
	private MovieRepository repository;

	@GetMapping
	public ResponseEntity<List<Movie>> findAll() {
		List<Movie> list = (List<Movie>) repository.findAll();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/get-intervals")
	public ResponseEntity<Map<String, List<MovieAwardInterval>>> getMinAndMaxPeriods() {
		Map<String, List<MovieAwardInterval>> minMaxPeriod = Utils.getAwardInterval(repository);
		return ResponseEntity.ok().body(minMaxPeriod);
	}

	public void preencherDados() throws IOException {
		Resource resource = new ClassPathResource("movielist.csv");
		BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));

		try (CSVReader csvReader = new CSVReader(reader)) {
			List<Movie> movies = new ArrayList<>();
			String[] nextRecord = csvReader.readNext();
			while ((nextRecord = csvReader.readNext()) != null) {
				movies.add(Utils.mapMovie(nextRecord));
			}
			for (Iterator<Movie> iterator = movies.iterator(); iterator.hasNext();) {
				Movie movie = (Movie) iterator.next();
				repository.save(movie);
			}
		}
	}

	@PostMapping(value = "/create")
	public ResponseEntity<?> save(@RequestBody Movie movie) {
		Movie wm = repository.save(movie);
		return ResponseEntity.created(null).body(wm);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<?> find(@PathVariable Long id) {
		if (id == null) {
			return ResponseEntity.badRequest().build();
		}
		Optional<Movie> optionalMovie = repository.findById(id);
		if (optionalMovie.equals(Optional.empty())) {
			return ResponseEntity.notFound().build();
		}
		Movie movie = optionalMovie.get();
		return ResponseEntity.ok().body(movie);

	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		if (id == null) {
			return ResponseEntity.badRequest().build();
		}
		Optional<Movie> optionalMovie = repository.findById(id);
		if (optionalMovie.equals(Optional.empty())) {
			return ResponseEntity.notFound().build();
		}
		repository.deleteById(id);
		return ResponseEntity.ok().body(optionalMovie.get());
	}

	public void limpaDados() {
		repository.deleteAll();
	}

}
