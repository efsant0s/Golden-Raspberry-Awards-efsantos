package com.efsantos.GoldenRaspberryAwards;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.efsantos.GoldenRaspberryAwards.model.Movie;
import com.efsantos.GoldenRaspberryAwards.resourse.MovieAwardResource;

@SpringBootTest(classes = GoldenRaspberryAwardsEfsantosApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private MovieAwardResource movieService;

	@Autowired
	private TestRestTemplate restTemplate;

	@BeforeEach
	void contextLoads() throws IOException {
		this.movieService.preencherDados();
	}

	private String getUrl() {
		return "http://localhost:" + port;
	}

	@Test
	public void getAwardDifferenceResults() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<Map> response = restTemplate.exchange(getUrl() + "/movie-award/get-intervals", HttpMethod.GET,
				entity, Map.class);
		assertThat(response.getBody() != null);
		assertThat((response.getBody() instanceof LinkedHashMap));
		LinkedHashMap<String, ArrayList<LinkedHashMap<String, String>>> retorno = (LinkedHashMap<String, ArrayList<LinkedHashMap<String, String>>>) response
				.getBody();
		assertEquals(1, retorno.get("min").get(0).get("interval"));
		assertEquals(13, retorno.get("max").get(0).get("interval"));
		this.movieService.limpaDados();
	}

	@Test
	public void findSingleMovie() throws IOException {
		this.movieService.preencherDados();
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		int idBusca = 3;
		ResponseEntity<Map> response = restTemplate.exchange(getUrl() + "/movie-award/" + idBusca, HttpMethod.GET,
				entity, Map.class);
		assertThat(response.getBody() != null);
		assertThat((response.getBody() instanceof Movie));
		this.movieService.limpaDados();

	}

	@Test
	public void findAllMoviesAndDeleteMovie() throws IOException {
		int idDelete = 1;
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<List> response = restTemplate.exchange(getUrl() + "/movie-award/", HttpMethod.GET, entity,
				List.class);
		assertThat(response.getBody() != null);
		assertThat((response.getBody() instanceof List));
		List<Movie> listaFilmes = response.getBody();
		assertEquals(206, listaFilmes.size());
		ResponseEntity<Movie> resposta = restTemplate.exchange(getUrl() + "/movie-award/delete/" + idDelete,
				HttpMethod.DELETE, entity, Movie.class);
		response = restTemplate.exchange(getUrl() + "/movie-award/", HttpMethod.GET, entity, List.class);
		listaFilmes = response.getBody();
		assertEquals(205, listaFilmes.size());
		this.movieService.limpaDados();
	}

	@Test
	public void createMovie() throws IOException {
		this.movieService.limpaDados();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Movie mov = new Movie(1992, "Batman", "Warner", "Bruce Wayne", "no");
		HttpEntity entity = new HttpEntity(mov, headers);
		ResponseEntity<Movie> cadastro = restTemplate.exchange(getUrl() + "/movie-award/create", HttpMethod.POST,
				entity, Movie.class);
		assertThat(cadastro.getBody() != null);
		assertThat((cadastro.getBody() instanceof Movie));

		entity = new HttpEntity<String>(null, headers);
		ResponseEntity<List> response = restTemplate.exchange(getUrl() + "/movie-award/", HttpMethod.GET, entity,
				List.class);
		assertThat(response.getBody() != null);
		assertThat((response.getBody() instanceof List));
		List<Movie> listaFilmes = response.getBody();
		assertEquals(1, listaFilmes.size());
		this.movieService.limpaDados();
	}
}
