package com.efsantos.GoldenRaspberryAwards.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.efsantos.GoldenRaspberryAwards.model.Movie;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Long> {
	List<Movie> findByWinner(String winner); 
	List<Movie> findByWinnerAndProducersContainingIgnoreCase(String winner, String producer);
	List<Movie> findByProducersContainingIgnoreCase(String producer);
}
