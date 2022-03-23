package com.efsantos.GoldenRaspberryAwards.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.commons.lang3.ArrayUtils;

import com.efsantos.GoldenRaspberryAwards.utils.Utils;

@Entity
public class Movie {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column
	private Integer year;
	@Column
	private String title;
	@Column
	private String studios;
	@Column
	private String producers;
	@Column
	private String winner;

	public Movie() {
		// TODO Auto-generated constructor stub
	}

	/**
	 
	 * @param year
	 * @param title
	 * @param studios
	 * @param producers
	 * @param winner
	 */
	public Movie( Integer year, String title, String studios, String producers, String winner) {
		super(); 
		this.year = year;
		this.title = title;
		this.studios = studios;
		this.producers = producers;
		this.winner = winner;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

 
	public Integer getYear() {
		return year;
	}

	public void setYear(int year) {
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

	public String[] getProducersArray() {
		return Utils.getProducersArray(this.getProducers());
	}

	public void setProducers(String producers) {
		this.producers = producers;
	}

	public boolean isWinner() {
		return winner.endsWith("S");
	}

	public void setWinner(boolean winner) {
		this.winner = winner ? "S" : "N";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Movie [year=" + year + ", title=" + title + ", studios=" + studios + ", producers=" + producers
				+ ", winner=" + winner + "]";
	}

}
