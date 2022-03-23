package com.efsantos.GoldenRaspberryAwards;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File; 
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList; 
import java.util.List; 
import org.apache.commons.lang3.ArrayUtils; 
import org.junit.jupiter.api.Test; 
import org.springframework.util.ResourceUtils;
 
import com.efsantos.GoldenRaspberryAwards.model.Movie; 
import com.efsantos.GoldenRaspberryAwards.utils.Utils;
import com.opencsv.CSVReader;
 class IntegrationTests {
 

	@Test
	void contextLoads() {
		
	}

 

	@Test
	void SeparadorDeProdutores() throws IOException {
		List<Movie> StringListaProdutores = obterListaDeDados();
		for (Movie movie : StringListaProdutores) {
			String producers = movie.getProducers();
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
			// System.out.println("-----------------" + movie.getProducers() +
			// "-----------------");
			//
			// "-----------------");
			for (int i = 0; i < listaProdutores.length; i++) {
				// System.out.println(listaProdutores[i]);
			}
		}
	}

	@Test
	void LerArquivo() throws IOException {
		File file = ResourceUtils.getFile("classpath:movielist.csv");
		assertTrue(file.exists());
		FileReader filereader = new FileReader(file);
		CSVReader csvReader = new CSVReader(filereader);
		String[] nextRecord;
		while ((nextRecord = csvReader.readNext()) != null) {
			// System.out.println(nextRecord[0]);
		}
	}

	@Test
	void CriarListArquivos() throws IOException {
		File file = ResourceUtils.getFile("classpath:movielist.csv");
		assertTrue(file.exists());
		FileReader filereader = new FileReader(file);
		CSVReader csvReader = new CSVReader(filereader);
		List<Movie> movies = new ArrayList<>();
		String[] nextRecord = csvReader.readNext();
		while ((nextRecord = csvReader.readNext()) != null) {
			movies.add(Utils.mapMovie(nextRecord));
		}
		assertEquals(movies.size(), 206);
	}

	@Test
	void GarantirQueDadosSaoExatos() throws IOException {
		File file = ResourceUtils.getFile("classpath:movielist.csv");
		FileReader filereader = new FileReader(file);
		CSVReader csvReader = new CSVReader(filereader);
		List<Movie> movies = new ArrayList<>();
		String[] nextRecord = csvReader.readNext();
		while ((nextRecord = csvReader.readNext()) != null) {
			movies.add(Utils.mapMovie(nextRecord));
		}
	}

	List<Movie> obterListaDeDados() throws IOException {
		File file = ResourceUtils.getFile("classpath:movielist.csv");
		FileReader filereader = new FileReader(file);
		CSVReader csvReader = new CSVReader(filereader);
		List<Movie> movies = new ArrayList<>();
		String[] nextRecord = csvReader.readNext();
		while ((nextRecord = csvReader.readNext()) != null) {
			movies.add(Utils.mapMovie(nextRecord));
		}
		return movies;
	}

}
