package com.texoit.golden.raspberry.awards.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.texoit.golden.raspberry.awards.models.Movie;
import com.texoit.golden.raspberry.awards.repository.MovieRepository;
import com.texoit.golden.raspberry.awards.util.IntervalWinMinMaxUtil;
import com.texoit.golden.raspberry.awards.vo.IntervalWinMinMaxVO;

@RestController
@RequestMapping("movies")
public class MovieController {
	
	@Autowired
	private MovieRepository movieRepository;
	
	@PostMapping
	public ResponseEntity<Movie> save(@RequestBody Movie movie){
		movieRepository.save(movie);
		return new ResponseEntity<Movie>(movie, HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<Movie>> getAll(){
		List<Movie> movies = movieRepository.findAll();
		return new ResponseEntity<List<Movie>>(movies, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Movie> getById(@PathVariable("id") Long id){
		Optional<Movie> movieOptional = movieRepository.findById(id);
		if(movieOptional.isPresent()) {
			return new ResponseEntity<Movie>(movieOptional.get(), HttpStatus.OK);	
		}else {
			return new ResponseEntity<Movie>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Movie> update(@PathVariable("id") Long id, @RequestBody Movie movie){
		Optional<Movie> movieOptional = movieRepository.findById(id);
		if(movieOptional.isPresent()) {
			Movie movieDb = movieOptional.get();
			movieDb.setYear(movie.getYear());
			movieDb.setTitle(movie.getTitle());
			movieDb.setStudios(movie.getStudios());
			movieDb.setProducers(movie.getProducers());
			movieDb.setWinner(movie.getWinner());
			movieRepository.save(movieDb);
			return new ResponseEntity<Movie>(movieDb, HttpStatus.OK);	
		}else {
			return new ResponseEntity<Movie>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/interval/winner")
	public ResponseEntity<IntervalWinMinMaxVO> getInterval(){
		List<Movie> moviesWinners = movieRepository.findByWinner(true);
		IntervalWinMinMaxVO intervaloPremioMinMax = new IntervalWinMinMaxUtil().getIntervalWinMinMax(moviesWinners);
		return new ResponseEntity<IntervalWinMinMaxVO>(intervaloPremioMinMax, HttpStatus.OK);	
	}
}
