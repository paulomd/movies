package com.texoit.golden.raspberry.awards.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.texoit.golden.raspberry.awards.models.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {
	List<Movie> findByWinner(boolean vencedor);
}
