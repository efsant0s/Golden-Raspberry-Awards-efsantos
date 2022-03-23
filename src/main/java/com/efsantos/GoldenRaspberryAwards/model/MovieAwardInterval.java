package com.efsantos.GoldenRaspberryAwards.model;

public class MovieAwardInterval {
	private String producer;
	private int interval;
	private String previousWin;
	private String followingWin;

	public MovieAwardInterval() {

	}

	/**
	 * @param producer
	 * @param interval
	 * @param previousWin
	 * @param followingWin
	 */
	public MovieAwardInterval(String producer, int interval, String previousWin, String followingWin) {
		super();
		this.producer = producer;
		this.interval = interval;
		this.previousWin = previousWin;
		this.followingWin = followingWin;
	}

	public MovieAwardInterval(String producer, Integer interval, Integer previousWin, Integer followingWin) {
		this.producer = producer;
		this.interval = interval;
		this.previousWin = previousWin.toString();
		this.followingWin = followingWin.toString();

	}

	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public String getPreviousWin() {
		return previousWin;
	}

	public void setPreviousWin(String previousWin) {
		this.previousWin = previousWin;
	}

	public String getFollowingWin() {
		return followingWin;
	}

	public void setFollowingWin(String followingWin) {
		this.followingWin = followingWin;
	}

	@Override
	public String toString() {
		return "MovieAwardInterval [producer=" + producer + ", interval=" + interval + ", previousWin=" + previousWin
				+ ", followingWin=" + followingWin + "]";
	}

}
