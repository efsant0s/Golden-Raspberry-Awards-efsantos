package com.efsantos.GoldenRaspberryAwards.resourse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
	@PutMapping(value = "/edit")
	public ResponseEntity<?> update(@RequestBody Movie worst) {
		if (worst.getId() == 0) {
			return ResponseEntity.badRequest().build();
		}
		Optional<Movie> optionalWorst = repository.findById(worst.getId());
		if (optionalWorst.equals(Optional.empty())) {
			return ResponseEntity.notFound().build();
		}
		Movie wm = repository.save(worst);
		return ResponseEntity.ok().body(wm);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> find(@PathVariable Long id) {
		if (id == null) {
			return ResponseEntity.badRequest().build();
		}
		Optional<Movie> optionalWorst = repository.findById(id);
		if (optionalWorst.equals(Optional.empty())) {
			return ResponseEntity.notFound().build();
		}
		Movie worst = optionalWorst.get();
		return ResponseEntity.ok().body(worst);

	}
	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		if (id == null) {
			return ResponseEntity.badRequest().build();
		}
		Optional<Movie> optionalWorst = repository.findById(id);
		if (optionalWorst.equals(Optional.empty())) {
			return ResponseEntity.notFound().build();
		}
		repository.deleteById(id);
		return ResponseEntity.ok().body(optionalWorst.get());
	}
	@GetMapping(value = "/get-movie-by-producers") 
	public ResponseEntity<Map<String, List<Movie>>> getMoviesByProducers() {
		Map<String, List<Movie>> getMovieByProducers = Utils.getMoviesByProducers(repository, false);
		return ResponseEntity.ok().body(getMovieByProducers);
	}
	@GetMapping(value = "/get-intervals")
	public ResponseEntity<Map<String, List<MovieAwardInterval>>> getMinAndMaxPeriods() {
		Map<String, List<MovieAwardInterval>> minMaxPeriod = Utils.getAwardInterval(repository);
		return ResponseEntity.ok().body(minMaxPeriod);
	}

	@PostMapping(value = "/create")
	public ResponseEntity<?> save(@RequestBody Movie worst) {
		Movie wm = repository.save(worst);
		return ResponseEntity.created(null).body(wm);
	}




}
