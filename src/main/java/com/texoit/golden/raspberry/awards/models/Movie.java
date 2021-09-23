package com.texoit.golden.raspberry.awards.models;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Movie {
	@GeneratedValue
	@Id
	private Long id;
	private Integer year;
	private String title;
	private String studios;
	private String producers;
	private Boolean winner;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStudios() {
		return studios;
	}
	public void setStudios(String studios) {
		this.studios = studios;
	}
	public String getProducers() {
		return producers;
	}
	public void setProducers(String producers) {
		this.producers = producers;
	}
	public Boolean getWinner() {
		return winner;
	}
	public void setWinner(Boolean winner) {
		this.winner = winner;
	}
	
	public List<String> getProducersList(){
		String[] split = producers.replace("and",",").split(",");
		return Arrays.asList(split).stream().map(String::trim).collect(Collectors.toList());
	}
	
	public Movie clone() {
		Movie movie = new Movie();
		movie.setId(getId());
		movie.setYear(getYear());
		movie.setTitle(getTitle());
		movie.setStudios(getStudios());
		movie.setProducers(getProducers());
		movie.setWinner(getWinner());
		return movie;
	}
}
