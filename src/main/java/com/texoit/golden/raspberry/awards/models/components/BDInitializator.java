package com.texoit.golden.raspberry.awards.models.components;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.texoit.golden.raspberry.awards.models.Movie;
import com.texoit.golden.raspberry.awards.repository.MovieRepository;

@Component
public class BDInitializator {
	
	@Autowired
	private MovieRepository indicacaoRepository;

	@PostConstruct
	private void init() {
		List<Movie> indicacoes = getIndicacoes();
		indicacaoRepository.saveAll(indicacoes);
	}
	
	private List<Movie> getIndicacoes(){
		List<Movie> indicacoes = new ArrayList<Movie>();
		try {
			CSVReader reader = createReader();
			indicacoes = reader.readAll().stream().map(this::parserCsv).collect(Collectors.toList());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return indicacoes;
	}

	private CSVReader createReader() throws FileNotFoundException {
		String moveList = getClass().getClassLoader().getResource("movielist.csv").getFile();
		CSVParser parser = new CSVParserBuilder()
			    .withSeparator(';')
			    .withIgnoreQuotations(true)
			    .build();
		CSVReader reader = new CSVReaderBuilder(new FileReader(moveList))
				.withSkipLines(1)
				.withCSVParser(parser)
				.build();
		return reader;
	}

	private Movie parserCsv(String[] data) {
		Movie movie = new Movie();
		movie.setYear(Integer.parseInt(data[0]));
		movie.setTitle(data[1]);
		movie.setStudios(data[2]);
		movie.setProducers(data[3]);
		movie.setWinner(data.length == 5 && data[4].equalsIgnoreCase("yes"));
		return movie;
	}
}
