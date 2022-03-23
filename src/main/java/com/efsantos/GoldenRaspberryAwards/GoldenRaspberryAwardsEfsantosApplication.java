package com.efsantos.GoldenRaspberryAwards;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;

import com.efsantos.GoldenRaspberryAwards.model.Movie;
import com.efsantos.GoldenRaspberryAwards.repository.MovieRepository;
import com.efsantos.GoldenRaspberryAwards.utils.Utils;
import com.opencsv.CSVReader;

@SpringBootApplication
public class GoldenRaspberryAwardsEfsantosApplication {

	public static void main(String[] args) throws IOException {
		ConfigurableApplicationContext configurable = SpringApplication
				.run(GoldenRaspberryAwardsEfsantosApplication.class, args);
		if (args.length == 0) {
			Resource resource = new ClassPathResource("movielist.csv");
			BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
			MovieRepository repo = configurable.getBean(MovieRepository.class);
			try (CSVReader csvReader = new CSVReader(reader)) {
				List<Movie> movies = new ArrayList<>();
				String[] nextRecord = csvReader.readNext();
				while ((nextRecord = csvReader.readNext()) != null) {
					movies.add(Utils.mapMovie(nextRecord));
				}
				for (Iterator<Movie> iterator = movies.iterator(); iterator.hasNext();) {
					Movie movie = (Movie) iterator.next();
					repo.save(movie);
				}
			}
		} else {
			if (!(args[0] instanceof String) || !args[0].equals("local")) {
				throw new IOException("Caminho do arquivo CSV Incorreto!");
			} else {
				MovieRepository repo = configurable.getBean(MovieRepository.class);
				File file = ResourceUtils.getFile(new File(".").getAbsolutePath() + "/movielist.csv");
				if (!file.exists()) {
					throw new IOException("Caminho do arquivo CSV Incorreto!");
				}
				FileReader filereader = new FileReader(file);
				try (CSVReader csvReader = new CSVReader(filereader)) {
					List<Movie> movies = new ArrayList<>();
					String[] nextRecord = csvReader.readNext();
					while ((nextRecord = csvReader.readNext()) != null) {
						movies.add(Utils.mapMovie(nextRecord));
					}
					for (Iterator<Movie> iterator = movies.iterator(); iterator.hasNext();) {
						Movie movie = (Movie) iterator.next();
						repo.save(movie);
					}
				}
			}
		}

	}

}
